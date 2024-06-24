package nl.deltares.search.results;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + SearchModuleKeys.SEARCH_RESULTS_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class SearchResultsPortletSharedSearchContributor implements PortletSharedSearchContributor {

    private static final Log LOG = LogFactoryUtil.getLog(SearchResultsPortletSharedSearchContributor.class);

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {

        ThemeDisplay themeDisplay = portletSharedSearchSettings.getThemeDisplay();
        SearchResultsPortletConfiguration searchResultsConfiguration;
        boolean reverseOrder;
        try {
            searchResultsConfiguration = _configurationProvider.getPortletInstanceConfiguration(
                    SearchResultsPortletConfiguration.class, themeDisplay.getLayout(), portletSharedSearchSettings.getPortletId());
            reverseOrder = Boolean.parseBoolean(searchResultsConfiguration.reverseOrder());
        } catch (ConfigurationException e) {
            LOG.warn(String.format("Could not get SearchResultsConfiguration for portlet '%s': %s", portletSharedSearchSettings.getPortletId(), e.getMessage()));
            reverseOrder = false;
        }

        String namespace = '_' + portletSharedSearchSettings.getPortletId() + '_';

        final String deltaParam = FacetUtils.getRequestParameter(namespace + "delta", portletSharedSearchSettings.getRenderRequest());
        final String curParam = FacetUtils.getRequestParameter(namespace + "cur", portletSharedSearchSettings.getRenderRequest());
        if (deltaParam != null) {
            portletSharedSearchSettings.setPaginationDelta(Integer.parseInt(deltaParam));
        } else {
            portletSharedSearchSettings.setPaginationDelta(20);
        }
        if (curParam != null) {
            portletSharedSearchSettings.setPaginationStart(Integer.parseInt(curParam));
        }

        try {
            DSDSiteConfiguration dsdSiteConfiguration = _configurationProvider.getPortletInstanceConfiguration(DSDSiteConfiguration.class,
                    themeDisplay.getLayout(), portletSharedSearchSettings.getPortletId());

            String dateFieldName = dsdSiteConfiguration.dsdRegistrationDateField();
            String[] structureKeys;
            String structureList = dsdSiteConfiguration.dsdRegistrationStructures();
            if (structureList != null && !structureList.isEmpty()){
                structureKeys =  StringUtil.split(structureList, ' ');
            } else {
                structureKeys = new String[0];
            }

            _dsDsdJournalArticleUtils.sortByDDMFieldArrayField(themeDisplay.getSiteGroupId(), structureKeys, dateFieldName,
                    portletSharedSearchSettings.getSearchRequestBuilder(), themeDisplay.getLocale(), reverseOrder);
        } catch (ConfigurationException e) {
            LOG.warn("Could not get DsdSiteConfiguration for portlet " + portletSharedSearchSettings.getPortletId(), e);
        }

    }

    @Reference
    private DsdJournalArticleUtils _dsDsdJournalArticleUtils;
    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

}
