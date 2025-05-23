/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Service_builder_RegistrationAttribute&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationAttribute
 * @generated
 */
public class RegistrationAttributeTable
	extends BaseTable<RegistrationAttributeTable> {

	public static final RegistrationAttributeTable INSTANCE =
		new RegistrationAttributeTable();

	public final Column<RegistrationAttributeTable, Long>
		registrationAttributeId = createColumn(
			"registrationAttributeId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<RegistrationAttributeTable, Long> registrationId =
		createColumn(
			"registrationId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegistrationAttributeTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RegistrationAttributeTable, String> value =
		createColumn("value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private RegistrationAttributeTable() {
		super(
			"Service_builder_RegistrationAttribute",
			RegistrationAttributeTable::new);
	}

}