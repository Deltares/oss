package nl.deltares.search.facet.program;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.model.facet.FacetSelection;
import nl.deltares.portal.utils.DsdSessionUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.facet.program.builder.UserProgramFacetBuilder;
import nl.deltares.search.facet.program.builder.UserProgramFacetFactory;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + SearchModuleKeys.USER_PROGRAM_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class UserProgramFacetPortletSharedContributor implements PortletSharedSearchContributor {
    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {

        ThemeDisplay themeDisplay = portletSharedSearchSettings.getThemeDisplay();
        String currentPortletId = portletSharedSearchSettings.getPortletId();
        long userId;
        long groupId;
        String selection = (String) FacetUtils.getFromSession(currentPortletId, "user-program-site-selection", portletSharedSearchSettings.getRenderRequest());
        if (selection == null || selection.isEmpty()) {
            userId = themeDisplay.getUserId();
            groupId = themeDisplay.getSiteGroupId();
            //Copy FacetSelection to the context of the SearchResultsPortlet
            FacetUtils.removeFromSession(themeDisplay.getPortletDisplay().getId(), "facet-selection", portletSharedSearchSettings.getRenderRequest());

        } else {
            FacetSelection facetSelection = FacetUtils.getFacetSelection(Long.parseLong(selection), themeDisplay);
            groupId = facetSelection.getSiteGroupId();
            userId = facetSelection.getUserId();

            //Copy FacetSelection to the context of the SearchResultsPortlet
            FacetUtils.storeInSession(themeDisplay.getPortletDisplay().getId(), "facet-selection", facetSelection, portletSharedSearchSettings.getRenderRequest());
        }
        try {
            List<String> entryClassPKs;
            if (showRegistrationForOthers(portletSharedSearchSettings)) {
                entryClassPKs = _dsdSessionUtils.getResourceIdsByAuthorAndGroup(userId, groupId)
                        .stream()
                        .map(String::valueOf)
                        .collect(Collectors.toList());
            } else {
                entryClassPKs = _dsdSessionUtils.getResourceIdsByUserAndGroup(userId, groupId)
                        .stream()
                        .map(String::valueOf)
                        .collect(Collectors.toList());
            }
            portletSharedSearchSettings.addFacet(buildFacet(entryClassPKs, portletSharedSearchSettings));
        } catch (Exception e) {
            LOG.debug("Could not get registrations for user [" + userId + "]", e);
        }
    }

    private boolean showRegistrationForOthers(PortletSharedSearchSettings portletSharedSearchSettings) {

        try {
            UserProgramFacetConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(UserProgramFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
            String showRegistrationsForOthers = configuration.showRegistrationsMadeForOthers();
            return Boolean.parseBoolean(showRegistrationsForOthers);
        } catch (ConfigurationException e) {
            LOG.warn("Could not find configuration for UserProgramFacetConfiguration", e);
        }
        return false;
    }

    private Facet buildFacet(List<String> entryClassPKs, PortletSharedSearchSettings portletSharedSearchSettings) {
        _userProgramFacetFactory.setField("entryClassPK");

        UserProgramFacetBuilder userProgramFacetBuilder = new UserProgramFacetBuilder(_userProgramFacetFactory);
        userProgramFacetBuilder.setSearchContext(portletSharedSearchSettings.getSearchContext());
        userProgramFacetBuilder.setClassPKs(entryClassPKs);

        return userProgramFacetBuilder.build();
    }

    @Reference
    private DsdSessionUtils _dsdSessionUtils;

    @Reference
    private UserProgramFacetFactory _userProgramFacetFactory;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(UserProgramFacetPortletSharedContributor.class);
}
