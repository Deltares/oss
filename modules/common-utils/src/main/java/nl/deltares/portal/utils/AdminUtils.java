package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;

import java.io.PrintWriter;
import java.util.Locale;

public interface AdminUtils {

    KeycloakUtils getKeycloakUtils();

    void deleteUserRelatedContent(long site, User user, PrintWriter writer);

    void deleteLiferayUser(User user, PrintWriter writer);

    User getOrCreateRegistrationUser(long companyId, User loggedInUser, String registrationEmail,
                                            String firstName, String lastName, Locale locale) throws Exception;
}
