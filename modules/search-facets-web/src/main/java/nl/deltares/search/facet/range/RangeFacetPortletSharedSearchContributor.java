package nl.deltares.search.facet.range;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.facet.DeltaresRangeFieldValueFacet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + SearchModuleKeys.RANGE_FACET_PORTLET
        },
        service = PortletSharedSearchContributor.class
)
public class RangeFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    private static final Log LOG = LogFactoryUtil.getLog(RangeFacetPortletSharedSearchContributor.class);

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {

        try {
            RangeFacetConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(RangeFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());

            String companyIds = configuration.companyIds();
            if (!companyIds.isEmpty()) {
                String[] ids = companyIds.split(" ");
                _dsdJournalArticleUtils.addCompanyIndexers(portletSharedSearchSettings, ids);
            }

            String groupIdsConfig = configuration.groupIds();
            if (!groupIdsConfig.isEmpty()) {
                String[] groupIds = groupIdsConfig.split(" ");
                try {
                    _dsdJournalArticleUtils.addTermsFacet(portletSharedSearchSettings, "groupId", groupIds, false);
                } catch (PortalException e) {
                    LOG.warn(e);
                }
            }

            String termFieldName = configuration.termFieldName();
            String upperValue = configuration.upperValue();
            String lowerValue = configuration.lowerValue();
            if (!(upperValue.isEmpty() && lowerValue.isEmpty())) {
                portletSharedSearchSettings.addFacet(new DeltaresRangeFieldValueFacet(termFieldName, upperValue, lowerValue,
                        portletSharedSearchSettings.getSearchContext()));
            }

        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }

    }

    @Reference
    private ConfigurationProvider _configurationProvider;

    @Reference
    private DsdJournalArticleUtils _dsdJournalArticleUtils;

}