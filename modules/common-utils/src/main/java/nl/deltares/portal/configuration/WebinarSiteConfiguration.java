package nl.deltares.portal.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.portal.constants.OssConstants;

@ExtendedObjectClassDefinition(
        category = "oss-general",
        scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
        id = OssConstants.WEBINAR_SITE_CONFIGURATIONS_PID,
        localization = "content/Language", name = "webinar-site-configuration"
)
public interface WebinarSiteConfiguration {

    @Meta.AD(required = false, deflt = "https://api.getgo.com/", description = "Base URL of GOTO website")
    String gotoURL();

    @Meta.AD(required = false, deflt = "", description = "GOTO Client Id for this site")
    String gotoClientId();

    @Meta.AD(required = false, deflt = "", description = "GOTO Client Secret for this site")
    String gotoClientSecret();

    @Meta.AD(required = false, deflt = "", description = "GOTO User for this site")
    String gotoUserName();

    @Meta.AD(required = false, deflt = "",type = Meta.Type.Password, description = "GOTO User password for this site")
    String gotoUserPassword();

    @Meta.AD(required = false, deflt = "true",type = Meta.Type.Boolean, description = "Cache GOTO tokens for this site?")
    boolean gotoCacheToken();

    @Meta.AD(required = false, deflt = "https://deltares.anewspring.nl/api/", description = "Base URL of aNewSpring website")
    String aNewSpringURL();

    @Meta.AD(required = false, deflt = "", description = "aNewSpring API key for this site")
    String aNewSpringApiKey();

    @Meta.AD(required = false, deflt = "true", type = Meta.Type.Boolean, description = "Cache aNewSpring tokens for this site?")
    boolean aNewSpringCacheToken();


}


