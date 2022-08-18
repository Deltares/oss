package nl.deltares.useraccount.portlet;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.useraccount.constants.UserProfilePortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS-account",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=Password",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/password.jsp",
                "javax.portlet.name=" + UserProfilePortletKeys.PASSWORD,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class PasswordPortlet extends MVCPortlet {

    @Reference
    private KeycloakUtils keycloakUtils;

    /**
     * Save user attributes to database
     *
     * @param actionRequest  Save action
     * @param actionResponse Save response
     */
    @SuppressWarnings("unused")
    public void savePassword(ActionRequest actionRequest, ActionResponse actionResponse) {

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();
        final String currentPassword = ParamUtil.getString(actionRequest, "currentPassword");
        final String newPassword = ParamUtil.getString(actionRequest, "newPassword");
        final String repeatPassword = ParamUtil.getString(actionRequest, "repeatPassword");

        if (Validator.isBlank(currentPassword) || Validator.isBlank(newPassword) || Validator.isBlank(repeatPassword)) {
            SessionErrors.add(actionRequest, "update-password-failed", "Missing fields");
            return;
        }
        if (!newPassword.equals(repeatPassword)) {
            SessionErrors.add(actionRequest, "update-password-failed", "New and repeat password do not match");
            return;
        }

        try {
            keycloakUtils.resetPassword(user.getScreenName(), currentPassword, newPassword);
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "update-password-failed", e.getMessage());
            return;
        }

        SessionMessages.add(actionRequest, "update-password-success");
    }


}