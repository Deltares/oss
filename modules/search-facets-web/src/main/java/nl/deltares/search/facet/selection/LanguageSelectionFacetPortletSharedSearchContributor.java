package nl.deltares.search.facet.selection;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DDMStructureUtil;
import nl.deltares.search.constans.FacetPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;
import java.util.Optional;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + FacetPortletKeys.LANGUAGE_SELECTION_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class LanguageSelectionFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        final Group scopeGroup = portletSharedSearchSettings.getThemeDisplay().getScopeGroup();
        long groupId = scopeGroup.getGroupId();
        final Locale siteDefaultLocale = LocaleUtil.fromLanguageId(scopeGroup.getDefaultLanguageId());
        SelectionFacetConfiguration configuration;
        try {
            configuration = _configurationProvider.getPortletInstanceConfiguration(
                    SelectionFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
        } catch (ConfigurationException e) {
            LOG.debug("Could not get structures configuration", e);
            return;
        }

        String structureName = configuration.structureName().toLowerCase();
        String fieldName = configuration.fieldName();
        String name = structureName + '-' + fieldName;

        Optional<String> selectionOptional = portletSharedSearchSettings.getParameter(name);
        String selection;
        if (selectionOptional.isPresent()) {
            selection = selectionOptional.get();
            if (selection.equals("*")) return;
        } else {
            selection = portletSharedSearchSettings.getThemeDisplay().getLocale().getLanguage();
        }
        Optional<DDMStructure> ddmStructureOptional = _ddmStructureUtil.getDDMStructureByName(groupId, structureName, siteDefaultLocale);
        if (ddmStructureOptional.isPresent()){
            long ddmStructureId = ddmStructureOptional.get().getStructureId();
            String typeField = _ddmIndexer.encodeName(ddmStructureId, fieldName, siteDefaultLocale);
            portletSharedSearchSettings.addFacet(buildFacet(typeField, selection, portletSharedSearchSettings));
        }

    }

    protected Facet buildFacet(String fieldName, String type,
                               PortletSharedSearchSettings portletSharedSearchSettings) {

        _selectionFacetFactory.setField(fieldName);

        SelectionFacetBuilder selectionFacetBuilder = new SelectionFacetBuilder(_selectionFacetFactory);
        selectionFacetBuilder.setSearchContext(portletSharedSearchSettings.getSearchContext());
        selectionFacetBuilder.setSelection(type);
        return selectionFacetBuilder.build();
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

    @Reference
    private SelectionFacetFactory _selectionFacetFactory;

    private static final Log LOG = LogFactoryUtil.getLog(LanguageSelectionFacetPortletSharedSearchContributor.class);
}
