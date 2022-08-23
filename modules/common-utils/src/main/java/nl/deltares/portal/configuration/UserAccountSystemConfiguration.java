package nl.deltares.portal.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.portal.constants.OssConstants;

@ExtendedObjectClassDefinition(
        category = "oss-general",
        scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
        id = OssConstants.USER_ACCOUNT_SYSTEM_CONFIGURATIONS_PID,
        localization = "content/Language", name = "user-account-system-configuration"
)
public interface UserAccountSystemConfiguration {

    @Meta.AD(required = false, deflt = "(?=^.{8,}$)(?=.*\\d)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", description = "Regular expression defining password complexity. Keep in mind to escape '\\' characters in expression")
    String passwordComplexityRegularExpression();

    @Meta.AD(required = false, deflt = "<ul>" +
            "<li>The password length must be greater than or equal to 8</li>" +
            "<li>The password must contain one or more uppercase characters</li>" +
            "<li>The password must contain one or more lowercase characters</li>" +
            "<li>The password must contain one or more numeric values</li>" +
            "</ul>", description = "Message to show on validation of password rules. Possible to use HTML encoding.")
    String passwordComplexityMessage();

}
