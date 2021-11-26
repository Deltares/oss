package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;

import java.io.PrintWriter;
import java.util.List;

public interface AdminUtils {

    List<User> getDisabledUsers(long companyId, int start, int max, long disabledAfterTime, PrintWriter writer);

    void deleteUserAndRelatedContent(long site, User user, PrintWriter writer, boolean deleteFromKeycloak);

}
