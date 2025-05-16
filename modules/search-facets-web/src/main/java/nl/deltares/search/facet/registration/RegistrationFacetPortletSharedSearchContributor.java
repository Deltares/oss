package nl.deltares.search.facet.registration;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;
import java.util.Optional;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + SearchModuleKeys.REGISTRATION_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class RegistrationFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {


    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        ThemeDisplay themeDisplay = portletSharedSearchSettings.getThemeDisplay();
        String[] structureKeys = getStructureKeys(portletSharedSearchSettings);

        final Locale siteDefaultLocale = LocaleUtil.fromLanguageId(themeDisplay.getScopeGroup().getDefaultLanguageId());
        _dsdJournalArticleUtils.queryMultipleFieldValues(
                themeDisplay.getScopeGroupId(),
                structureKeys,
                portletSharedSearchSettings.getSearchContext(),
                siteDefaultLocale);
    }

    private String[] getStructureKeys(PortletSharedSearchSettings portletSharedSearchSettings) {

        Optional<String> optional = Optional.ofNullable(portletSharedSearchSettings.getParameter("structureList"));
        return optional.map(s -> s.split(" ")).orElseGet(() -> {
            String structureList = getConfiguredValue( portletSharedSearchSettings);
            if (structureList != null && !structureList.isEmpty()){
                return StringUtil.split(structureList, ' ');
            }
            return new String[0];
        });
    }

    @SuppressWarnings("SameParameterValue")
    private String getConfiguredValue(PortletSharedSearchSettings portletSharedSearchSettings){

        try {
            RegistrationFacetConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(RegistrationFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
            String overrulingStructures = configuration.structureList();
            if (overrulingStructures.isEmpty()){

                DSDSiteConfiguration siteConfiguration = _configurationProvider
                            .getGroupConfiguration(DSDSiteConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getSiteGroupId());
                return siteConfiguration.dsdRegistrationStructures();
            }
            return overrulingStructures;
        } catch (ConfigurationException e) {
            LOG.warn("Could not find configuration for structureList", e);
        }
        return null;
    }
    @Reference
    private DsdJournalArticleUtils _dsdJournalArticleUtils;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }
    private static final Log LOG = LogFactoryUtil.getLog(RegistrationFacetPortletSharedSearchContributor.class);

}
