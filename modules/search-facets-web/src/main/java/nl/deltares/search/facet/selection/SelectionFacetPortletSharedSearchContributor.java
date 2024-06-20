package nl.deltares.search.facet.selection;

import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DeltaresCacheUtils;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + SearchModuleKeys.SELECTION_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class SelectionFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Reference
    private DeltaresCacheUtils deltaresCacheUtils;

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {

        final Map<String, Object> portalCache = deltaresCacheUtils.findPortletConfig(portletSharedSearchSettings.getPortletId());

        if (portalCache == null) {
            return;
        }
        final String structureName = (String) portalCache.get("structureName");
        final String fieldName = (String) portalCache.get("fieldName");
        final String name = (String) portalCache.get("name");
        final Long groupId = (Long) portalCache.get("groupId");
        final Locale siteDefaultLocale = (Locale) portalCache.get("siteDefaultLocale");

        Optional<String> selectionOptional = portletSharedSearchSettings.getParameterOptional(name);
        //check for parameter is in namespace of searchResultsPortlet
        String selection = selectionOptional.orElseGet(() -> FacetUtils.getRequestParameter(name, portletSharedSearchSettings.getRenderRequest()));
        if (selection == null) {
            return;
        }

        _dsdJournalArticleUtils.queryDdmFieldValue(groupId, fieldName, selection, new String[]{structureName},
                portletSharedSearchSettings.getSearchContext(), siteDefaultLocale);

    }

    @Reference
    private DsdJournalArticleUtils _dsdJournalArticleUtils;

}
