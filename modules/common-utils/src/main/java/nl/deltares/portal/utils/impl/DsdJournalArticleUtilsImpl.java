package nl.deltares.portal.utils.impl;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.*;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.generic.*;
import com.liferay.portal.kernel.util.DateUtil;
import nl.deltares.portal.utils.DDMStructureUtil;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DuplicateCheck;
import nl.deltares.search.facet.registration.StructureKeyFacetBuilder;
import nl.deltares.search.facet.registration.StructureKeyFacetFactory;
import nl.deltares.search.facet.registration.builder.impl.StructureKeyFacetFactoryImpl;
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
    private DDMIndexer _ddmIndexer;

    @Override
    public JournalArticle getLatestArticle(long classPK) throws PortalException {
        return JournalArticleLocalServiceUtil.getLatestArticle(classPK);
    }

    @Override
    public List<JournalArticle> getEvents(long groupId, Locale locale) throws PortalException {

        Optional<DDMStructure> eventStructure = ddmStructureUtil.getDDMStructureByName(groupId, "EVENT", locale);
        if (eventStructure.isPresent()){
            String structureKey = eventStructure.get().getStructureKey();
            DuplicateCheck check = new DuplicateCheck();
            try {
                List<JournalArticle> structureArticles = JournalArticleLocalServiceUtil.getStructureArticles(groupId, structureKey);
                return check.filterLatest(structureArticles);
            } catch (Exception e) {
                throw new PortalException(e);
            }
        }
        return Collections.emptyList();
    }

    protected Query buildMultiMatchQuery(String[] fieldNames, String fieldValue, MatchQuery.Operator operator) {
        MultiMatchQuery multiMatchQuery = new MultiMatchQuery(fieldValue);
        multiMatchQuery.addFields(fieldNames);
        multiMatchQuery.setOperator(operator);
        return multiMatchQuery;
    }

    protected Query buildDateRangeQuery(String fieldName, Date startDate, Date endDate, Locale locale) {

        String startDateString = null;
        if (startDate != null) startDateString = DateUtil.getDate(startDate, "yyyy-MM-dd", locale);
        String endDateString = null;
        if (endDate != null) endDateString =DateUtil.getDate(endDate, "yyyy-MM-dd", locale);
        return new TermRangeQueryImpl(fieldName, startDateString, endDateString, startDate != null, endDate != null);
    }

    protected Facet buildStructureKeyFacet(List<String> structureKeys, SearchContext searchContext) {

        StructureKeyFacetFactory factory = new StructureKeyFacetFactoryImpl();
        factory.setField("ddmStructureKey");

        StructureKeyFacetBuilder builder = new StructureKeyFacetBuilder(factory);
        builder.setSearchContext(searchContext);
        builder.setStructureKeys(structureKeys.toArray(new String[0]));
        return builder.build();
    }

    @Override
    public List<JournalArticle> getRegistrationsForPeriod(long companyId, long groupId, Date startDate, Date endDate,
                                                          String[] structureKeys, String dateFieldName, Locale locale) throws PortalException {
        SearchContext sc = initSearchContext(companyId, groupId);
        contributeDsdDateRangeRegistrations(groupId, startDate, endDate, structureKeys, dateFieldName, sc, locale);
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
        if (ddmStructureByName.isPresent()){

            DDMStructure ddmStructure = ddmStructureByName.get();
            try {
                DDMFormField ddmFormField = ddmStructure.getDDMFormField(optionsField);
                DDMFormFieldOptions ddmFormFieldOptions = ddmFormField.getDDMFormFieldOptions();
                if (ddmFormFieldOptions == null) return Collections.emptyMap();

                Map<String, String> optionValues = new TreeMap<>();
                ddmFormFieldOptions.getOptions().forEach((s, localizedValue) -> {
                    optionValues.put(s, localizedValue.getString(locale));
                });
                return optionValues;
            } catch (PortalException e) {
                throw new PortalException(String.format("Could not find field '%s' in structure '%s' for groupId %d", structureName, optionsField, groupId));
            }
        }
        throw new PortalException(String.format("Could not structure '%s' for groupId %d", structureName, groupId));

    }

    @Override
    public void contributeDsdDateRangeRegistrations(long groupId, Date startDate, Date endDate,
                                                    String[] structureKeys, String dateFieldName,
                                                    SearchContext searchContext, Locale locale){

        List<Optional<DDMStructure>> optionalList = ddmStructureUtil.getDDMStructuresByName(groupId, structureKeys, locale);

        BooleanClauseFactory booleanClauseFactory = BooleanClauseFactoryUtil.getBooleanClauseFactory();

        BooleanQueryImpl booleanQuery = new BooleanQueryImpl();
        for (Optional<DDMStructure> structureOptional : optionalList) {
            if (structureOptional.isPresent()) {
                long ddmStructureId = structureOptional.get().getStructureId();
                String startDateField = _ddmIndexer.encodeName(ddmStructureId, dateFieldName, locale);
                booleanQuery.add(buildDateRangeQuery(startDateField, startDate, endDate, locale), BooleanClauseOccur.SHOULD);
            }
        }
        BooleanClause<Query> queryBooleanClause = booleanClauseFactory.create(booleanQuery, BooleanClauseOccur.MUST.getName());
        searchContext.setBooleanClauses(join(queryBooleanClause, searchContext.getBooleanClauses()));
    }

    private BooleanClause[] join(BooleanClause newClause, BooleanClause<Query>[] existingClauses) {

        if (existingClauses == null || existingClauses.length == 0) return new BooleanClause[]{newClause};
        BooleanClause[] joinedClauses = new BooleanClause[existingClauses.length + 1];
        System.arraycopy(existingClauses, 0, joinedClauses, 0, existingClauses.length);
        System.arraycopy(new BooleanClause[] {newClause}, 0, joinedClauses, existingClauses.length, 1);
        return joinedClauses;
    }

    @Override
    public void contributeDsdEventRegistrations(long groupId, String eventId, SearchContext searchContext, Locale locale){
        List<Optional<DDMStructure>> sessionStructure = ddmStructureUtil.getDDMStructuresByName(groupId, new String[]{"SESSION", "DINNER", "BUSTRANSFER"}, locale);

        BooleanClauseFactory booleanClauseFactory = BooleanClauseFactoryUtil.getBooleanClauseFactory();
        List<String> eventIdFields = new ArrayList<>();
        sessionStructure.forEach(optional -> optional.ifPresent(structure -> {
            long ddmStructureId = structure.getStructureId();
            String idField = _ddmIndexer.encodeName(ddmStructureId, "eventId", locale);
            eventIdFields.add(idField);
        }));
        searchContext.setBooleanClauses(join(booleanClauseFactory.create(buildMultiMatchQuery(
                eventIdFields.toArray(new String[0]), eventId, MatchQuery.Operator.OR), BooleanClauseOccur.MUST.getName()), searchContext.getBooleanClauses()));
    }

    @Override
    public void contributeDsdRegistrations(long groupId, String[] registrationStructureNames, SearchContext searchContext, Locale locale){
        List<Optional<DDMStructure>> sessionStructure = ddmStructureUtil.getDDMStructuresByName(groupId, registrationStructureNames, locale);

        List<String> structureKeys = new ArrayList<>();
        sessionStructure.forEach(optional -> {
            optional.ifPresent(structure -> structureKeys.add(structure.getStructureKey()));
        });

        searchContext.addFacet(buildStructureKeyFacet(structureKeys, searchContext));
    }

    @Override
    public List<JournalArticle> getRegistrations(long companyId, long groupId, String[] structureKeys, Locale locale) throws PortalException {
        SearchContext sc = initSearchContext(companyId, groupId);
        contributeDsdRegistrations(groupId, structureKeys, sc, locale);
        return executeSearch(groupId, sc);
    }

    @Override
    public List<JournalArticle> getRegistrationsForEvent(long companyId, long groupId, String eventArticleId, Locale locale) throws PortalException {
        SearchContext sc = initSearchContext(companyId, groupId);
        contributeDsdEventRegistrations(groupId, eventArticleId, sc, locale);
        return executeSearch(groupId, sc);
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
