package nl.deltares.tableview.portlet.portlet;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.tableview.model.DisplayRegistration;
import nl.deltares.tableview.portlet.constants.TablePortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS-table",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=RegistrationTable",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/registrationTable.jsp",
                "javax.portlet.name=" + TablePortletKeys.REGISTRATIONTABLE,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class RegistrationTablePortlet extends MVCPortlet {

    @Reference
    private DsdJournalArticleUtils dsdJournalArticleUtils;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }


    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        final String path = ParamUtil.getString(renderRequest, "mvcPath", null);
        if (path != null && path.endsWith("editRegistration.jsp")) {
            doLoadRegistration(renderRequest);
        } else {
            doFilterValues(renderRequest);
        }
        super.render(renderRequest, renderResponse);
    }

    private void doLoadRegistration(RenderRequest renderRequest) {

        final String registrationId = ParamUtil.getString(renderRequest, "registrationId", null);
        if (registrationId == null) {
            SessionErrors.add(renderRequest, "action-failed", "No registration Id provided.");
            return;
        }

        Registration registration;
        final long id = Long.parseLong(registrationId);
        try {
            registration = RegistrationLocalServiceUtil.getRegistration(id);
        } catch (PortalException e) {
            SessionErrors.add(renderRequest, "action-failed", String.format("Error getting registration %s: %s", registrationId, e.getMessage()));
            return;
        }

        String eventName;
        try {
            final JournalArticle eventArticle = dsdJournalArticleUtils.getLatestArticle(registration.getEventResourcePrimaryKey());
            eventName = eventArticle.getTitle();
        } catch (PortalException e) {
            eventName = String.valueOf(registration.getEventResourcePrimaryKey());
        }

        String sessionName;
        try {
            final JournalArticle registrationArticle = dsdJournalArticleUtils.getLatestArticle(registration.getResourcePrimaryKey());
            sessionName = registrationArticle.getTitle();
        } catch (PortalException e) {
            sessionName = String.valueOf(registration.getResourcePrimaryKey());
        }
        final String email = ParamUtil.getString(renderRequest, "filterEmail", null);

        renderRequest.setAttribute("record",
                new DisplayRegistration(id,email, eventName, sessionName, formatJson(registration.getUserPreferences()),
                        registration.getStartTime(), registration.getEndTime()));
    }

    private String formatJson(String json) {

        try {
            final JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);
            return jsonObject.toString(4);
        } catch (JSONException e) {
            return json;
        }

    }

    private void doFilterValues(RenderRequest renderRequest) {

        String email = ParamUtil.getString(renderRequest, "filterEmail", null);
        if (email == null) {
            renderRequest.setAttribute("records", Collections.emptyList());
            return;
        }
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        final long companyId = themeDisplay.getCompanyId();
        final long siteGroupId = themeDisplay.getSiteGroupId();

        String eventId = null;
        try {
            DSDSiteConfiguration configuration = _configurationProvider.
                    getGroupConfiguration(DSDSiteConfiguration.class, siteGroupId);
            if (configuration.eventId() > 0) {
                eventId = String.valueOf(configuration.eventId());
            }

        } catch (ConfigurationException e) {
            SessionErrors.add(renderRequest, "filter-failed", "Could not find default EventId for this site.");
            renderRequest.setAttribute("records", Collections.emptyList());
            return;
        }

        JournalArticle eventArticle;
        try {
            eventArticle = dsdJournalArticleUtils.getJournalArticle(siteGroupId, eventId);
        } catch (PortalException e) {
            SessionErrors.add(renderRequest, "filter-failed", String.format("Error parsing EventId %s: %s", eventId, e.getMessage()));
            renderRequest.setAttribute("records", Collections.emptyList());
            return;
        }

        List<DisplayRegistration> records = null;
        try {
            User user = UserLocalServiceUtil.getUserByEmailAddress(companyId, email);
            List<Registration> registrations = RegistrationLocalServiceUtil.getUserEventRegistrations(siteGroupId, user.getUserId(), eventArticle.getResourcePrimKey());
            records = convertToDisplayRegistrations(registrations, user, eventArticle);
        } catch (Exception e) {
            SessionErrors.add(renderRequest, "filter-failed", e.getMessage());
        }

        renderRequest.setAttribute("records", Objects.requireNonNullElse(records, Collections.emptyList()));
    }

    private List<DisplayRegistration> convertToDisplayRegistrations(List<Registration> registrations, User user, JournalArticle event) {

        final ArrayList<DisplayRegistration> displays = new ArrayList<>(registrations.size());
        final String emailAddress = user.getEmailAddress();
        final String eventTitle = event.getTitle();

        registrations.forEach(registration -> {
            String registrationTitle;
            try {
                final JournalArticle registrationArticle = dsdJournalArticleUtils.getLatestArticle(registration.getResourcePrimaryKey());
                registrationTitle = registrationArticle.getTitle();
            } catch (PortalException e) {
                registrationTitle = String.valueOf(registration.getResourcePrimaryKey());
            }
            displays.add(new DisplayRegistration(registration.getRegistrationId(),
                    emailAddress, eventTitle, registrationTitle, null, registration.getStartTime(), registration.getEndTime()));
        });

        displays.sort(DisplayRegistration::compareTo);
        return displays;
    }

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void filter(ActionRequest actionRequest, ActionResponse actionResponse) {

        final String selectedEmail = ParamUtil.getString(actionRequest, "filterEmail", null);
        actionResponse.getRenderParameters().setValue("filterEmail", selectedEmail);
    }

    /**
     * Delete selected registration
     *
     * @param actionRequest  Delete action
     * @param actionResponse Delete response
     */
    @SuppressWarnings("unused")
    public void delete(ActionRequest actionRequest, ActionResponse actionResponse) {

        final String registrationId = ParamUtil.getString(actionRequest, "registrationId", null);
        try {
            RegistrationLocalServiceUtil.deleteRegistration(Long.parseLong(registrationId));
            SessionMessages.add(actionRequest, "action-success", String.format("Delete registration %s", registrationId));
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "action-failed", String.format("Failed to delete registration: %s", e.getMessage()));
        }

        final String selectedEmail = ParamUtil.getString(actionRequest, "filterEmail", null);
        actionResponse.getRenderParameters().setValue("filterEmail", selectedEmail);
    }

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void save(ActionRequest actionRequest, ActionResponse actionResponse) {

        final String userPreferences = ParamUtil.getString(actionRequest, "preferences", null);
        final String registrationId = ParamUtil.getString(actionRequest, "registrationId", null);

        try {
            validate(userPreferences);
        } catch (JSONException e) {
            SessionErrors.add(actionRequest, "action-failed", "Invalid JSON content: " + e.getMessage());
            PortalUtil.copyRequestParameters(actionRequest, actionResponse);
            actionResponse.getRenderParameters().setValue("mvcPath", "/editRegistration.jsp");
            return;
        }
        try {
            final Registration registration = RegistrationLocalServiceUtil.getRegistration(Long.parseLong(registrationId));
            registration.setUserPreferences(userPreferences);
            RegistrationLocalServiceUtil.updateRegistration(registration);
            SessionMessages.add(actionRequest, "action-success", String.format("Updated registration %s", registrationId));
        } catch (PortalException e) {
            SessionErrors.add(actionRequest, "action-failed", String.format("Failed to update registration %s: %s", registrationId, e.getMessage()));
        }


        final String filterEmail = ParamUtil.getString(actionRequest, "filterEmail", null);
        actionResponse.getRenderParameters().setValue("filterEmail", filterEmail);

    }

    private void validate(String preferences) throws JSONException {
        if (preferences == null) return;
        JsonContentUtils.parseContent(preferences);

    }

}