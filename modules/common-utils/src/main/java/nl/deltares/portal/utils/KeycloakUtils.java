package nl.deltares.portal.utils;

import java.util.List;
import java.util.Map;

public interface KeycloakUtils {

    enum ATTRIBUTES {
        org_address,
        org_city,
        org_country,
        org_name,
        org_postal,
        org_vat,
        first_name,
        last_name,
        initials,
        email,
        academicTitle,
        jobTitle
    }

    boolean isActive();

    String getAdminUserMailingsPath();

    String getUserMailingPath();

    String getAccountPath();

    String getAvatarPath();

    String getAdminAvatarPath();

    String getAdminUsersPath();

    List<String> getDisabledUsers(int start, int maxResults, Long after, Long before) throws Exception;

    byte[] getUserAvatar(String email) throws Exception;

    int updateUserAttributes(String email, Map<String, String> attributes) throws Exception;

    Map<String, String> getUserAttributes(String email) throws Exception;

    int registerUserLogin(String email, String siteId) throws Exception;

    void deleteUser(String email) throws Exception;

    void subscribe(String emailAddress, String mailingId) throws Exception;

    void unsubscribe(String emailAddress, String mailingId) throws Exception;

    boolean isSubscribed(String emailAddress, List<String> mailingIds) throws Exception;
}
