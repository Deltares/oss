package nl.deltares.search.facet.checkbox;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;

@Component(
        immediate = true,
        property = "jakarta.portlet.name=" + SearchModuleKeys.CHECKBOX_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class CheckboxFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {


    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        final Group scopeGroup = portletSharedSearchSettings.getThemeDisplay().getScopeGroup();
        long groupId = scopeGroup.getGroupId();
        final Locale siteDefaultLocale = LocaleUtil.fromLanguageId(scopeGroup.getDefaultLanguageId());
        CheckboxFacetConfiguration checkboxFacetConfiguration;
        try {
            checkboxFacetConfiguration = _configurationProvider.getPortletInstanceConfiguration(
                    CheckboxFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }

        final boolean explicit = Boolean.parseBoolean(checkboxFacetConfiguration.explicitSearch());
        String structureName = checkboxFacetConfiguration.structureName();
        String fieldName = checkboxFacetConfiguration.fieldName();
        String name = structureName + '-' + fieldName; //important to use '-' because this translates to JSP id
        boolean defaultValue = Boolean.parseBoolean(checkboxFacetConfiguration.defaultValue());

        String selection = portletSharedSearchSettings.getParameter(name);
        //check for parameter is in namespace of searchResultsPortlet

        if (selection == null) {
            selection = (String) FacetUtils.getFromSession(
                    portletSharedSearchSettings.getPortletId(),
                    name, portletSharedSearchSettings.getRenderRequest());
        }
        final boolean option;
        if (selection == null){
            if (Boolean.parseBoolean(checkboxFacetConfiguration.visible())){
                return; //nothing selected so do not filter.
            }
            option = defaultValue;
        } else {
            option = Boolean.TRUE.equals(FacetUtils.parseYesNo(selection));
        }

        if (explicit) {
            //look only for article containing the search field
            _dsdJournalArticleUtils.queryDdmFieldValue(groupId, fieldName, Boolean.toString(option), new String[]{structureName},
                    portletSharedSearchSettings.getSearchContext(), siteDefaultLocale);
        } else {
            //exclude all articles containing the opposite value, allowing all articles without value to pass through
            _dsdJournalArticleUtils.queryExcludeDdmFieldValue(groupId, fieldName, Boolean.toString(!option), new String[]{structureName},
                    portletSharedSearchSettings.getSearchContext(), siteDefaultLocale);
        }

    }

    @Reference
    private ConfigurationProvider _configurationProvider;

    @Reference
    private DsdJournalArticleUtils _dsdJournalArticleUtils;
}
