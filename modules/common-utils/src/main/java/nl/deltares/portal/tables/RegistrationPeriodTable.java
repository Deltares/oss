package nl.deltares.portal.tables;


import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;
import java.util.Date;

public class RegistrationPeriodTable extends BaseTable<RegistrationPeriodTable> {

    public static final String EXTERNAL_IDENTIFIER = "Registration_Period_Table";

    public static final RegistrationPeriodTable INSTANCE = new RegistrationPeriodTable();

    public final Column<RegistrationPeriodTable, Long> id = createColumn(
            "c_Id_", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

    public final Column<RegistrationPeriodTable, Long> journalArticleId = createColumn(
            "journalArticleId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

    public final Column<RegistrationPeriodTable, Date> startTime = createColumn(
            "startTime", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

    public final Column<RegistrationPeriodTable, Date> endTime = createColumn(
            "endTime", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

    private RegistrationPeriodTable() {
        super("O_5363499_Registration_Period_Table", RegistrationPeriodTable::new);
    }
}
