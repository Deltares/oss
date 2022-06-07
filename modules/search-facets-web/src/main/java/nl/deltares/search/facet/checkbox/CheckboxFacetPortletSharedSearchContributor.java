package nl.deltares.search.facet.checkbox;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DDMStructureUtil;
import nl.deltares.search.constans.FacetPortletKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;
import java.util.Optional;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + FacetPortletKeys.CHECKBOX_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class CheckboxFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        final Group scopeGroup = portletSharedSearchSettings.getThemeDisplay().getScopeGroup();
        long groupId = scopeGroup.getGroupId();
        final Locale siteDefaultLocale = LocaleUtil.fromLanguageId(scopeGroup.getDefaultLanguageId());
        ThemeDisplay themeDisplay = portletSharedSearchSettings.getThemeDisplay();
        CheckboxFacetConfiguration _configuration;
        try {
            _configuration = _configurationProvider.getPortletInstanceConfiguration(
                    CheckboxFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
        } catch (ConfigurationException e) {
            LOG.debug(String.format("Could not get configuration for portlet '%s': %s", themeDisplay.getPortletDisplay().getId(), e.getMessage()), e);
            return;
        }

        String structureName = _configuration.structureName().toLowerCase();
        String fieldName = _configuration.fieldName();
        String name = structureName + '-' + fieldName; //important to use '-' because this translates to JSP id

        Optional<String> facetSelection = portletSharedSearchSettings.getParameter(name);
        if (facetSelection.isPresent()) {
            String selection = facetSelection.get();
            final Boolean option = FacetUtils.parseYesNo(selection);
            Optional<DDMStructure> ddmStructureOptional = _ddmStructureUtil.getDDMStructureByName(groupId, structureName, siteDefaultLocale);
            if (option != null && ddmStructureOptional.isPresent()) {
                long ddmStructureId = ddmStructureOptional.get().getStructureId();
                String encodeName = _ddmIndexer.encodeName(ddmStructureId, fieldName, siteDefaultLocale);
                portletSharedSearchSettings.addCondition(BooleanClauseFactoryUtil.create(encodeName, option.toString(), BooleanClauseOccur.MUST.getName()));
            }
        }

    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    @Reference
    private DDMIndexer _ddmIndexer;

    @Reference
    private DDMStructureUtil _ddmStructureUtil;

    private static final Log LOG = LogFactoryUtil.getLog(CheckboxFacetPortletSharedSearchContributor.class);
}
