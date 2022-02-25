package nl.deltares.portal.utils;

import java.util.Map;

public interface DownloadUtils {

    /**
     * Check if the download portal has been configured in portal-ext.properties
     * @return True if configuration is present. Else false.
     */
    boolean isActive();

    /**
     * Returns a URL string containing a temporary download link.
     * @param fileId Identifier of file to share.
     * @return Download URL
     * @throws Exception If file does not exist
     */
    String getDirectDownloadLink(long fileId) throws Exception;

    /**
     * Create a new download link and send this to user via email
     *
     * @param filePath File or directory to share
     * @param email    Email to send link to
     * @return Share ID
     * @throws Exception If file is already shared to user if file does not exist.
     */
    int sendShareLink(String filePath, String email) throws Exception;

    /**
     * Revoke an earlier creaded share.
     * @param shareId Share identifier
     * @throws Exception If share does not exist.
     */
    void deleteShareLink(int shareId) throws Exception;

    /**
     * Check if a share link already exists for a certain user and file combination.
     * @param filePath File of folder to check
     * @param email Email of user to check
     * @return Existing share ID or -1 if not share does not exist.
     */
    int shareLinkExists(String filePath, String email) throws Exception;

    /**
     * Resend an email to recipient of share link.
     * Old share link is first removed before creating a new one.
     * @param shareId Id of existing share.
     */
    int resendShareLink(int shareId) throws Exception;

    /**
     * Retrieve information about an existing share link
     * @param shareId Share link identifier
     * @return Map containing relevant share link properties
     * @throws Exception
     */
    Map<String, String> getShareLinkInfo(int shareId) throws Exception;
}
