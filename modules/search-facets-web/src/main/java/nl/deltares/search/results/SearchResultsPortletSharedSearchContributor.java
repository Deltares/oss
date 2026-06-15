package nl.deltares.search.results;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
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
        int delta = -1;
        try {
            searchResultsConfiguration = _configurationProvider.getPortletInstanceConfiguration(
                    SearchResultsPortletConfiguration.class, themeDisplay.getLayout(), portletSharedSearchSettings.getPortletId());
            reverseOrder = Boolean.parseBoolean(searchResultsConfiguration.reverseOrder());
            delta = Integer.parseInt(searchResultsConfiguration.numberOfResults().isEmpty() ? "20" : searchResultsConfiguration.numberOfResults());
        } catch (ConfigurationException e) {
            LOG.warn(String.format("Could not get SearchResultsConfiguration for portlet '%s': %s", portletSharedSearchSettings.getPortletId(), e.getMessage()));
            reverseOrder = false;
        }

        _dsDsdJournalArticleUtils.addDefaultFacets(portletSharedSearchSettings);

        String namespace = '_' + portletSharedSearchSettings.getPortletId() + '_';

        final String deltaParam = FacetUtils.getRequestParameter(namespace + "delta", portletSharedSearchSettings.getRenderRequest());
        if (deltaParam != null) {
            delta = Integer.parseInt(deltaParam);
        }
        final String curParam = FacetUtils.getRequestParameter(namespace + "cur", portletSharedSearchSettings.getRenderRequest());
        portletSharedSearchSettings.setPaginationDelta(delta);

        if (curParam != null) {
            portletSharedSearchSettings.setPaginationStart(Integer.parseInt(curParam));
        }

        //Sort values based on modification time. Sorting on registrationDate field does not work properly over entire index
        SortOrder sortOrder = reverseOrder ? SortOrder.DESC : SortOrder.ASC;
        _dsDsdJournalArticleUtils.addDDMFieldSortTerm(portletSharedSearchSettings, "ddmFieldArray", "registrationDate", null, sortOrder);

    }

    @Reference
    private DsdJournalArticleUtils _dsDsdJournalArticleUtils;
    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

}
