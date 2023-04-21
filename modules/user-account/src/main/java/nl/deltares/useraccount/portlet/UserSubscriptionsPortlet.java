package nl.deltares.useraccount.portlet;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.model.subscriptions.SubscriptionSelection;
import nl.deltares.portal.utils.EmailSubscriptionUtils;
import nl.deltares.useraccount.constants.UserProfilePortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import javax.portlet.*;
import java.io.IOException;
import java.util.ArrayList;
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

    private EmailSubscriptionUtils subscriptionUtils;
    @Reference(
            unbind = "-",
            cardinality = ReferenceCardinality.AT_LEAST_ONE
    )
    protected void setSubscriptionUtilsUtils(EmailSubscriptionUtils subscriptionUtils) {
        if (!subscriptionUtils.isActive()) return;
        if (this.subscriptionUtils == null){
            this.subscriptionUtils = subscriptionUtils;
        } else if (subscriptionUtils.isDefault()){
            this.subscriptionUtils = subscriptionUtils;
        }
    }

    @Override
    public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();
        if (!user.isDefaultUser() && user.isActive()) {
            try {
                final List<SubscriptionSelection> mailings = subscriptionUtils.getSubscriptions(user.getEmailAddress());
                request.setAttribute("subscriptions", mailings);
            } catch (Exception e) {
                SessionErrors.add(request, "update-subscription-failed", "Error reading subscriptions: " + e.getMessage());
                request.setAttribute("subscriptions", Collections.emptyList());
            }

            super.render(request, response);
        }
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

        List<SubscriptionSelection> selectedSubscriptions;
        try {
            selectedSubscriptions = getSelectedSubscriptions(actionRequest);
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "update-subscription-failed", e.getMessage());
            return;
        }
        updateUserSubscriptions(actionRequest, user, selectedSubscriptions);

    }

    private List<SubscriptionSelection> getSelectedSubscriptions(ActionRequest actionRequest) throws Exception {

        final List<SubscriptionSelection> allSubscriptions = subscriptionUtils.getSubscriptions(null); //get list of all subscriptions
        for (SubscriptionSelection mailing : allSubscriptions) {
            final String id = mailing.getId();
            final boolean selected = ParamUtil.getBoolean(actionRequest, "selected_" + id, false);
            mailing.setSelected(selected);
        }
        return allSubscriptions;
    }

    private void updateUserSubscriptions(ActionRequest actionRequest, User user, List<SubscriptionSelection> subscriptions) {

        final String emailAddress = user.getEmailAddress();
        try {
            List<String> subscribeIds = new ArrayList<>();
            List<String> unsubscribeIds = new ArrayList<>();
            for (SubscriptionSelection subscription : subscriptions) {
                if (subscription.isSelected()) {
                    subscribeIds.add(subscription.getId());
                } else {
                    unsubscribeIds.add(subscription.getId());
                }
            }
            subscriptionUtils.subscribeAll(user, subscribeIds);
            subscriptionUtils.unsubscribeAll(emailAddress, unsubscribeIds);
            SessionMessages.add(actionRequest, "update-subscription-success");
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "update-subscription-failed", e.getMessage());
        }

    }

}