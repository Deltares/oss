package nl.deltares.search.facet.event;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;
import java.util.Optional;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + SearchModuleKeys.EVENT_FACET_PORTLET
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
        try {
            DSDSiteConfiguration configuration = _configurationProvider.
                    getGroupConfiguration(DSDSiteConfiguration.class, groupId);
            structureKeys = FacetUtils.getStructureKeys(configuration);
        } catch (ConfigurationException e) {
            LOG.debug("Could not get event configuration", e);
        }
        String[] eventIds = getEventIds(portletSharedSearchSettings);
        if (eventIds.length > 0) {
            _dsdJournalArticleUtils.queryDdmFieldValues(groupId, "eventId", eventIds, structureKeys,
                    portletSharedSearchSettings.getSearchContext(), siteDefaultLocale);
        }

    }

    private String[] getEventIds(PortletSharedSearchSettings portletSharedSearchSettings) {

        Optional<String> optional = portletSharedSearchSettings.getParameterOptional("eventsList");
        return optional.map(s -> s.split(" ")).orElseGet(() -> {
            String structureList = getConfiguredValue(portletSharedSearchSettings);
            if (structureList != null && !structureList.isEmpty()) {
                return StringUtil.split(structureList, ' ');
            }
            return new String[0];
        });
    }

    private String getConfiguredValue(PortletSharedSearchSettings portletSharedSearchSettings) {

        try {
            EventFacetConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(EventFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
            String overrulingEvents = configuration.eventsList();
            if (overrulingEvents.isEmpty()) {

                DSDSiteConfiguration siteConfiguration = _configurationProvider
                        .getGroupConfiguration(DSDSiteConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getSiteGroupId());
                return String.valueOf(siteConfiguration.eventId());
            }
            return overrulingEvents;
        } catch (ConfigurationException e) {
            LOG.warn("Could not find configuration for eventsList", e);
        }
        return null;
    }

    @Reference
    private DsdJournalArticleUtils _dsdJournalArticleUtils;

    @Reference
    protected PortletSharedSearchRequest portletSharedSearchRequest;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(EventFacetPortletSharedSearchContributor.class);
}