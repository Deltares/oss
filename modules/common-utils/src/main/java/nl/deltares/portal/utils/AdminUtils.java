package nl.deltares.portal.utils;

import java.io.PrintWriter;

public interface AdminUtils {

    void deleteUserAndRelatedContent(long site, long userid, PrintWriter writer);

}
