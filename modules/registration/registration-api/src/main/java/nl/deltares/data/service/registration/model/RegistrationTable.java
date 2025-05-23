/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Service_builder_Registration&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Registration
 * @generated
 */
public class RegistrationTable extends BaseTable<RegistrationTable> {

	public static final RegistrationTable INSTANCE = new RegistrationTable();

	public final Column<RegistrationTable, Long> registrationId = createColumn(
		"registrationId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RegistrationTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegistrationTable, Long> registrationResourceId =
		createColumn(
			"registrationResourceId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<RegistrationTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegistrationTable, Long> authorId = createColumn(
		"authorId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegistrationTable, Date> registrationTime =
		createColumn(
			"registrationTime", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private RegistrationTable() {
		super("Service_builder_Registration", RegistrationTable::new);
	}

}