package nl.deltares.useraccount.portlet;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.model.keycloak.KeycloakMailing;
import nl.deltares.portal.model.keycloak.KeycloakUserMailing;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.useraccount.constants.UserProfilePortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS-account",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=UserSubscription",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/usersubscription.jsp",
                "javax.portlet.name=" + UserProfilePortletKeys.USERSUBSCRIPTION,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class UserSubscriptionsPortlet extends MVCPortlet {

    @Reference
    private KeycloakUtils keycloakUtils;

    @Override
    public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();
        if (!user.isDefaultUser() && user.isActive()) {
            try {
                final List<KeycloakUserMailing> userMailings = keycloakUtils.getUserMailings(user.getEmailAddress());
                final List<KeycloakMailing> mailings = keycloakUtils.getMailings();

                setSelection(userMailings, mailings);
                request.setAttribute("subscriptions", mailings);
            } catch (Exception e) {
                SessionErrors.add(request, "update-subscription-failed", "Error reading subscriptions: " + e.getMessage());
                request.setAttribute("subscriptions", Collections.emptyList());
            }

            super.render(request, response);
        }
    }

    private void setSelection(List<KeycloakUserMailing> userMailings, List<KeycloakMailing> mailings) {
        for (KeycloakUserMailing userMailing : userMailings) {
            KeycloakMailing mailing = findMailing(userMailing.getMailingId(), mailings);
            if (mailing == null) {
                continue;
            }
            mailing.setSelected(true);
            mailing.setSelectedDeliveryType(userMailing.getDelivery());
            mailing.setSelectedLanguage(userMailing.getLanguage());
        }
    }

    private KeycloakMailing findMailing(String mailingId, List<KeycloakMailing> mailings) {
        for (KeycloakMailing mailing : mailings) {
            if (mailing.getId().equals(mailingId)) return mailing;
        }
        return null;
    }

    /**
     * Save user attributes to database
     *
     * @param actionRequest  Save action
     * @param actionResponse Save response
     */
    @SuppressWarnings("unused")
    public void saveSubscriptions(ActionRequest actionRequest, ActionResponse actionResponse) {

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();

        List<KeycloakMailing> userMailings = null;
        try {
            userMailings = getUserSubscriptions(actionRequest);
        } catch (IOException e) {
            SessionErrors.add(actionRequest, "update-subscription-failed", e.getMessage());
            return;
        }
        updateUserSubscriptions(actionRequest, user, userMailings);

    }

    private List<KeycloakMailing> getUserSubscriptions(ActionRequest actionRequest) throws IOException {

        final List<KeycloakMailing> mailings = keycloakUtils.getMailings();
        for (KeycloakMailing mailing : mailings) {
            final String id = mailing.getId();
            final boolean selected = ParamUtil.getBoolean(actionRequest, "selected_" + id, false);
            mailing.setSelected(selected);
            if (!selected) {
                continue;
            }
            final String language = ParamUtil.getString(actionRequest, "language_" + id, null);
            mailing.setSelectedLanguage(language);
            final String delivery = ParamUtil.getString(actionRequest, "delivery_" + id, null);
            mailing.setSelectedDelivery(delivery);
        }
        return mailings;
    }

    private void updateUserSubscriptions(ActionRequest actionRequest, User user, List<KeycloakMailing> subscriptions) {

        final String emailAddress = user.getEmailAddress();
        try {
            for (KeycloakMailing subscription : subscriptions) {
                if(subscription.isSelected()){
                    keycloakUtils.subscribe(emailAddress, subscription.getId(), subscription.getSelectedDelivery(), subscription.getSelectedLanguage());
                } else {
                    keycloakUtils.unsubscribe(emailAddress, subscription.getId());
                }
            }

            SessionMessages.add(actionRequest, "update-subscription-success");
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "update-subscription-failed", e.getMessage());
        }

    }

}