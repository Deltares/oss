package nl.deltares.portal.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.portal.constants.DsdConstants;

@ExtendedObjectClassDefinition(
        category = "dsd-general",
        scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
        id = DsdConstants.DSD_SITE_CONFIGURATIONS_PID,
        localization = "content/Language", name = "dsd-site-configuration"
)
public interface DSDSiteConfiguration {

    @Meta.AD(required = false, deflt = "0")
    long eventId();

    @Meta.AD(required = false, deflt = "", description = "Configure the friendly URL of the registration form page")
    String registrationURL();

    @Meta.AD(required = false, deflt = "", description = "Configure the friendly URL of the bus transfer page")
    String busTransferURL();

    @Meta.AD(required = false, deflt = "", description = "Configure the friendly URL of the travel and stay page")
    String travelStayURL();

    @Meta.AD(required = false, deflt = "", description = "Configure the URL where the General Course Conditions of Deltares can be found")
    String conditionsURL();

    @Meta.AD(required = false, deflt = "/privacy-declaration", description = "Configure the URL where the Privacy Policy of Deltares can be found")
    String privacyURL();

    @Meta.AD(required = false, deflt = "/contact", description = "Configure the URL where the Privacy Policy of Deltares can be found")
    String contactURL();

    @Meta.AD(required = false, deflt = "mydeltares@deltares.nl", description = "Configure the Send From email address")
    String sendFromEmail();

    @Meta.AD(required = false, deflt = "dsd@deltares.nl", description = "Configure the Reply To email address")
    String replyToEmail();

    @Meta.AD(required = false, deflt = "false", description = "Configure if current site is a DSD site.")
    boolean dsdSite();
}
