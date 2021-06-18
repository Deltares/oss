package nl.deltares.search.facet.presentation;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
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
        property = "javax.portlet.name=" + FacetPortletKeys.PRESENTATION_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class PresentationFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {


    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        final Group scopeGroup = portletSharedSearchSettings.getThemeDisplay().getScopeGroup();
        long groupId = scopeGroup.getGroupId();
        final Locale siteDefaultLocale = LocaleUtil.fromLanguageId(scopeGroup.getDefaultLanguageId());
        Optional<String> hasPresentations = portletSharedSearchSettings.getParameter("hasPresentations");

        boolean onlyShowPresentations = false;
        if (hasPresentations.isPresent()) {
            onlyShowPresentations = Boolean.parseBoolean(hasPresentations.get());
        }

        if (onlyShowPresentations) {
            Optional<DDMStructure> ddmStructureOptional = _ddmStructureUtil
                    .getDDMStructureByName(groupId, "SESSION", siteDefaultLocale);
            if (ddmStructureOptional.isPresent()) {
                long ddmStructureId = ddmStructureOptional.get().getStructureId();
                String fieldName = _ddmIndexer.encodeName(ddmStructureId, "hasPresentations");
                portletSharedSearchSettings.addCondition(BooleanClauseFactoryUtil.create(fieldName, "true", BooleanClauseOccur.MUST.getName()));
            }
        }

    }

    @Reference
    private DDMIndexer _ddmIndexer;


    @Reference
    private DDMStructureUtil _ddmStructureUtil;
}
