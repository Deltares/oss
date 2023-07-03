package nl.deltares.oss.download.upgrade.v1_3_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.oss.download.upgrade.AbsUpgradeProcess;

public class RemoveShareIdColumn extends AbsUpgradeProcess {

    @Override
    protected void doUpgrade() throws Exception {
        String tableName = "Downloads_Download";
        if (!hasTable(tableName)){
            if (_log.isWarnEnabled()){
                _log.warn(StringBundler.concat(
                        "Not updating table ",
                        tableName, " because table does not exist."
                ));
            }
            return;
        }
        alterTableDropColumn(tableName, "shareId");
        alterColumnName(tableName, "filePath", "fileName longtext");
        alterColumnName(tableName, "directDownloadUrl", "fileShareUrl longtext");

    }


    private static final Log _log = LogFactoryUtil.getLog(
            RemoveShareIdColumn.class);
}
