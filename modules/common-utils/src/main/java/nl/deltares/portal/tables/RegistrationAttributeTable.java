package nl.deltares.portal.tables;


import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;
import java.util.Date;

public class RegistrationAttributeTable extends BaseTable<RegistrationAttributeTable> {

    public static final String EXTERNAL_IDENTIFIER = "Registration_Attribute_Table";

    public static final RegistrationAttributeTable INSTANCE = new RegistrationAttributeTable();

    public final Column<RegistrationAttributeTable, Long> id = createColumn(
            "c_Id_", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

    public final Column<RegistrationAttributeTable, Long> registrationId = createColumn(
            "registrationId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

    public final Column<RegistrationAttributeTable, String> attributeName = createColumn(
            "attributeName_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

    public final Column<RegistrationAttributeTable, String> attributeValue = createColumn(
            "attributeValue_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

    private RegistrationAttributeTable() {
        super("O_5363499_Registration_Attribute_Table", RegistrationAttributeTable::new);
    }
}
