package nl.deltares.search.facet.terms;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.facet.DeltaresTermFieldValueFacet;
import nl.deltares.search.facet.DeltaresTermsFieldValueFacet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Arrays;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + SearchModuleKeys.TERMS_FACET_PORTLET
        },
        service = PortletSharedSearchContributor.class
)
public class TermsFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {

        //Only return latest version of article
        portletSharedSearchSettings.addFacet(new DeltaresTermFieldValueFacet("head", "true",
                portletSharedSearchSettings.getSearchContext()));
        //Only return active articles
        portletSharedSearchSettings.addFacet(new DeltaresTermsFieldValueFacet("status", new String[]{"0"},
                portletSharedSearchSettings.getSearchContext()));

        SearchRequestBuilder searchRequestBuilder = portletSharedSearchSettings.getSearchRequestBuilder();
        try {
            TermsFacetConfiguration termsPortletConfiguration = _configurationProvider.getPortletInstanceConfiguration(TermsFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());

            String companyId = termsPortletConfiguration.companyId();
            if (!companyId.isEmpty()) {
                searchRequestBuilder.companyId(Long.parseLong(companyId));
                searchRequestBuilder.addIndex("liferay-" + companyId);
            }

            String groupIdsConfig = termsPortletConfiguration.groupIds();
            if (!groupIdsConfig.isEmpty()) {
                String[] groupIds = groupIdsConfig.split(" ");
                long[] array = Arrays.stream(groupIds).mapToLong(Long::parseLong).toArray();
                searchRequestBuilder.groupIds(array);
                portletSharedSearchSettings.addFacet(new DeltaresTermsFieldValueFacet("groupId", groupIds,
                        portletSharedSearchSettings.getSearchContext()));
            }

            String articleIdsConfig = termsPortletConfiguration.articleIds();
            if (articleIdsConfig.isEmpty()) {
                String ddmStructureKey = termsPortletConfiguration.ddmStructureKey();
                if (!ddmStructureKey.isEmpty()) {
                    portletSharedSearchSettings.addFacet(new DeltaresTermFieldValueFacet("ddmStructureKey", ddmStructureKey,
                            portletSharedSearchSettings.getSearchContext()));
                }
            } else {
                String[] articleIds = articleIdsConfig.split(" ");
                portletSharedSearchSettings.addFacet(new DeltaresTermsFieldValueFacet("articleId", articleIds,
                        portletSharedSearchSettings.getSearchContext()));
            }

        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }

    }

    @Reference
    private ConfigurationProvider _configurationProvider;
}