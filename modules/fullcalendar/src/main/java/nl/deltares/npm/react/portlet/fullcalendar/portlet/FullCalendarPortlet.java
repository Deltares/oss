package nl.deltares.npm.react.portlet.fullcalendar.portlet;

import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import nl.deltares.npm.react.portlet.fullcalendar.constants.FullCalendarPortletKeys;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DsdParserUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Optional;

/**
 * @author rooij_e
 */
@Component(
        configurationPid = "nl.deltares.npm.react.portlet.fullcalendar.portlet.FullCalendarConfiguration",
        immediate = true,
        property = {
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-js=/lib/index.es.js",
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=FullCalendar Portlet",
                "javax.portlet.init-param.config-template=/configuration.jsp",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=" + FullCalendarPortletKeys.FullCalendar,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class

)
public class FullCalendarPortlet extends MVCPortlet {

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        PortletSharedSearchResponse portletSharedSearchResponse = portletSharedSearchRequest.search(renderRequest);
        Optional<String> startDateOptional = portletSharedSearchResponse.getParameter("startDate", renderRequest);
        startDateOptional.ifPresent(s -> {
            try {
                renderRequest.setAttribute("startDate", dateFormat.parse(s));
            } catch (ParseException e) {
                //
            }
        });

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        try {
            Map<String, String> typeMap = dsdJournalArticleUtils.getStructureFieldOptions(themeDisplay.getSiteGroupId(),
                    DsdArticle.DSD_STRUCTURE_KEYS.Session.name().toUpperCase(),
                    "type", themeDisplay.getLocale());
            renderRequest.setAttribute("typeMap", typeMap);
        } catch (PortalException e) {
            throw new PortletException("Could not get options for field 'type' in structure SESSIONS: " + e.getMessage(), e);
        }

        super.render(renderRequest, renderResponse);
    }

    @Override
    public void doView(
            RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        JSPackage jsPackage = _npmResolver.getJSPackage();

        renderRequest.setAttribute(
                FullCalendarPortletKeys.BOOTSTRAP_REQUIRE,
                jsPackage.getResolvedId() + " as bootstrapRequire");

        renderRequest.setAttribute(
                FullCalendarConfiguration.class.getName(),
                _configuration);

        renderRequest.setAttribute(
                "mainRequire",
                _npmResolver.resolveModuleName("fullcalendar") + " as main");

        renderRequest.setAttribute(DsdParserUtils.class.getName(), dsdParserUtils);

        renderRequest.setAttribute(ConfigurationProvider.class.getName(), _configurationProvider);

        super.doView(renderRequest, renderResponse);
    }

    @Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
        _configuration = ConfigurableUtil.createConfigurable(
                FullCalendarConfiguration.class, properties);
    }


    @Reference
    protected PortletSharedSearchRequest portletSharedSearchRequest;

    @Reference
    private NPMResolver _npmResolver;

    @Reference
    private DsdParserUtils dsdParserUtils;

    @Reference
    DsdJournalArticleUtils dsdJournalArticleUtils;

    private volatile FullCalendarConfiguration _configuration;

    private ConfigurationProvider _configurationProvider;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }
}