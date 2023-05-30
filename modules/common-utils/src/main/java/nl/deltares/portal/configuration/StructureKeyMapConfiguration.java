package nl.deltares.portal.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.portal.constants.OssConstants;

@ExtendedObjectClassDefinition(
        category = "oss-general",
        scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
        id = OssConstants.STRUCTURE_KEY_MAP_CONFIGURATIONS_PID,
        localization = "content/Language", name = "structure-key-map-configuration"
)
public interface StructureKeyMapConfiguration {

    @Meta.AD(required = false, deflt = "{}", description = "JSON mapping to map structure keys to internal names.")
    String structureKeyMap();

}
