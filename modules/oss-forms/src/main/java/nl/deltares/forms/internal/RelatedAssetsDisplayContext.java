package nl.deltares.forms.internal;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RelatedAssetsDisplayContext {

    private static final Log LOG = LogFactoryUtil.getLog(RelatedAssetsDisplayContext.class);
    private final AssetEntryService _assetEntryService;
    private final DsdParserUtils _dsdParserUtils;
    private final List<Registration> _relatedRegistrations = new ArrayList<>();
    private final ThemeDisplay _themeDisplay;
    private final String ids;

    public RelatedAssetsDisplayContext(HttpServletRequest request, AssetEntryService assetEntryService, DsdParserUtils dsdParserUtils) throws Exception {

        _assetEntryService = assetEntryService;
        _dsdParserUtils = dsdParserUtils;
        CPRequestHelper cpRequestHelper = new CPRequestHelper(request);
        _themeDisplay = cpRequestHelper.getThemeDisplay();
        ids = ParamUtil.getString(request, "ids");
        loadRelatedAssets(ids);
    }

    public JournalArticleDisplay getArticleDisplay(PortletRequest portletRequest, PortletResponse portletResponse,
                                                   String ddmTemplateKey, JournalArticle journalArticle, ThemeDisplay themeDisplay) {
        JournalArticleDisplay articleDisplay = null;
        try {
            articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(
                    journalArticle, ddmTemplateKey, "VIEW",
                    themeDisplay.getLanguageId(), 1, new PortletRequestModel(portletRequest, portletResponse),
                    themeDisplay);
        } catch (Exception e) {
            String message = String.format("Error getting article display object for article [%s] with template ID [%s]",
                    journalArticle.getArticleId(), ddmTemplateKey);
            LOG.debug(message, e);
        }
        return articleDisplay;
    }

    public List<Registration> getRelatedArticles() {
        return _relatedRegistrations;
    }

    public ThemeDisplay getThemeDisplay() {
        return _themeDisplay;
    }

    private void loadRelatedAssets(String ids) throws Exception {
        if (ids.isEmpty()) return;
        String[] registrationIds = ids.split(",", -1);

        AssetEntryQuery assetEntryQuery = _getAssetEntryQuery();

        long[] assetIds = new long[registrationIds.length];
        for (int i = 0; i < registrationIds.length; i++) {
            String registrationId = registrationIds[i];
            if (registrationId.isEmpty()) continue;
            JournalArticle latestArticle = JournalArticleLocalServiceUtil.getLatestArticle(_themeDisplay.getScopeGroupId(), registrationId);
            AssetEntry entry = AssetEntryLocalServiceUtil.getEntry(latestArticle.getModelClassName(), latestArticle.getResourcePrimKey());
            assetIds[i] = entry.getEntryId();
        }
        assetEntryQuery.setLinkedAssetEntryIds(assetIds);

        List<AssetEntry> entries = _assetEntryService.getEntries(assetEntryQuery);
        for (AssetEntry entry : entries) {
            JournalArticle journalArticle = JournalArticleLocalServiceUtil.fetchLatestArticle(entry.getClassPK());
            if (journalArticle == null) {continue;}
            if (Arrays.stream(registrationIds).anyMatch(registrationId -> journalArticle.getArticleId().equals(registrationId))) {continue;}

            Registration registration = _dsdParserUtils.getRegistration(journalArticle);
            if (registration.canUserRegister(_themeDisplay.getScopeGroupId())) {
                _relatedRegistrations.add(registration);
            }

        }
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

}
