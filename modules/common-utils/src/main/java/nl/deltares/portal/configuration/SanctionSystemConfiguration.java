package nl.deltares.portal.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.portal.constants.OssConstants;

@ExtendedObjectClassDefinition(
        category = "oss-general",
        scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
        id = OssConstants.Sanction_SYSTEM_CONFIGURATIONS_PID,
        localization = "content/Language", name = "sanction-system-configuration"
)
public interface SanctionSystemConfiguration {

    @Meta.AD(required = false, deflt = "", description = "List of sanctioned countries in ISO code 2 format (';' separated )")
    String sanctionCountryIsoCodes();
}
