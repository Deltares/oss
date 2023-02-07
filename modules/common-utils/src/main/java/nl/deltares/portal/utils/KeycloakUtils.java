package nl.deltares.portal.utils;

import nl.deltares.portal.model.keycloak.KeycloakMailing;
import nl.deltares.portal.model.keycloak.KeycloakUserMailing;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public interface KeycloakUtils {

    static String extractUsernameFromEmail(String email, int iteration) {
        final String[] split = email.split("@");
        final String username = split[0];
        if (iteration == 0) {
            return username;
        }
        return username + "_" + (iteration - 1);
    }

    enum ATTRIBUTES {
        org_address,
        org_city,
        org_country,
        org_name,
        org_postal,
        org_vat,
        org_website,
        org_phone,
        first_name,
        last_name,
        initials,
        email,
        academicTitle,
        jobTitle,
        phone
    }

    boolean isActive();

    String getAdminUserMailingsPath();

    String getUserMailingPath();

    String getAccountPath();

    String getAvatarPath();

    String getAdminAvatarPath();

    String getAdminMailingsPath();

    int downloadDisabledUsers(int maxResults, int paginationStart, PrintWriter writer) throws Exception;

    int downloadUnverifiedUsers(int maxResults, int paginationStart, PrintWriter writer) throws Exception;

    int countUnverifiedUsers(PrintWriter writer) throws IOException;

    byte[] getUserAvatar(String email) throws Exception;

    void deleteUserAvatar(String email) throws Exception;

    void updateUserAvatar(String emailAddress, byte[] image, String fileName) throws Exception;

    int updateUserProfile(String email, Map<String, String> attributes) throws Exception;

    int updateUserAttributes(String email, Map<String, String> attributes) throws Exception;

    Map<String, String> getUserAttributes(String email) throws Exception;

    Map<String, String> getUserInfo(String email) throws Exception;

    boolean isExistingUsername(String username) throws Exception;

    List<KeycloakMailing> getMailings() throws IOException;

    List<KeycloakUserMailing> getUserMailings(String email) throws IOException;

    int registerUserLogin(String email, String siteId) throws Exception;

    int deleteUserWithEmail(String email) throws Exception;

    int deleteUserWithId(String id) throws Exception;

    void subscribe(String emailAddress, String mailingId) throws Exception;

    void subscribe(String emailAddress, String mailingId, String delivery, String language) throws Exception;

    void unsubscribe(String emailAddress, String mailingId) throws Exception;

    boolean isSubscribed(String emailAddress, List<String> mailingIds) throws Exception;

    int resetPassword(String username, String currentPassword, String newPassword) throws Exception;
}
