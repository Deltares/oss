package nl.deltares.portal.utils.impl;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.*;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.sort.SortOrder;
import nl.deltares.portal.utils.DDMStructureUtil;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DuplicateCheck;
import nl.deltares.search.facet.DeltaresDateRangeFacet;
import nl.deltares.search.facet.DeltaresDdmFieldValueFacet;
import nl.deltares.search.facet.DeltaresMultipleFieldValueFacet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.*;


@Component(
        immediate = true,
        service = DsdJournalArticleUtils.class
)
public class DsdJournalArticleUtilsImpl implements DsdJournalArticleUtils {

    @Reference
    DDMStructureUtil ddmStructureUtil;

    @Reference
    AssetEntryService _assetEntryService;

    @Override
    public List<JournalArticle> getRelatedArticles(long groupId, String[] articleIds) throws PortalException {

        AssetEntryQuery assetEntryQuery = _getAssetEntryQuery();

        List<Long> assetIds = new ArrayList<>();
        for (int i = 0; i < articleIds.length; i++) {
            String registrationId = articleIds[i];
            if (registrationId.isEmpty()) continue;
            JournalArticle latestArticle = JournalArticleLocalServiceUtil.fetchArticle(groupId, registrationId);
            if (latestArticle == null) continue;
            AssetEntry entry = AssetEntryLocalServiceUtil.getEntry(latestArticle.getModelClassName(), latestArticle.getResourcePrimKey());
            assetIds.add(entry.getEntryId());
        }
        assetEntryQuery.setLinkedAssetEntryIds(assetIds.stream().mapToLong(Long::longValue).toArray());
        if (assetIds.isEmpty()) return Collections.emptyList();

        List<AssetEntry> entries = _assetEntryService.getEntries(assetEntryQuery);
        List<JournalArticle> relatedArticles = new ArrayList<>();
        for (AssetEntry entry : entries) {
            JournalArticle journalArticle = JournalArticleLocalServiceUtil.fetchLatestArticle(entry.getClassPK());
            if (journalArticle == null) {
                continue;
            }
            if (Arrays.stream(articleIds).anyMatch(registrationId -> journalArticle.getArticleId().equals(registrationId))) {
                continue;
            }
            relatedArticles.add(journalArticle);
        }
        return relatedArticles;
    }

    private AssetEntryQuery _getAssetEntryQuery() {
        AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

        ServiceContext serviceContext =
                ServiceContextThreadLocal.getServiceContext();

        assetEntryQuery.setClassNameIds(
                AssetRendererFactoryRegistryUtil.getIndexableClassNameIds(
                        serviceContext.getCompanyId(), true));

        assetEntryQuery.setEnablePermissions(true);


        assetEntryQuery.setGroupIds(
                new long[]{serviceContext.getScopeGroupId()});

        return assetEntryQuery;
    }

    @Override
    public JournalArticle getLatestArticle(long classPK) throws PortalException {
        return JournalArticleLocalServiceUtil.getLatestArticle(classPK);
    }

    @Override
    public List<JournalArticle> getEvents(long groupId, Locale locale) throws PortalException {

        Optional<DDMStructure> eventStructure = ddmStructureUtil.getDDMStructureByName(groupId, "EVENT", locale);
        if (eventStructure.isPresent()) {
            long structureId = eventStructure.get().getStructureId();
            DuplicateCheck check = new DuplicateCheck();
            try {
                List<JournalArticle> structureArticles = JournalArticleLocalServiceUtil.getStructureArticles(groupId, structureId);
                return check.filterLatest(structureArticles);
            } catch (Exception e) {
                throw new PortalException(e);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<JournalArticle> getRegistrationsForPeriod(long companyId, long groupId, Date startDate, Date endDate,
                                                          String[] structureKeys, String dateFieldName, Locale locale) throws PortalException {
        SearchContext sc = initSearchContext(companyId, groupId);
        queryDateRange(groupId, startDate, endDate, structureKeys, dateFieldName, sc, locale);
        return executeSearch(groupId, sc);
    }

    private List<JournalArticle> executeSearch(long groupId, SearchContext sc) throws SearchException {
        Indexer<JournalArticle> indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

        Hits hits = indexer.search(sc);
        DuplicateCheck check = new DuplicateCheck();

        List<JournalArticle> structureArticles = new ArrayList<>();
        hits.toList().forEach(doc -> {
            try {
                structureArticles.add(JournalArticleLocalServiceUtil.getArticle(groupId, doc.get("articleId")));
            } catch (Exception e) {
                //
            }
        });
        return check.filterLatest(structureArticles);
    }

    @Override
    public Map<String, String> getStructureFieldOptions(long groupId, String structureName, String optionsField, Locale locale) throws PortalException {

        Optional<DDMStructure> ddmStructureByName = ddmStructureUtil.getDDMStructureByName(groupId, structureName, locale);
        if (ddmStructureByName.isPresent()) {

            DDMStructure ddmStructure = ddmStructureByName.get();
            try {
                DDMFormField ddmFormField = ddmStructure.getDDMFormFieldByFieldReference(optionsField);
                DDMFormFieldOptions ddmFormFieldOptions = ddmFormField.getDDMFormFieldOptions();
                if (ddmFormFieldOptions == null) return Collections.emptyMap();

                Map<String, String> optionValues = new TreeMap<>();
                ddmFormFieldOptions.getOptions().forEach((s, localizedValue) -> optionValues.put(s, localizedValue.getString(locale)));
                return optionValues;
            } catch (PortalException e) {
                throw new PortalException(String.format("Could not find field '%s' in structure '%s' for groupId %d", optionsField, structureName, groupId));
            }
        }
        return Collections.emptyMap();

    }

    @Override
    public void queryDateRange(long groupId, Date startDate, Date endDate,
                               String[] structureKeys, String dateFieldName,
                               SearchContext searchContext, Locale locale) {

        final List<String> fieldNameValues = ddmStructureUtil.getEncodedFieldNamesForStructures(groupId, dateFieldName, structureKeys, locale);
        final DeltaresDateRangeFacet facet = new DeltaresDateRangeFacet(dateFieldName, searchContext);
        facet.setFieldNameValues(fieldNameValues.toArray(new String[0]));
        if (startDate != null) facet.setStartSearchDate(DateUtil.getDate(startDate, "yyyy-MM-dd", locale));
        if (endDate != null) facet.setEndSearchDate(DateUtil.getDate(endDate, "yyyy-MM-dd", locale));
        searchContext.addFacet(facet);
    }

    @Override
    public void sortByDDMFieldArrayField(long groupId, String[] structureKeys, String dateFieldName,
                                         SearchRequestBuilder searchRequestBuilder, Locale locale, boolean reverseOrder) {

        final List<String> fieldNameValues = ddmStructureUtil.getEncodedFieldNamesForStructures(groupId, dateFieldName, structureKeys, locale);
        searchRequestBuilder.sorts(ddmStructureUtil.buildDDMFieldArraySort(fieldNameValues.toArray(new String[0]),
                reverseOrder ? SortOrder.DESC : SortOrder.ASC));

    }

    @Override
    public void queryDdmFieldValue(long groupId, String searchFieldName, String searchFieldValueKeywordValue,
                                   String[] structureKeys, SearchContext searchContext, Locale locale) {

        queryDdmFieldValue(groupId, searchFieldName, searchFieldValueKeywordValue, structureKeys, searchContext, locale, false);
    }

    @Override
    public void queryDdmFieldValues(long groupId, String searchFieldName, String[] searchFieldValueKeywordValues,
                                    String[] structureKeys, SearchContext searchContext, Locale locale) {

        queryDdmFieldValues(groupId, searchFieldName, searchFieldValueKeywordValues, structureKeys, searchContext, locale, false);
    }

    @Override
    public void queryExcludeDdmFieldValue(long groupId, String searchFieldName, String searchFieldValueKeywordValue,
                                          String[] structureKeys, SearchContext searchContext, Locale locale) {
        queryDdmFieldValue(groupId, searchFieldName, searchFieldValueKeywordValue, structureKeys, searchContext, locale, true);
    }

    private void queryDdmFieldValue(long groupId, String searchFieldName, String searchFieldValueKeywordValue,
                                    String[] structureKeys, SearchContext searchContext, Locale locale, boolean excludeValue) {
        queryDdmFieldValues(groupId, searchFieldName, new String[]{searchFieldValueKeywordValue}, structureKeys, searchContext, locale, excludeValue);
    }

    private void queryDdmFieldValues(long groupId, String searchFieldName, String[] searchFieldValueKeywordValues,
                                     String[] structureKeys, SearchContext searchContext, Locale locale, boolean excludeValue) {
        if (searchFieldValueKeywordValues == null || searchFieldValueKeywordValues.length == 0) return;

        final String languageString = locale.toString();
        final List<String> fieldNameValues = ddmStructureUtil.getEncodedFieldNamesForStructures(groupId, searchFieldName, structureKeys, locale);
        DeltaresDdmFieldValueFacet nestedFacetImpl = new DeltaresDdmFieldValueFacet(searchFieldName, languageString, searchContext);
        nestedFacetImpl.setFieldNameValues(fieldNameValues.toArray(new String[0]));
        nestedFacetImpl.setFieldValueKeywordName("ddmFieldValueKeyword");
        nestedFacetImpl.setFieldValueKeywordValues(searchFieldValueKeywordValues);
        nestedFacetImpl.setExclude(excludeValue);
        searchContext.addFacet(nestedFacetImpl);
    }

    @Override
    public void queryMultipleFieldValues(long groupId, String[] structureKeys, SearchContext searchContext, Locale locale) {
        final List<Optional<DDMStructure>> ddmStructuresOptionals = ddmStructureUtil.getDDMStructuresByName(groupId, structureKeys, locale);
        final DeltaresMultipleFieldValueFacet facet = new DeltaresMultipleFieldValueFacet("ddmStructureKey", searchContext);
        List<String> fieldValues = new ArrayList<>();
        ddmStructuresOptionals.forEach(optionalDDMStructure -> optionalDDMStructure.ifPresent(ddmStructure -> fieldValues.add(ddmStructure.getStructureKey())));
        facet.setFieldValues(fieldValues.toArray(new String[0]));
        searchContext.addFacet(facet);
    }

    @Override
    public List<JournalArticle> getRegistrations(long companyId, long groupId, String[] structureKeys, Locale locale) throws PortalException {
        SearchContext sc = initSearchContext(companyId, groupId);
        queryMultipleFieldValues(groupId, structureKeys, sc, locale);
        return executeSearch(groupId, sc);
    }

    @Override
    public List<JournalArticle> getRegistrationsForEvent(long companyId, long groupId, String eventArticleId, String[] registrationStructureKeys, Locale locale) throws PortalException {
        final SearchContext searchContext = initSearchContext(companyId, groupId);
        queryDdmFieldValue(groupId, "eventId", eventArticleId, registrationStructureKeys, searchContext, locale);
        return executeSearch(groupId, searchContext);
    }

    private SearchContext initSearchContext(long companyId, long groupId) {
        SearchContext sc = new SearchContext();
        sc.setCompanyId(companyId);
        sc.setGroupIds(new long[]{groupId});
        sc.setStart(QueryUtil.ALL_POS);
        sc.setEnd(QueryUtil.ALL_POS);
        return sc;
    }


    @Override
    public JournalArticle getJournalArticle(long groupId, String articleId) throws PortalException {
        return JournalArticleLocalServiceUtil.getLatestArticle(groupId, articleId);
    }
}