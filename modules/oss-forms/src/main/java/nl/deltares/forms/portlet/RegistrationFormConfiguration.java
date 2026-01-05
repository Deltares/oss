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

    @Meta.AD(required = false, deflt = "" , description = "Configure the display template for selected assets")
    String selectedAssetsTemplate();

    @Meta.AD(required = false, deflt = "" , description = "Configure the display template for related assets")
    String relatedAssetsTemplate();

    @Meta.AD(required = false, deflt = "true", description = "Configure visibility of Badge info page.")
    Boolean showBadgeInfo();

    @Meta.AD(required = false, deflt = "false", description = "Configure visibility of Related info page. If 'true' then always show page. If 'false' only show page when there is related info to be shown.")
    Boolean alwaysShowRelatedInfo();


}
