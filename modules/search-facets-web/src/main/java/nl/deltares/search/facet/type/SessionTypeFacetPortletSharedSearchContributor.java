package nl.deltares.search.facet.type;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DDMStructureUtil;
import nl.deltares.search.constans.FacetPortletKeys;
import nl.deltares.search.facet.topic.SessionTopicFacetBuilder;
import nl.deltares.search.facet.topic.SessionTopicFacetFactory;
import nl.deltares.search.facet.type.builder.SessionTypeFacetBuilder;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;
import java.util.Optional;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + FacetPortletKeys.SESSION_TYPE_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class SessionTypeFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        Locale locale = portletSharedSearchSettings.getThemeDisplay().getLocale();
        long groupId = portletSharedSearchSettings.getThemeDisplay().getScopeGroupId();
        Optional<String> sessionTypeOptional = portletSharedSearchSettings.getParameter("type");

        String type = null;
        if (sessionTypeOptional.isPresent()) {
            type = sessionTypeOptional.get();
        }

        if (type != null) {
            String[] structures = new String[]{"SESSION"};
            for (String structure : structures) {
                Optional<DDMStructure> ddmStructureOptional = _ddmStructureUtil
                        .getDDMStructureByName(groupId, structure, locale);
                if (ddmStructureOptional.isPresent()) {
                    long ddmStructureId = ddmStructureOptional.get().getStructureId();
                    String typeField = _ddmIndexer.encodeName(ddmStructureId, "type", locale);
                    portletSharedSearchSettings.addFacet(buildFacet(typeField, type, portletSharedSearchSettings));
                }

            }
        }

    }

    protected Facet buildFacet(String fieldName, String type,
                               PortletSharedSearchSettings portletSharedSearchSettings) {

        _sessionTypeFacetFactory.setField(fieldName);

        SessionTypeFacetBuilder sessionTypeFacetBuilder = new SessionTypeFacetBuilder(_sessionTypeFacetFactory);
        sessionTypeFacetBuilder.setSearchContext(portletSharedSearchSettings.getSearchContext());
        sessionTypeFacetBuilder.setSessionType(type);
        return sessionTypeFacetBuilder.build();
    }

    @Reference
    private DDMIndexer _ddmIndexer;

    @Reference
    private DDMStructureUtil _ddmStructureUtil;

    @Reference
    private SessionTopicFacetFactory _sessionTypeFacetFactory;
}
