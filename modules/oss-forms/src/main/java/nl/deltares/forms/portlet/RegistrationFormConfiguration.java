package nl.deltares.forms.portlet;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.portal.constants.OssConstants;

@ExtendedObjectClassDefinition(
        category = "oss-general",
        scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
        id = OssConstants.REGISTRATIONFORM_CONFIGURATIONS_PID,
        localization = "content/Language", name = "registration-form-configuration"
)
public interface RegistrationFormConfiguration {

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

    @Meta.AD(required = false, deflt = "true", description = "Configure visibility of Badge info page.")
    boolean showBadgeInfo();



}
