package nl.deltares.search.facet.topic;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.portal.kernel.search.facet.Facet;
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
        property = "javax.portlet.name=" + FacetPortletKeys.SESSION_TOPIC_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class SessionTopicFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        Locale locale = portletSharedSearchSettings.getThemeDisplay().getLocale();
        long groupId = portletSharedSearchSettings.getThemeDisplay().getScopeGroupId();
        Optional<String> sessionTopicOptional = portletSharedSearchSettings.getParameter("topic");

        String type = null;
        if (sessionTopicOptional.isPresent()) {
            type = sessionTopicOptional.get();
        }

        if (type != null) {
            String[] structures = new String[]{"SESSION"};
            for (String structure : structures) {
                Optional<DDMStructure> ddmStructureOptional = _ddmStructureUtil
                        .getDDMStructureByName(groupId, structure, locale);
                if (ddmStructureOptional.isPresent()) {
                    long ddmStructureId = ddmStructureOptional.get().getStructureId();
                    String typeField = _ddmIndexer.encodeName(ddmStructureId, "topic", locale);
                    portletSharedSearchSettings.addFacet(buildFacet(typeField, type, portletSharedSearchSettings));
                }

            }
        }

    }

    protected Facet buildFacet(String fieldName, String type,
                               PortletSharedSearchSettings portletSharedSearchSettings) {

        _sessionTypeFacetFactory.setField(fieldName);

        SessionTopicFacetBuilder sessionTypeFacetBuilder = new SessionTopicFacetBuilder(_sessionTypeFacetFactory);
        sessionTypeFacetBuilder.setSearchContext(portletSharedSearchSettings.getSearchContext());
        sessionTypeFacetBuilder.setSessionTopic(type);
        return sessionTypeFacetBuilder.build();
    }

    @Reference
    private DDMIndexer _ddmIndexer;

    @Reference
    private DDMStructureUtil _ddmStructureUtil;

    @Reference
    private SessionTopicFacetFactory _sessionTypeFacetFactory;
}
