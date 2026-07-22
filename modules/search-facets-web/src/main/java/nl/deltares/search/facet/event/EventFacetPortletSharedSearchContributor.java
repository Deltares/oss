package nl.deltares.search.facet.event;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;

@Component(
        immediate = true,
        property = {
                "jakarta.portlet.name=" + SearchModuleKeys.EVENT_FACET_PORTLET
        },
        service = PortletSharedSearchContributor.class
)
public class EventFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        final Group scopeGroup = portletSharedSearchSettings.getThemeDisplay().getScopeGroup();
        long groupId = scopeGroup.getGroupId();
        final Locale siteDefaultLocale = LocaleUtil.fromLanguageId(scopeGroup.getDefaultLanguageId());

        String[] structureKeys = null;
        String eventsList;

        try {
            EventFacetConfiguration eventPortletConfiguration = _configurationProvider.getPortletInstanceConfiguration(EventFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
            eventsList = eventPortletConfiguration.eventsList();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        try {
            DSDSiteConfiguration siteConfiguration = _configurationProvider.
                    getGroupConfiguration(DSDSiteConfiguration.class, groupId);
            structureKeys = FacetUtils.getStructureKeys(siteConfiguration);
            if (eventsList == null || eventsList.isEmpty()) {
                eventsList = String.valueOf(siteConfiguration.eventId());
            }
        } catch (ConfigurationException e) {
            LOG.debug("Could not get event configuration", e);
        }

        if (eventsList == null || eventsList.isEmpty()) {
            return;
        }
        String[] eventIds = eventsList.split(" ");
        //Store the eventIds in the session so they can be picked up by the calendar.
        FacetUtils.storeInSession("callendar", "eventIds", eventsList.replace(' ', ','), portletSharedSearchSettings.getRenderRequest());
        if (eventIds.length > 0) {
            _dsdJournalArticleUtils.queryDdmFieldValues(groupId, "eventId", eventIds, structureKeys,
                    portletSharedSearchSettings.getSearchContext(), siteDefaultLocale);
        }

    }


    @Reference
    private DsdJournalArticleUtils _dsdJournalArticleUtils;

    @Reference
    private ConfigurationProvider _configurationProvider;

    private static final Log LOG = LogFactoryUtil.getLog(EventFacetPortletSharedSearchContributor.class);
}