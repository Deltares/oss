package nl.deltares.portal.utils;

public interface KeycloakUtils {
    String getMailingPath();

    String getAccountPath();

    String getAvatarPath();

    String getAdminAvatarPath();

    byte[] getUserAvatar(String email);
}
