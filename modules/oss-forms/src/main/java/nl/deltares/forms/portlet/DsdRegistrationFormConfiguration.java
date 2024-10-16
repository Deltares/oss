package nl.deltares.forms.portlet;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.portal.constants.OssConstants;

@ExtendedObjectClassDefinition(
        category = "oss-general",
        scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
        id = OssConstants.DSD_REGISTRATIONFORM_CONFIGURATIONS_PID,
        localization = "content/Language", name = "dsd-registration-form-configuration"
)
public interface DsdRegistrationFormConfiguration {

    @Meta.AD(required = false, deflt = "", description = "Configure the friendly URL of the registration success page.")
    String registerSuccessURL();

    @Meta.AD(required = false, deflt = "", description = "Configure the friendly URL of the unregister success page.")
    String unregisterSuccessURL();

    @Meta.AD(required = false, deflt = "", description = "Configure the friendly URL of the update success page." )
    String updateSuccessURL();

    @Meta.AD(required = false, deflt = "", description = "Configure the friendly URL of the registration failure page.")
    String failURL();

    @Meta.AD(required = false, deflt = "{}", description = "Configure the text to display above the child headers.")
    String childHeaderText();


}
