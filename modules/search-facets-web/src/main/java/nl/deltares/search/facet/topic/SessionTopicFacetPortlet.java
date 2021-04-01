package nl.deltares.search.facet.topic;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.FacetPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * @author allan
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.css-class-wrapper=portlet-session-topic-facet",
                "com.liferay.portlet.display-category=OSS-search",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=SessionTopicFacet",
                "javax.portlet.expiration-cache=0",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/facet/topic/view.jsp",
                "javax.portlet.name=" + FacetPortletKeys.SESSION_TOPIC_FACET_PORTLET,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class SessionTopicFacetPortlet extends MVCPortlet {

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        PortletSharedSearchResponse portletSharedSearchResponse = portletSharedSearchRequest.search(renderRequest);
        Optional<String> typeOptional = portletSharedSearchResponse.getParameter("topic", renderRequest);

        String type = null;
        if (typeOptional.isPresent()) {
            type = typeOptional.get();
        }
        renderRequest.setAttribute("topic", type);

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

        try {
            Map<String, String> topicMap = dsdJournalArticleUtils.getStructureFieldOptions(themeDisplay.getSiteGroupId(),
                    DsdArticle.DSD_STRUCTURE_KEYS.Session.name().toUpperCase(),
                    "topic", themeDisplay.getLocale());
            renderRequest.setAttribute("topicMap", topicMap);
        } catch (PortalException e) {
            throw new PortletException("Could not get options for field 'topic' in structure SESSIONS: " + e.getMessage(), e);
        }

        super.render(renderRequest, renderResponse);
    }

    @Reference
    protected PortletSharedSearchRequest portletSharedSearchRequest;

    @Reference
    DsdJournalArticleUtils dsdJournalArticleUtils;
}