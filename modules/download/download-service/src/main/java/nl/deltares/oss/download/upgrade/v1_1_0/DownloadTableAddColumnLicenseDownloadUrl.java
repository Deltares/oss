package nl.deltares.oss.download.upgrade.v1_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.oss.download.upgrade.AbsUpgradeProcess;

public class DownloadTableAddColumnLicenseDownloadUrl extends AbsUpgradeProcess {

    @Override
    protected void doUpgrade() throws Exception {
        String tableName = "Downloads_Download";
        String columnName = "licenseDownloadUrl";
        if (!hasTable(tableName)){
            if (_log.isWarnEnabled()){
                _log.warn(StringBundler.concat(
                        "Not adding column ",
                        columnName, " because table ",
                        tableName,
                        " does not exist."
                ));
            }
            return;
        }
        addColumn(tableName, columnName, "longtext");

    }


    private static final Log _log = LogFactoryUtil.getLog(
            DownloadTableAddColumnLicenseDownloadUrl.class);
}
