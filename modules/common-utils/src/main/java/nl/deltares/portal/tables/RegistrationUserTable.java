package nl.deltares.portal.tables;


import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

public class RegistrationUserTable extends BaseTable<RegistrationUserTable> {
    public static final String EXTERNAL_IDENTIFIER = "Registration_User_Table";

    public static final RegistrationUserTable INSTANCE = new RegistrationUserTable();


    public final Column<RegistrationUserTable, Long> id = createColumn(
            "c_Id_", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

    public final Column<RegistrationUserTable, Long> journalArticleId = createColumn(
            "journalArticleId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

    public final Column<RegistrationUserTable, Long> userId = createColumn(
            "userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

    public final Column<RegistrationUserTable, String> userEmail = createColumn(
            "userEmail", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

    public final Column<RegistrationUserTable, String> authorEmail = createColumn(
            "authorEmail", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

    private RegistrationUserTable() {
        super("O_5363499_Registration_User_Table", RegistrationUserTable::new);
    }
}
