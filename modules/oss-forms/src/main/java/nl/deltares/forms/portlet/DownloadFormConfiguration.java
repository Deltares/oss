package nl.deltares.forms.portlet;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.portal.constants.OssConstants;

@ExtendedObjectClassDefinition(
        category = "oss-general",
        scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
        id = OssConstants.DOWNLOADFORM_CONFIGURATIONS_PID,
        localization = "content/Language", name = "download-form-configuration"
)
public interface DownloadFormConfiguration {

    @Meta.AD(required = false, deflt = "", description = "Configure the friendly URL of the success page.")
    String successURL();

    @Meta.AD(required = false, deflt = "", description = "Configure the friendly URL of the failure page.")
    String failureURL();

}
