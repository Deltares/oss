package nl.deltares.search.facet.range;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.facet.DeltaresDdmDateRangeFacet;
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

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {

        try {
            RangeFacetConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(RangeFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
            String termFieldName = configuration.termFieldName();
            String upperValue = configuration.upperValue();
            String lowerValue = configuration.lowerValue();
            if (!(upperValue.isEmpty() && lowerValue.isEmpty())) {

                if (Boolean.parseBoolean(configuration.isDdmField())) {
                    portletSharedSearchSettings.addFacet(new DeltaresDdmDateRangeFacet(termFieldName, lowerValue, upperValue,
                            portletSharedSearchSettings.getSearchContext()));
                } else {
                    portletSharedSearchSettings.addFacet(new DeltaresRangeFieldValueFacet(termFieldName, lowerValue, upperValue,
                            portletSharedSearchSettings.getSearchContext()));
                }
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