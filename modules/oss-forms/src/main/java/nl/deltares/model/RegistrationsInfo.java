package nl.deltares.model;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DsdParserUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

public class RegistrationsInfo {

    private final Map<String, Event> eventsMap = new HashMap<>();
    private final Map<String, Registration> registrationsMap = new HashMap<>();
    private final List<Registration> relatedArticles = new ArrayList<>();
    private final Map<String, List<RegistrationInfo>> userRegistrationsMap = new HashMap<>();

    public List<Registration> getRegistrations() {
        return new ArrayList<>(registrationsMap.values());
    }

    public List<String> getRegistrationArticleIds() {
        return new ArrayList<>(registrationsMap.keySet());
    }

    public Registration getRegistration(String articleId){
        return registrationsMap.get(articleId);
    }

    public List<RegistrationInfo> getUserRegistrations(String articleId) {
        return userRegistrationsMap.getOrDefault(articleId, Collections.emptyList());
    }

    public List<Registration> getRelatedArticles() {
        return new ArrayList<>(relatedArticles);
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrationsMap.clear();

        Map<String, List<RegistrationInfo>> userRegistrationsCopy = new HashMap<>(registrations.size());
        for (Registration registration : registrations) {
            registrationsMap.put(registration.getArticleId(), registration);
            List<RegistrationInfo> registrationInfos = userRegistrationsMap.get(registration.getArticleId());
            if (registrationInfos != null) userRegistrationsCopy.put(registration.getArticleId(), registrationInfos);
        }
        userRegistrationsMap.clear();
        userRegistrationsMap.putAll(userRegistrationsCopy);
    }

    public boolean isPaymentRequired() {
        return registrationsMap.values().stream().anyMatch(registration -> registration.getPrice() > 0);
    }

    public static void loadRegistrations(HttpServletRequest httpServletRequest, RegistrationsInfo registrationsInfo, DsdParserUtils dsdParserUtils, ThemeDisplay themeDisplay) {

        String ids = ParamUtil.getString(httpServletRequest, "ids");
        String[] registrationIds = ids.split(",", -1);

        List<Registration> newRegistrations = new ArrayList<>();
        for (String registrationId : registrationIds) {
            if (registrationId == null || registrationId.isEmpty()) continue;
            Registration registration = registrationsInfo.getRegistration(registrationId);

            if (registration == null) {
                try {
                    registration = dsdParserUtils.getRegistration(
                            themeDisplay.getScopeGroupId(), registrationId);
                } catch (PortalException e) {
                    continue;
                }
            }
            if (registration != null){
                newRegistrations.add(registration);
            }
        }
        registrationsInfo.setRegistrations(newRegistrations);
    }

    public static void loadRelatedArticles(RegistrationsInfo registrationsInfo, DsdJournalArticleUtils dsdJournalArticleUtils,
                                           DsdParserUtils dsdParserUtils) throws Exception {

        List<String> selectedRegistrations = registrationsInfo.getRegistrationArticleIds();
        long registrationsGroupId = registrationsInfo.getRegistrationsGroupId();
        List<JournalArticle> relatedArticles = dsdJournalArticleUtils.getRelatedArticles(
                registrationsGroupId, selectedRegistrations.toArray(new String[0]));
        List<Registration> relatedRegistrations = new ArrayList<>();
        for (JournalArticle relatedArticle : relatedArticles) {
            if (relatedArticle == null) {
                continue;
            }
            if (selectedRegistrations.stream().anyMatch(registrationId -> relatedArticle.getArticleId().equals(registrationId))) {
                continue;
            }

            Registration registration = dsdParserUtils.getRegistration(relatedArticle);
            if (registration.canUserRegister(registrationsGroupId)) {
                relatedRegistrations.add(registration);
            }
        }
        registrationsInfo.setRelatedArticles(relatedRegistrations);
    }

    public static void loadUserRegistrations(RegistrationsInfo registrationsInfo, User user) {

        List<Registration> registrations = registrationsInfo.getRegistrations();
        for (Registration registration : registrations) {

            List<RegistrationInfo> userRegistrations = registrationsInfo.getUserRegistrations(registration.getArticleId());
            if (userRegistrations.isEmpty()){
                RegistrationInfo registrationInfo = getRegistrationInfo(user, registration);
                registrationsInfo.setUserRegistrations(registration.getArticleId(), Collections.singletonList(registrationInfo));
            }

        }
    }

    private static RegistrationInfo getRegistrationInfo(User user, Registration registration) {
        RegistrationInfo registrationInfo = new RegistrationInfo();
        registrationInfo.setTitle(registration.getTitle());
        registrationInfo.setArticleId(registration.getArticleId());
        registrationInfo.setPrice((float) registration.getPrice());
        registrationInfo.setFirstName(user.getFirstName());
        registrationInfo.setLastName(user.getLastName());
        registrationInfo.setEmail(user.getEmailAddress());
        registrationInfo.setSalutation(user.getJobTitle());
        return registrationInfo;
    }

    public void setRelatedArticles(List<Registration> relatedArticles) {
        this.relatedArticles.clear();
        this.relatedArticles.addAll(relatedArticles);
    }

    public void setUserRegistrations(String articleId, List<RegistrationInfo> userRegistrations) {
        userRegistrationsMap.put(articleId, userRegistrations);
    }

    public List<RegistrationInfo> getAllUserRegistrations() {
        return userRegistrationsMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public Event getEvent(String eventId) {
        return eventsMap.get(eventId);
    }

    public void putEvent(Event event){
        eventsMap.put(event.getArticleId(), event);
    }

    public long getRegistrationsCompanyId(){
        for (Registration registration : registrationsMap.values()) {
            if (registration.getCompanyId() > 0) {
                return registration.getCompanyId();
            }
        }
        return 0;
    }

    public long getRegistrationsGroupId(){
        for (Registration registration : registrationsMap.values()) {
            if (registration.getGroupId() > 0) {
                return registration.getGroupId();
            }
        }
        return 0;
    }
}
