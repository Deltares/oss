package nl.deltares.portal.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.portal.constants.OssConstants;

@ExtendedObjectClassDefinition(
        category = "oss-general",
        scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
        id = OssConstants.Download_SITE_CONFIGURATIONS_PID,
        localization = "content/Language", name = "download-site-configuration"
)
public interface DownloadSiteConfiguration {

    @Meta.AD(required = false, deflt = "{}", description = "Map search results portlet to display template.")
    String templateMap();

    @Meta.AD(required = false, deflt = "", description = "Configure the friendly URL of the download form page")
    String downloadURL();
}
