package nl.deltares.useraccount.portlet;

import aQute.bnd.annotation.metatype.Meta;
import nl.deltares.useraccount.constants.UserProfilePortletKeys;

@Meta.OCD(
        id = UserProfilePortletKeys.USERSUBSCRIPTION_CONFIGURATION
)
public interface UserSubscriptionsConfiguration {

    @Meta.AD(required = false, deflt = "false" , description = "Switch to toggle between showing List items and Consent Groups")
    String showConsentGroups();
}
