package nl.deltares.portal.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.impl.Download;
import org.w3c.dom.Document;

import java.util.Map;

public interface DownloadUtils {

    /**
     * Check if this site contains a download configuration.
     * @param groupId Site groupId
     * @return True if DownloadSiteConfiguration is present
     */
    boolean isThisADownloadSite(long groupId);

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
    Map<String, String> sendShareLink(String filePath, String email) throws Exception;

    /**
     * Create a new download link and send this to user via email
     *
     * @param filePath File or directory to share
     * @param email    Email to send link to
     * @param password Option to include a password
     * @return Share ID
     * @throws Exception If file is already shared to user if file does not exist.
     */
    Map<String, String> sendShareLink(String filePath, String email, boolean password) throws Exception;

    /**
     * Revoke an earlier created share.
     * @param shareId Share identifier
     * @throws Exception If share does not exist.
     */
    void deleteShareLink(int shareId) throws Exception;

    /**
     * Return document containing the existing share information for 'filePath' element.
     * @param filePath Path to shared file.
     * @return response Document
     */
    Document getFileShares(String filePath) throws Exception;

    /**
     * Check if a share link already exists for a certain user and file combination.
     * @param filePath File of folder to check
     * @param email Email of user to check
     * @return Existing share info ('id', 'expiration').
     */
    Map<String, String> shareLinkExists(String filePath, String email) throws Exception;

    /**
     * Check if a download link has already been issued and is still valid.
     *
     * @param downloadId ArticleId of download
     * @param userId User requesting download
     * @param groupId Site id in which to look
     * @return Existing download url if available
     */
    String directDownloadExists(long downloadId, long userId, long groupId);

    /**
     * Resend an email to recipient of share link.
     * Old share link is first removed before creating a new one.
     * @param shareId Id of existing share.
     */
    Map<String, String> resendShareLink(int shareId) throws Exception;

    /**
     * Resend an email to recipient of share link.
     * Old share link is first removed before creating a new one.
     * @param shareId Id of existing share.
     * @param password option to protect with password
     */
    Map<String, String> resendShareLink(int shareId, boolean password) throws Exception;

    /**
     * Retrieve information about an existing share link
     * @param shareId Share link identifier
     * @return Map containing relevant share link properties
     */
    Map<String, String> getShareLinkInfo(int shareId) throws Exception;

    /**
     * Register the download request in the downloads table.
     * @param user User
     * @param groupId Group from which download takes place
     * @param downloadId ArticleId of download
     * @param filePath Path to file
     * @param shareInfo Information regarding the share. If payment is pending then this info is not yet available
     * @param userAttributes Information about the user
     */
    void registerDownload(User user, long groupId, long downloadId, String filePath, Map<String, String> shareInfo, Map<String, String> userAttributes) throws PortalException;

    /**
     * Register the direct download request in the downloads table.
     * @param user User
     * @param groupId Group from which download takes place
     * @param downloadId ArticleId of download
     * @param filePath Path to file
     * @param directDownloadUrl Url for direct download
     * @param userAttributes Information about the user
     */
    void registerDownload(User user, long groupId, long downloadId, String filePath, String directDownloadUrl, Map<String, String> userAttributes) throws PortalException;


    void incrementDownloadCount(long companyId, long groupId, long downloadId);

    /**
     * Update any pending downloads that have not yet been assigned a shareLink (Pending shares have a shareLink == -1)
     * @param user User the check
     * @param groupId Site id
     */
    void updatePendingShares(User user, long groupId);

    /**
     * Update any processing activities that have not yet been assigned a shareLink and are out of date (Processing shares have a shareLink == -9)
     * @param user User the check
     * @param groupId Site id
     */
    void updateProcessingShares(User user, long groupId);

    /**
     * Count downloads
     * @param download Download
     * @return Download count
     */
    @SuppressWarnings("unused")
    int getDownloadCount(Download download);

    /**
     * Get status of this download
     * @param download Download to check
     * @param user Logged in user
     * @return Status value: available, expired, invalid,  payment_pending, unknown
     */
    @SuppressWarnings("unused")
    String getDownloadStatus(Download download, User user);

    /**
     * Check if payment is pending for download
     * @param download download to check for
     * @param user User to check for
     * @return Returns true if payment is pending else false
     */
    @SuppressWarnings("unused")
    boolean isPaymentPending(Download download, User user);
}
