package nl.deltares.search.facet.selection;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;
import java.util.Optional;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + SearchModuleKeys.SELECTION_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class SelectionFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        final Group scopeGroup = portletSharedSearchSettings.getThemeDisplay().getScopeGroup();
        long groupId = scopeGroup.getGroupId();
        final Locale siteDefaultLocale = LocaleUtil.fromLanguageId(scopeGroup.getDefaultLanguageId());
        SelectionFacetConfiguration configuration;
        try {
            configuration = _configurationProvider.getPortletInstanceConfiguration(
                    SelectionFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
        } catch (ConfigurationException e) {
            LOG.debug("Could not get structures configuration", e);
            return;
        }

        String structureName = configuration.structureName().toLowerCase();
        String fieldName = configuration.fieldName();
        String name = structureName + '-' + fieldName;

        Optional<String> selectionOptional = portletSharedSearchSettings.getParameterOptional(name);
        //check for parameter is in namespace of searchResultsPortlet
        String selection = selectionOptional.orElseGet(() -> FacetUtils.getIteratorParameter(name, portletSharedSearchSettings.getRenderRequest()));
        if (selection != null) {
            _dsdJournalArticleUtils.queryDdmFieldValue(groupId, fieldName, selection, new String[]{structureName},
                    portletSharedSearchSettings.getSearchContext(), siteDefaultLocale);
        }

    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    @Reference
    private DsdJournalArticleUtils _dsdJournalArticleUtils;


    private static final Log LOG = LogFactoryUtil.getLog(SelectionFacetPortletSharedSearchContributor.class);
}
