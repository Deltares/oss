package nl.deltares.search.facet.registration;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.search.constans.FacetPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Collections;
import java.util.List;
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
        List<String> structureKeys = getStructureKeys(portletSharedSearchSettings);
        _dsdJournalArticleUtils.contributeDsdRegistrations(
                themeDisplay.getScopeGroupId(),
                structureKeys.toArray(new String[0]),
                portletSharedSearchSettings.getSearchContext(),
                themeDisplay.getLocale());
    }

    private List<String> getStructureKeys(PortletSharedSearchSettings portletSharedSearchSettings) {

        Optional<String> optional = portletSharedSearchSettings.getParameter("structureList");
        if (optional.isPresent()) {
            try {
                return JsonContentUtils.parseJsonArrayToList(optional.get());
            } catch (JSONException e) {
                LOG.debug(String.format("Could not parse configured structures %s: %s", optional.get(), e.getMessage()), e);
            }
        }
        return getConfiguredStructures(portletSharedSearchSettings);
    }

    private List<String> getConfiguredStructures(PortletSharedSearchSettings portletSharedSearchSettings){

        try {
            RegistrationFacetConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(
                    RegistrationFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());

            String structureList = configuration.structureList();

            if (structureList != null && !structureList.isEmpty()){
                return JsonContentUtils.parseJsonArrayToList(structureList);
            }
            return Collections.emptyList();

        } catch (ConfigurationException | JSONException e) {
            LOG.debug("Could not get structures configuration", e);
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
