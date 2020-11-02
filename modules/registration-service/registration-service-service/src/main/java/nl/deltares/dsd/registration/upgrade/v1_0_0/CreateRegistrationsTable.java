package nl.deltares.dsd.registration.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import nl.deltares.dsd.registration.upgrade.AbsUpgradeProcess;

public class CreateRegistrationsTable extends AbsUpgradeProcess {

    @Override
    protected void doUpgrade() throws Exception {
        String tableName = "Registrations_Registration";

        if (!hasTable(tableName)) {
            String createTableSql = StringBundler.concat(
                    "create table ", tableName, " (" +
                            "registrationId LONG not null primary key," +
                            "groupId LONG," +
                            "companyId LONG," +
                            "userId LONG," +
                            "resourcePrimaryKey LONG," +
                            "parentResourcePrimaryKey LONG," +
                            "startTime DATE null," +
                            "endTime DATE null," +
                            "userPreferences STRING null)");

            runSQL(createTableSql);
        }
        addIndex(tableName, "IX_1113F23F", new String[]{"groupId", "userId", "resourcePrimaryKey"});
        addIndex(tableName, "IX_96E41489", new String[]{"groupId", "userId", "parentResourcePrimaryKey"});
        addIndex(tableName, "IX_CAA20105", new String[]{"groupId", "resourcePrimaryKey"});
        addIndex(tableName, "IX_EF4176CF", new String[]{"groupId", "parentResourcePrimaryKey"});

    }

}
