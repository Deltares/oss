package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;

import java.io.PrintWriter;

public interface AdminUtils {

    int downloadDisabledUsers(long disabledAfterTime, PrintWriter writer) throws Exception;

    void deleteUserAndRelatedContent(long site, User user, PrintWriter writer, boolean deleteFromKeycloak);

}
