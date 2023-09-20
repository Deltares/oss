package nl.deltares.portal.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.portal.constants.OssConstants;

@ExtendedObjectClassDefinition(
        category = "oss-general",
        scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
        id = OssConstants.Sitemap_SYSTEM_CONFIGURATIONS_PID,
        localization = "content/Language", name = "Deltares sitemap configuration"
)
public interface SiteMapConfiguration {

    @Meta.AD(required = false, deflt = "", description = "Site ID of download portal")
    String downloadPortalSiteId();
}
