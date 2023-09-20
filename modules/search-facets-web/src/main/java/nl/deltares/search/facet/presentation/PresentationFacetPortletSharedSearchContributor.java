package nl.deltares.search.facet.presentation;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.facet.event.EventFacetPortletSharedSearchContributor;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletPreferences;
import java.util.Locale;
import java.util.Optional;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + SearchModuleKeys.PRESENTATION_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class PresentationFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {


    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        final ThemeDisplay themeDisplay = portletSharedSearchSettings.getThemeDisplay();
        final Group scopeGroup = themeDisplay.getScopeGroup();
        long groupId = scopeGroup.getGroupId();
        final Locale siteDefaultLocale = LocaleUtil.fromLanguageId(scopeGroup.getDefaultLanguageId());

        Optional<String> hasPresentations = portletSharedSearchSettings.getParameterOptional("hasPresentations");
        boolean onlyShowPresentations;
        boolean visible = true;
        boolean defaultValue = false;
        if (portletSharedSearchSettings.getPortletPreferencesOptional().isPresent()) {
            final PortletPreferences portletPreferences = portletSharedSearchSettings.getPortletPreferencesOptional().get();
            final String visibleConf = portletPreferences.getValue("visible", "");
            final String defaultValueConf = portletPreferences.getValue("defaultValue", "");

            if (!visibleConf.isEmpty() && !defaultValueConf.isEmpty()) {
                visible = Boolean.parseBoolean(visibleConf);
                defaultValue = Boolean.parseBoolean(defaultValueConf);
            }
        }
        if (visible) {
            onlyShowPresentations = hasPresentations.isPresent() && Boolean.parseBoolean(hasPresentations.get());
        } else {
            onlyShowPresentations = defaultValue;
        }

        if (onlyShowPresentations) {

            String[] structureKeys = null;
            try {
                DSDSiteConfiguration configuration = _configurationProvider.
                        getGroupConfiguration(DSDSiteConfiguration.class, groupId);

                structureKeys = FacetUtils.getStructureKeys(configuration);
            } catch (ConfigurationException e) {
                LOG.debug("Could not get dsd site configuration", e);
            }

            _dsdJournalArticleUtils.queryDdmFieldValue(groupId, "hasPresentations", "true", structureKeys,
                    portletSharedSearchSettings.getSearchContext(), siteDefaultLocale);

        }

    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(EventFacetPortletSharedSearchContributor.class);

    @Reference
    private DsdJournalArticleUtils _dsdJournalArticleUtils;

}
