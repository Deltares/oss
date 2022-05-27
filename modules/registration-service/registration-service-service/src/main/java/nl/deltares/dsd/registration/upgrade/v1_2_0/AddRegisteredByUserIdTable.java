package nl.deltares.dsd.registration.upgrade.v1_2_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.dsd.registration.upgrade.AbsUpgradeProcess;

public class AddRegisteredByUserIdTable extends AbsUpgradeProcess {

    @Override
    protected void doUpgrade() throws Exception {

        String tableName = "Registrations_Registration";
        String columnName = "registeredByUserId";
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
        addIndex(tableName, "IX_V120_REGISTEREDBY", new String[]{"groupId", "registeredByUserId"});
        addIndex(tableName, "IX_V120_REGISTEREDBY_EVENT", new String[]{"groupId", "registeredByUserId","eventResourcePrimaryKey"});
    }

    private static final Log _log = LogFactoryUtil.getLog(
            AddRegisteredByUserIdTable.class);
}
