package nl.deltares.search.facet.event;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.utils.DDMStructureUtil;
import nl.deltares.search.constans.FacetPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;
import java.util.Optional;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + FacetPortletKeys.EVENT_FACET_PORTLET
        },
        service = PortletSharedSearchContributor.class
)
public class EventFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        ThemeDisplay themeDisplay = portletSharedSearchSettings.getThemeDisplay();
        Locale locale = themeDisplay.getLocale();
        long groupId = themeDisplay.getScopeGroupId();

        String eventId = null;

        try {
            DSDSiteConfiguration configuration = _configurationProvider.
                    getGroupConfiguration(DSDSiteConfiguration.class, groupId);
            if (configuration.eventId() > 0) {
                eventId = String.valueOf(configuration.eventId());
            }

        } catch (ConfigurationException e) {
            LOG.debug("Could not get event configuration", e);
        }

        if (eventId != null) {
            String[] structures = new String[]{"SESSION"};
            for (String structure : structures) {
                Optional<DDMStructure> ddmStructureOptional = _ddmStructureUtil
                        .getDDMStructureByName(structure, locale);
                if (ddmStructureOptional.isPresent()) {
                    long ddmStructureId = ddmStructureOptional.get().getStructureId();
                    String idField = _ddmIndexer.encodeName(ddmStructureId, "eventId", locale);
                    portletSharedSearchSettings.addFacet(buildFacet(idField, eventId, portletSharedSearchSettings));
                }

            }
        }

    }

    protected Facet buildFacet(String fieldName, String eventId,
                               PortletSharedSearchSettings portletSharedSearchSettings) {

        _sessionTypeFacetFactory.setField(fieldName);

        EventFacetBuilder sessionTypeFacetBuilder = new EventFacetBuilder(_sessionTypeFacetFactory);
        sessionTypeFacetBuilder.setSearchContext(portletSharedSearchSettings.getSearchContext());
        sessionTypeFacetBuilder.setEventId(eventId);
        return sessionTypeFacetBuilder.build();
    }

    @Reference
    private DDMIndexer _ddmIndexer;

    @Reference
    private DDMStructureUtil _ddmStructureUtil;

    @Reference
    private EventFacetFactory _sessionTypeFacetFactory;

    @Reference
    protected PortletSharedSearchRequest portletSharedSearchRequest;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(EventFacetPortletSharedSearchContributor.class);
}
