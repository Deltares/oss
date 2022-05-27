package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;

import java.io.PrintWriter;
import java.util.Locale;

public interface AdminUtils {

    int downloadDisabledUsers(long disabledAfterTime, PrintWriter writer) throws Exception;

    void deleteUserAndRelatedContent(long site, User user, PrintWriter writer, boolean deleteFromKeycloak);

    User getOrCreateRegistrationUser(long companyId, User loggedInUser, String registrationEmail,
                                            String firstName, String lastName, Locale locale) throws Exception;
}
