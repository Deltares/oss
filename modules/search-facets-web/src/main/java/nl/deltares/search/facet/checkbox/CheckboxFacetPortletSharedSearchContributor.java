package nl.deltares.search.facet.checkbox;

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
        property = "javax.portlet.name=" + SearchModuleKeys.CHECKBOX_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class CheckboxFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Reference
    private DeltaresCacheUtils deltaresCacheUtils;

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {

        final Map<String, Object> portalCache = deltaresCacheUtils.findPortletConfig(portletSharedSearchSettings.getPortletId());

        if (portalCache == null) {
            return;
        }
        final boolean visible = portalCache.get("visible") != null && (Boolean) portalCache.get("visible");
        final boolean explicit = portalCache.get("explicitSearch") != null && (Boolean) portalCache.get("explicitSearch");
        String structureName = (String) portalCache.get("structureName");
        String fieldName = (String) portalCache.get("fieldName");
        String name = (String) portalCache.get("name"); //important to use '-' because this translates to JSP id
        final Long groupId = (Long) portalCache.get("groupId");
        final Locale siteDefaultLocale = (Locale) portalCache.get("siteDefaultLocale");

        String selection = null;
        Optional<String> facetSelection = portletSharedSearchSettings.getParameterOptional(name);
        if (facetSelection.isPresent()) {
            selection = facetSelection.get();
        } else if (!visible) {
            final boolean defaultValue = portalCache.get("defaultValue") != null && (Boolean)portalCache.get("defaultValue");
            selection = FacetUtils.serializeYesNo(defaultValue);
        }

        if (selection != null) {
            final Boolean option = FacetUtils.parseYesNo(selection);
            if (option != null) {
                if (explicit) {
                    //look only for article containing the search field
                    _dsdJournalArticleUtils.queryDdmFieldValue(groupId, fieldName, option.toString(), new String[]{structureName},
                            portletSharedSearchSettings.getSearchContext(), siteDefaultLocale);
                } else {
                    //exclude all articles containing the opposite value, allowing all articles without value to pass through
                    _dsdJournalArticleUtils.queryExcludeDdmFieldValue(groupId, fieldName, Boolean.toString(!option), new String[]{structureName},
                            portletSharedSearchSettings.getSearchContext(), siteDefaultLocale);
                }

            }
        }

    }

    @Reference
    private DsdJournalArticleUtils _dsdJournalArticleUtils;
}
