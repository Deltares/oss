package nl.deltares.search.facet.checkbox;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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
        property = "javax.portlet.name=" + SearchModuleKeys.CHECKBOX_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class CheckboxFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        final Group scopeGroup = portletSharedSearchSettings.getThemeDisplay().getScopeGroup();
        long groupId = scopeGroup.getGroupId();
        final Locale siteDefaultLocale = LocaleUtil.fromLanguageId(scopeGroup.getDefaultLanguageId());
        ThemeDisplay themeDisplay = portletSharedSearchSettings.getThemeDisplay();
        CheckboxFacetConfiguration _configuration;
        try {
            _configuration = _configurationProvider.getPortletInstanceConfiguration(
                    CheckboxFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
        } catch (ConfigurationException e) {
            LOG.debug(String.format("Could not get configuration for portlet '%s': %s", themeDisplay.getPortletDisplay().getId(), e.getMessage()), e);
            return;
        }
        final boolean visible = Boolean.parseBoolean(_configuration.visible());
        final boolean explicit = Boolean.parseBoolean(_configuration.explicitSearch());
        String structureName = _configuration.structureName().toLowerCase();
        String fieldName = _configuration.fieldName();
        String name = structureName + '-' + fieldName; //important to use '-' because this translates to JSP id

        String selection = null;
        Optional<String> facetSelection = portletSharedSearchSettings.getParameterOptional(name);
        if (facetSelection.isPresent()) {
            selection = facetSelection.get();
        } else if (!visible) {
            selection = FacetUtils.serializeYesNo(Boolean.parseBoolean(_configuration.defaultValue()));
        }

        if (selection != null) {
            final Boolean option = FacetUtils.parseYesNo(selection);
            if (option != null) {
                if (explicit) {
                    //look only for article containing the search field
                    _dsdJournalArticleUtils.queryDdmFieldValue(groupId, fieldName, option.toString(), new String[]{structureName},
                            portletSharedSearchSettings.getSearchContext(), siteDefaultLocale);
                } else {
                    //exclude all articles containing the opposite value, allowing all articles without value to pass through
                    _dsdJournalArticleUtils.queryExcludeDdmFieldValue(groupId, fieldName, Boolean.toString(!option), new String[]{structureName},
                            portletSharedSearchSettings.getSearchContext(), siteDefaultLocale);
                }

            }
        }

    }

    @Reference
    private DsdJournalArticleUtils _dsdJournalArticleUtils;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(CheckboxFacetPortletSharedSearchContributor.class);
}
