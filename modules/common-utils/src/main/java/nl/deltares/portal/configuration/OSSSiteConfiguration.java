package nl.deltares.portal.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.portal.constants.OssConstants;

@ExtendedObjectClassDefinition(
        category = "oss-general",
        scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
        id = OssConstants.OSS_SITE_CONFIGURATIONS_PID,
        localization = "content/Language", name = "oss-site-configuration"
)
public interface OSSSiteConfiguration {

    @Meta.AD(required = false, deflt = "false", description = "Enable site selection on OSS admin configuration page.")
    boolean enableSiteId();

}
