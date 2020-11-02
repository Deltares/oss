package nl.deltares.dsd.registration.upgrade.v1_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.dsd.registration.upgrade.AbsUpgradeProcess;

public class UpgradeRegistrationsTable extends AbsUpgradeProcess {

    @Override
    protected void doUpgrade() throws Exception {

        String tableName = "Registrations_Registration";
        String columnName = "eventResourcePrimaryKey";
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
        addColumn(tableName, columnName, "LONG");
        addIndex(tableName, "IX_7DD2C1EF", new String[]{"groupId", "eventResourcePrimaryKey"});
        addIndex(tableName, "IX_38E884F5", new String[]{"groupId", "userId","eventResourcePrimaryKey"});
    }

    private static final Log _log = LogFactoryUtil.getLog(
            UpgradeRegistrationsTable.class);
}
