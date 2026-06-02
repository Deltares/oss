package nl.deltares.model;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DsdParserUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class RegistrationsInfo implements Serializable {

    private final long scopedGroupId;
    private final User user;
    private final ArrayList<String> eventIds = new ArrayList<>();
    private final ArrayList<String> registrationArticleIds = new ArrayList<>();
    private final List<String> relatedArticleIds = new ArrayList<>();
    private final Map<String, List<RegistrationInfo>> userRegistrationsMap = new HashMap<>();
    private final DsdParserUtils dsdParserUtils;

    private static final Log LOG = LogFactoryUtil.getLog(RegistrationsInfo.class);


    public RegistrationsInfo(DsdParserUtils dsdParserUtils, ThemeDisplay themeDisplay) {
        this.dsdParserUtils = dsdParserUtils;
        this.scopedGroupId = themeDisplay.getSiteGroupId();
        this.user = themeDisplay.getUser();

    }

    public List<Registration> getRegistrations() {
        return getRegistrations(registrationArticleIds);
    }

    private List<Registration> getRegistrations(List<String> registrationArticleIds) {
        return registrationArticleIds.stream().map(articleId -> {
            try {
                return dsdParserUtils.getRegistration(scopedGroupId, articleId);
            } catch (PortalException e) {
                LOG.warn("Error retrieving registration: " + e.getMessage());
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<String> getRegistrationArticleIds() {
        return Collections.unmodifiableList(registrationArticleIds);
    }

    public Registration getRegistration(String articleId) {
        try {
            return dsdParserUtils.getRegistration(scopedGroupId, articleId);
        } catch (PortalException e) {
            LOG.warn("Error retrieving registration: " + e.getMessage());
            return null;
        }
    }

    public List<RegistrationInfo> getUserRegistrations(String articleId) {
        return Collections.unmodifiableList(userRegistrationsMap.getOrDefault(articleId, Collections.emptyList()));
    }

    public List<String> getRelatedArticleIds() {
        return Collections.unmodifiableList(relatedArticleIds);
    }

    public void setRegistrations(List<String> registrationsIds) {
        this.registrationArticleIds.clear();

        Map<String, List<RegistrationInfo>> userRegistrationsCopy = new HashMap<>(registrationsIds.size());
        for (String registrationId : registrationsIds) {
            this.registrationArticleIds.add(registrationId);
            List<RegistrationInfo> registrationInfos = userRegistrationsMap.get(registrationId);
            if (registrationInfos != null) {
                userRegistrationsCopy.put(registrationId, registrationInfos);
            }
        }
        userRegistrationsMap.clear();
        userRegistrationsMap.putAll(userRegistrationsCopy);
    }

    public boolean isPaymentRequired() {

        return registrationArticleIds.stream().anyMatch(articleId -> {
            try {
                Registration registration = dsdParserUtils.getRegistration(scopedGroupId, articleId);
                if (registration != null) {
                    return registration.getPrice() > 0;
                }
            } catch (PortalException e) {
                LOG.warn("Error retrieving registration: " + e.getMessage());
            }
            return false;
        });

    }

    public static void loadRegistrations(HttpServletRequest httpServletRequest, RegistrationsInfo registrationsInfo) {

        String ids = ParamUtil.getString(httpServletRequest, "ids");
        String[] registrationIds = ids.split(",", -1);
        registrationsInfo.setRegistrations(Arrays.stream(registrationIds).collect(Collectors.toList()));
    }

    public static void loadRegistrationEvents(RegistrationsInfo registrationsInfo) {
        List<Registration> selectedRegistrations = registrationsInfo.getRegistrations();
        for (Registration selectedRegistration : selectedRegistrations) {
            long eventId = selectedRegistration.getEventId();
            if (eventId > 0) {
                registrationsInfo.putEventArticleId(eventId);
            }
        }
    }

    public static void loadRelatedArticles(RegistrationsInfo registrationsInfo, DsdJournalArticleUtils dsdJournalArticleUtils,
                                           DsdParserUtils dsdParserUtils) throws Exception {

        List<String> selectedRegistrationIds = registrationsInfo.getRegistrationArticleIds();
        List<JournalArticle> relatedArticles = dsdJournalArticleUtils.getRelatedArticles(
                registrationsInfo.scopedGroupId, selectedRegistrationIds.toArray(new String[0]));
        List<String> relatedRegistrationIds = new ArrayList<>();
        for (JournalArticle relatedArticle : relatedArticles) {
            if (relatedArticle == null) {
                continue;
            }
            if (selectedRegistrationIds.stream().anyMatch(registrationId -> relatedArticle.getArticleId().equals(registrationId))) {
                continue;
            }

            Registration registration = dsdParserUtils.getRegistration(relatedArticle);
            if (registration.canUserRegister(registrationsInfo.user.getUserId())) {
                relatedRegistrationIds.add(registration.getArticleId());
            }
        }
        registrationsInfo.setRelatedArticles(relatedRegistrationIds);
    }

    public static void loadChildArticles(RegistrationsInfo registrationsInfo) {
        List<String> selectedRegistrationIds = registrationsInfo.getRegistrationArticleIds();
        List<String> relatedArticles = registrationsInfo.getRelatedArticleIds();
        ArrayList<String> updatedRelatedArticles = new ArrayList<>(relatedArticles.size());
        updatedRelatedArticles.addAll(relatedArticles);
        Collection<Event> loadedEvents = registrationsInfo.eventIds.stream().map(eventId ->
                registrationsInfo.getEvent(Long.valueOf(eventId))).filter(Objects::nonNull).collect(Collectors.toList());
        for (Event loadedEvent : loadedEvents) {
            List<Registration> eventRegistrations = loadedEvent.getRegistrations(loadedEvent.getLocale());
            for (Registration registration : eventRegistrations) {
                if (registration.hasParent()) {

                    Registration parent = registration.getParentRegistration();
                    boolean parentIsSelected = parent != null && selectedRegistrationIds.contains(parent.getArticleId());
                    boolean childIsSelected = selectedRegistrationIds.contains(registration.getArticleId());

                    if (parent != null && childIsSelected && !parentIsSelected) {
                        if (!parent.canUserRegister(registrationsInfo.user.getUserId())) continue;

                        //This is a parent registration that has not been selected yet
                        updatedRelatedArticles.add(parent.getArticleId());
                    } else if (parentIsSelected && !childIsSelected) {
                        if (!registration.canUserRegister(registrationsInfo.user.getUserId())) continue;
                        //This is a child registration that has not been selected yet
                        updatedRelatedArticles.add(registration.getArticleId());
                    }
                }
            }
        }
        registrationsInfo.setRelatedArticles(updatedRelatedArticles);
    }

    public static void loadUserRegistrations(RegistrationsInfo registrationsInfo) {

        List<Registration> registrations = registrationsInfo.getRegistrations();
        for (Registration registration : registrations) {

            List<RegistrationInfo> userRegistrations = registrationsInfo.getUserRegistrations(registration.getArticleId());
            if (userRegistrations.isEmpty()) {
                RegistrationInfo registrationInfo = getRegistrationInfo(registrationsInfo.user, registration);
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

    public void setRelatedArticles(List<String> relatedArticleIds) {
        this.relatedArticleIds.clear();
        this.relatedArticleIds.addAll(relatedArticleIds);
    }

    public void setUserRegistrations(String articleId, List<RegistrationInfo> userRegistrations) {
        userRegistrationsMap.put(articleId, userRegistrations);
    }

    public List<RegistrationInfo> getAllUserRegistrations() {
        return userRegistrationsMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public Event getEvent(Long eventId) {
        try {
            return dsdParserUtils.getEvent(scopedGroupId, String.valueOf(eventId));
        } catch (PortalException e) {
            LOG.warn("Error retrieving event: " + e.getMessage());
        }
        return null;
    }

    public void putEventArticleId(Long articleId) {
        String eventId = String.valueOf(articleId);
        if (!eventIds.contains(eventId)) {
            eventIds.add(eventId);
        }
    }

    public long getRegistrationsCompanyId() {
        Optional<Long> first = registrationArticleIds.stream().map(articleId -> {
            try {
                return dsdParserUtils.getRegistration(scopedGroupId, String.valueOf(articleId)).getCompanyId();
            } catch (PortalException e) {
                return 0L;
            }
        }).filter(companyId -> companyId > 0).findFirst();

        return first.isEmpty() ? 0 : first.get();
    }

    public long getRegistrationsGroupId() {
        return scopedGroupId;
    }
}
