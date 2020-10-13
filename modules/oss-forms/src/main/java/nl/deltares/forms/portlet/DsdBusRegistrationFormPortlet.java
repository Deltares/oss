package nl.deltares.forms.portlet;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.forms.constants.OssFormPortletKeys;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.utils.DDMStructureUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdTransferUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.Optional;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=false",
                "javax.portlet.display-name=DsdBusRegistrationForm",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/bus_registration/view.jsp",
                "javax.portlet.name=" + OssFormPortletKeys.DSD_BUS_REGISTRATION_FORM,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DsdBusRegistrationFormPortlet extends MVCPortlet {

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        long groupId = themeDisplay.getScopeGroupId();

        try {
            DSDSiteConfiguration configuration = _configurationProvider
                    .getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());

            Event event = parserUtils.getEvent(groupId, String.valueOf(configuration.eventId()));
            renderRequest.setAttribute("event", event);
            renderRequest.setAttribute("transfers", event.getBusTransfers());
        } catch (Exception e) {
            LOG.debug("Could not get configuration instance", e);
        }

        Optional<DDMTemplate> ddmTemplateOptional = _ddmStructureUtil
                .getDDMTemplateByName("BUSTRANSFER", themeDisplay.getLocale());

        ddmTemplateOptional.ifPresent(ddmTemplate ->
                renderRequest.setAttribute("ddmTemplateKey", ddmTemplate.getTemplateKey()));

        renderRequest.setAttribute("dsdTransferUtils", dsdTransferUtils);

        super.render(renderRequest, renderResponse);
    }

    @Reference
    DsdParserUtils parserUtils;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    @Reference
    private DDMStructureUtil _ddmStructureUtil;

    @Reference
    private DsdTransferUtils dsdTransferUtils;

    private static final Log LOG = LogFactoryUtil.getLog(DsdBusRegistrationFormPortlet.class);
}
