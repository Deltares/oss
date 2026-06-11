package nl.deltares.search.facet.range;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.facet.DeltaresRangeFieldValueFacet;
import nl.deltares.search.facet.DeltaresTermFieldValueFacet;
import nl.deltares.search.facet.DeltaresTermsFieldValueFacet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + SearchModuleKeys.RANGE_FACET_PORTLET
        },
        service = PortletSharedSearchContributor.class
)
public class RangeFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {

        Map<String, Facet> existringFacets = portletSharedSearchSettings.getSearchContext().getFacets();
        //Only return latest version of article
        if (!existringFacets.containsKey("head")) {
            portletSharedSearchSettings.addFacet(new DeltaresTermFieldValueFacet("head", "true",
                    portletSharedSearchSettings.getSearchContext()));
        }
        //Only return active articles
        if (!existringFacets.containsKey("status")) {
            portletSharedSearchSettings.addFacet(new DeltaresTermsFieldValueFacet("status", new String[]{"0"},
                    portletSharedSearchSettings.getSearchContext()));
        }

        SearchRequestBuilder searchRequestBuilder = portletSharedSearchSettings.getSearchRequestBuilder();
        try {
            RangeFacetConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(RangeFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());

            String companyIds = configuration.companyIds();
            if (!companyIds.isEmpty()) {
                Arrays.stream(companyIds.split(" ")).forEach(companyId ->
                    searchRequestBuilder.addIndex("liferay-" + companyId)
                );
            }

            String groupIdsConfig = configuration.groupIds();
            if (!groupIdsConfig.isEmpty()) {
                String[] groupIds = groupIdsConfig.split(" ");
                Facet groupIdFacet = existringFacets.get("groupId");
                if (groupIdFacet == null) {
                    portletSharedSearchSettings.addFacet(new DeltaresTermsFieldValueFacet("groupId", groupIds,
                            portletSharedSearchSettings.getSearchContext()));
                } else {
                    ((DeltaresTermsFieldValueFacet)groupIdFacet).addValues(groupIds);
                }
            }

            String termFieldName = configuration.termFieldName();
            String upperValue = configuration.upperValue();
            String lowerValue = configuration.lowerValue();
            if (!(upperValue.isEmpty() && lowerValue.isEmpty()) ) {
                portletSharedSearchSettings.addFacet(new DeltaresRangeFieldValueFacet(termFieldName, upperValue, lowerValue,
                        portletSharedSearchSettings.getSearchContext()));
            }

        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }

    }

    @Reference
    private ConfigurationProvider _configurationProvider;

}