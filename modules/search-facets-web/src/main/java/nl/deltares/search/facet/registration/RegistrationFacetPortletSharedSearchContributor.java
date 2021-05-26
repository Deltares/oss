package nl.deltares.search.facet.registration;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.FacetPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Optional;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + FacetPortletKeys.REGISTRATION_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class RegistrationFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {


    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        ThemeDisplay themeDisplay = portletSharedSearchSettings.getThemeDisplay();
        String[] structureKeys = getStructureKeys(portletSharedSearchSettings);
        _dsdJournalArticleUtils.contributeDsdRegistrations(
                themeDisplay.getScopeGroupId(),
                structureKeys,
                portletSharedSearchSettings.getSearchContext(),
                themeDisplay.getLocale());
    }

    private String[] getStructureKeys(PortletSharedSearchSettings portletSharedSearchSettings) {

        Optional<String> optional = portletSharedSearchSettings.getParameter("structureList");
        return optional.map(s -> s.split(" ")).orElseGet(() -> getConfiguredStructures(portletSharedSearchSettings));
    }

    private String[] getConfiguredStructures(PortletSharedSearchSettings portletSharedSearchSettings){

        try {
            RegistrationFacetConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(
                    RegistrationFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());

            String structureList = configuration.structureList();

            if (structureList != null && !structureList.isEmpty()){
                return StringUtil.split(structureList, ' ');
            }
        } catch (ConfigurationException  e) {
            LOG.debug("Could not get structures configuration", e);
        }
        return new String[0];

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
