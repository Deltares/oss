package nl.deltares.dsd.registration.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class AbsUpgradeProcess extends UpgradeProcess {

    protected void addColumn(String tableName, String columnName, String dataType) throws Exception {
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

    protected void addIndex(String tableName, String indexName, String[] columns) throws Exception {
        if (hasIndex(tableName, indexName)){
            return;
        }
        StringBuilder columnsBuilder = new StringBuilder();
        boolean first = true;
        for (String column : columns) {
            if (!first) columnsBuilder.append(',');
            columnsBuilder.append(column);
            first = false;
        }

        String createIndexSql = StringBundler.concat("create index ", indexName, " on ", tableName,
                " (", columnsBuilder.toString(), ")");
        runSQL(createIndexSql);
    }

    protected boolean hasIndex(String tableName, String indexName){
        String sql = StringBundler.concat("SELECT count(*) FROM information_schema.statistics WHERE table_name = '",
                tableName, "' and  index_name = '", indexName, "'");

        try (PreparedStatement ps = connection.prepareStatement(
                sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int count = rs.getInt(1);

                if (count > 0) {
                    return true;
                }
            }
        }
        catch (Exception e) {
            _log.error(e, e);
        }
        return false;
    }

    private static final Log _log = LogFactoryUtil.getLog(
            AbsUpgradeProcess.class);
}
