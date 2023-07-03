package nl.deltares.portal.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.impl.Download;

import java.util.Map;

public interface DownloadUtils {

    /**
     * Check if the download portal has been configured in portal-ext.properties
     *
     * @return True if configuration is present. Else false.
     */
    boolean isActive();


    /**
     * Create a new download link and send this to user via email
     *
     * @param filePath File or directory to share
     * @param email    Email to send link to
     * @param password Option to include a password
     * @return Share ID
     * @throws Exception If file is already shared to user if file does not exist.
     */
    Map<String, String> createShareLink(String filePath, String email, boolean password) throws Exception;

    /**
     * Register the download request in the downloads table.
     *
     * @param user           User
     * @param groupId        Group from which download takes place
     * @param downloadId     ArticleId of download
     * @param fileName       Path to file
     * @param shareInfo      Information regarding the share. If payment is pending then this info is not yet available
     * @param userAttributes Information about the user
     */
    void registerDownload(User user, long groupId, long downloadId, String fileName, Map<String, String> shareInfo, Map<String, String> userAttributes) throws PortalException;

    /**
     * Register the direct download request in the downloads table.
     *
     * @param user           User
     * @param groupId        Group from which download takes place
     * @param downloadId     ArticleId of download
     * @param fileName       Path to file
     * @param fileShare      Url for direct download
     * @param userAttributes Information about the user
     */
    void registerDownload(User user, long groupId, long downloadId, String fileName, String fileShare, Map<String, String> userAttributes) throws PortalException;


    void incrementDownloadCount(long companyId, long groupId, long downloadId);

    /**
     * Count downloads
     *
     * @param download Download
     * @return Download count
     */
    @SuppressWarnings("unused")
    int getDownloadCount(Download download);

}
