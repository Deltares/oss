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

    @Meta.AD(required = false, deflt = "", description = "Configure the friendly URL of the download form page")
    String downloadURL();

    @Meta.AD(required = false, deflt = "{}", description = "Configure the URL where the Privacy Policy of Deltares can be found")
    String privacyURL();

    @Meta.AD(required = false, deflt = "{}", description = "Configure the URL where the Privacy Policy of Deltares can be found")
    String contactURL();

    @Meta.AD(required = false, deflt = "mydeltares@deltares.nl", description = "Configure the Send From email address")
    String sendFromEmail();

    @Meta.AD(required = false, deflt = "", description = "Configure the Reply To email address")
    String replyToEmail();

    @Meta.AD(required = false, deflt = "", description = "Configure the BCC email addresses (';' separated )")
    String bccToEmail();

    @Meta.AD(required = false, deflt = "", description = "Configure the URL where email banner image can be found.")
    String bannerURL();

    @Meta.AD(required = false, deflt = "true", description = "Configure if to send email notifications.")
    boolean enableEmails();
}
