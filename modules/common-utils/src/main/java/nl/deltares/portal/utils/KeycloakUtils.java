package nl.deltares.portal.utils;

import java.io.IOException;
import java.util.Map;

public interface KeycloakUtils {

    boolean isActive();

    String getUserMailingPath();

    String getAccountPath();

    String getAdminAvatarPath();

    byte[] getUserAvatar(String email) throws IOException;

    int updateUserAttributes(String email, Map<String, Object> attributes) throws IOException;

    Map<String, Object> getUserAttributes(String email) throws IOException;
}
