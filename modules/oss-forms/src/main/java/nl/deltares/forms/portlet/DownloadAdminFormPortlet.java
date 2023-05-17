package nl.deltares.forms.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import nl.deltares.portal.constants.OssConstants;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.version=3.0",
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=Download Admin Form",
                "javax.portlet.init-param.config-template=/admin/configuration/download_configuration.jsp",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/admin/download_admin.jsp",
                "javax.portlet.name=" + OssConstants.DOWNLOAD_ADMIN_FORM,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DownloadAdminFormPortlet extends MVCPortlet {

}