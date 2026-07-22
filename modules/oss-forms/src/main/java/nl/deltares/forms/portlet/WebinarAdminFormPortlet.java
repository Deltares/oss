package nl.deltares.forms.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import nl.deltares.portal.constants.OssConstants;
import org.osgi.service.component.annotations.Component;

import jakarta.portlet.Portlet;

/**
 * @author rooij_e
 */
@Component(
	immediate = true,
	property = {
			"jakarta.portlet.version=4.0",
			"com.liferay.portlet.display-category=OSS",
			"com.liferay.portlet.header-portlet-css=/css/main.css",
			"com.liferay.portlet.instanceable=true",
			"jakarta.portlet.display-name=Webinar Admin Form",
			"jakarta.portlet.init-param.config-template=/admin/configuration/web_configuration.jsp",
			"jakarta.portlet.init-param.template-path=/",
			"jakarta.portlet.init-param.view-template=/admin/web_admin.jsp",
			"jakarta.portlet.name=" + OssConstants.WEBINAR_ADMIN_FORM,
			"jakarta.portlet.resource-bundle=content.Language",
			"jakarta.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class WebinarAdminFormPortlet extends MVCPortlet {

}