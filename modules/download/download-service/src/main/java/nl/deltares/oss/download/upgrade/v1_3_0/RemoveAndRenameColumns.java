package nl.deltares.oss.download.upgrade.v1_3_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.oss.download.upgrade.AbsUpgradeProcess;

public class RemoveAndRenameColumns extends AbsUpgradeProcess {

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
        if (hasColumn(tableName, "shareId")) {
            alterTableDropColumn(tableName, "shareId");
        }
        if (hasColumn(tableName, "filePath")) {
            alterTableDropColumn(tableName, "filePath");
        }
        if (hasColumn(tableName, "directDownloadUrl")) {
            alterTableDropColumn(tableName, "directDownloadUrl");
        }

        addColumn(tableName, "fileName", "text");
        addColumn(tableName, "fileShareLink", "text");
    }


    private static final Log _log = LogFactoryUtil.getLog(
            RemoveAndRenameColumns.class);
}
