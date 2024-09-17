package nl.deltares.portal.utils;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

public interface KeycloakUtils {

    static String extractUsernameFromEmail(String email) {
        final String[] split = email.split("@");
        return split[0];
    }

    enum ATTRIBUTES {
        org_external_reference,
        org_registration_id,
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
        salutation,
        initials,
        email,
        academicTitle,
        jobTitle,
        phone
    }

    boolean isActive();

    String getAvatarPath();

    int callCheckUsersExist(File checkUsersInputFile, PrintWriter nonExistingUsersOutput) throws Exception;

    int downloadInvalidUsers(PrintWriter writer) throws Exception;

    byte[] getUserAvatar(String email) throws Exception;

    void deleteUserAvatar(String email) throws Exception;

    void updateUserAvatar(String emailAddress, File avatarFile) throws Exception;

    int updateUserProfile(String email, Map<String, String> attributes) throws Exception;

    int updateUserAttributes(String email, Map<String, String> attributes) throws Exception;

    Map<String, String> getUserAttributes(String email) throws Exception;

    Map<String, String> getUserInfo(String email) throws Exception;

    boolean isExistingUsername(String username) throws Exception;

    int registerUserLogin(String email, String siteId) throws Exception;

    int deleteUserWithEmail(String email) throws Exception;

    int deleteUserWithId(String id) throws Exception;

    int resetPassword(String username, String currentPassword, String newPassword) throws Exception;
}
