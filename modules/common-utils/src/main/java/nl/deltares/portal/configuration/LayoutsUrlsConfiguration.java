package nl.deltares.portal.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.portal.constants.DsdConstants;

@ExtendedObjectClassDefinition(
        scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
        id = DsdConstants.LAYOUTS_URLS_CONFIGURATIONS_PID
)
public interface LayoutsUrlsConfiguration {

    @Meta.AD(required = false, deflt = "", description = "Configure the friendly URL of the registration form page")
    String registrationURL();
}
