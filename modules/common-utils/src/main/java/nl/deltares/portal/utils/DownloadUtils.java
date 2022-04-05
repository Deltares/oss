package nl.deltares.portal.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.model.impl.Download;
import nl.deltares.portal.model.impl.DownloadGroup;

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
    Map<String, Object> sendShareLink(String filePath, String email) throws Exception;

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
     * @return Existing share info ('id', 'expiration').
     */
    Map<String, Object> shareLinkExists(String filePath, String email) throws Exception;

    /**
     * Check if a download link has already been issued and is still valid.
     *
     * @param downloadId ArticleId of download
     * @param userId User requesting download
     * @return Existing download url if available
     */
    String directDownloadExists(long downloadId, long userId);

    /**
     * Resend an email to recipient of share link.
     * Old share link is first removed before creating a new one.
     * @param shareId Id of existing share.
     */
    Map<String, Object> resendShareLink(int shareId) throws Exception;

    /**
     * Retrieve information about an existing share link
     * @param shareId Share link identifier
     * @return Map containing relevant share link properties
     * @throws Exception
     */
    Map<String, Object> getShareLinkInfo(int shareId) throws Exception;

    /**
     * Register the download request in the downloads table.
     * @param user User
     * @param downloadId ArticleId of download
     * @param shareInfo Information regarding the share. If payment is pending then this info is not yet available
     * @param userAttributes Information about the user
     */
    void registerDownload(User user, long downloadId, Map<String, Object> shareInfo, Map<String, String> userAttributes) throws PortalException;

    /**
     * Register the direct download request in the downloads table.
     * @param user User
     * @param downloadId ArticleId of download
     * @param directDownloadUrl Url for direct download
     * @param userAttributes Information about the user
     */
    void registerDownload(User user, long downloadId, String directDownloadUrl, Map<String, String> userAttributes) throws PortalException;


    void incrementDownloadCount(long downloadId);

    /**
     * Update any pending downloads that have not yet been assigned a shareLink
     * @param user User the check
     */
    void updatePendingShares(User user);

    /**
     * Count downloads
     * @param download Download
     * @return Download count
     */
    int getDownloadCount(Download download);

    /**
     * Check if payment is pending for download
     * @param download
     * @return
     */
    boolean isPaymentPending(Download download, ThemeDisplay themeDisplay);
}
