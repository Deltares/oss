package nl.deltares.dsd.registration.upgrade.v1_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

public class UpgradeRatingsTable extends UpgradeProcess {

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
        runSQL("create index IX_7DD2C1EF on Registrations_Registration " +
                        "(groupId, eventResourcePrimaryKey)");
    }

    private void addColumn(String tableName, String columnName, String dataType) throws Exception {
        if (hasColumn(tableName, columnName)) {
            return;
        }
        String dataTypeUpperCase = StringUtil.toUpperCase(dataType);

        StringBundler sb = new StringBundler(6);

        sb.append("alter table ");
        sb.append(tableName);
        sb.append(" add ");
        sb.append(columnName);
        sb.append(StringPool.SPACE);
        sb.append(dataTypeUpperCase);

        String sql = sb.toString();

        if (dataTypeUpperCase.equals("DATE") || dataType.equals("STRING")) {
            sql = sql.concat(" null");
        }

        runSQL(sql);
    }

    private static final Log _log = LogFactoryUtil.getLog(
            UpgradeProcess.class);
}
