package nl.deltares.portal.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import nl.deltares.portal.constants.OssConstants;

@ExtendedObjectClassDefinition(
        category = "oss-general",
        scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
        id = OssConstants.DSD_SITE_CONFIGURATIONS_PID,
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

    @Meta.AD(required = false, deflt = "{}", description = "Configure the URL where the General Course Conditions of Deltares can be found")
    String conditionsURL();

    @Meta.AD(required = false, deflt = "{}", description = "Configure the URL where the Privacy Policy of Deltares can be found")
    String privacyURL();

    @Meta.AD(required = false, deflt = "{}", description = "Configure the URL where the Privacy Policy of Deltares can be found")
    String contactURL();

    @Meta.AD(required = false, deflt = "mydeltares@deltares.nl", description = "Configure the Send From email address")
    String sendFromEmail();

    @Meta.AD(required = false, deflt = "dsd@deltares.nl", description = "Configure the Reply To email address")
    String replyToEmail();

    @Meta.AD(required = false, deflt = "dsd@deltares.nl", description = "Configure the BCC email addresses (';' separated )")
    String bccToEmail();

    @Meta.AD(required = false, deflt = "dsd@deltares.nl", description = "Configure the Reply To email address for when cancellation period has expired")
    String cancellationReplyToEmail();

    @Meta.AD(required = false, deflt = "", description = "Configure Mailing ids for which users can subscribe (';' separated )")
    String mailingIds();

    @Meta.AD(required = false, deflt = "true", description = "Configure if to send email notifications.")
    boolean enableEmails();

    @Meta.AD(required = false, deflt = "false", description = "Configure if registration email contains bus transfer information.")
    boolean enableBusInfo();

    @Meta.AD(required = false, deflt = "session bustransfer dinner", description = "Configure the structures to filter for. (space separated")
    String dsdRegistrationStructures();

    @Meta.AD(required = false, deflt = "registrationDate", description = "Configure the field name that contains the registration date.")
    String dsdRegistrationDateField();

    @Meta.AD(required = false, deflt = "registrationType", description = "Configure the field name that contains the registration type.")
    String dsdRegistrationTypeField();
    @Meta.AD(required = false, deflt = "0", description = "Configure the default companyId under which all Accounts are stored.")
    long getDefaultCompanyIdForAccounts();
}
