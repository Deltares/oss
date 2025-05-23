/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Service_builder_RegistrationResource&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationResource
 * @generated
 */
public class RegistrationResourceTable
	extends BaseTable<RegistrationResourceTable> {

	public static final RegistrationResourceTable INSTANCE =
		new RegistrationResourceTable();

	public final Column<RegistrationResourceTable, Long>
		registrationResourceId = createColumn(
			"registrationResourceId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<RegistrationResourceTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegistrationResourceTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegistrationResourceTable, Long> eventResourceId =
		createColumn(
			"eventResourceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegistrationResourceTable, Long> parentResourceId =
		createColumn(
			"parentResourceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegistrationResourceTable, String> resourceName =
		createColumn(
			"resourceName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RegistrationResourceTable, String> eventResourceName =
		createColumn(
			"eventResourceName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<RegistrationResourceTable, Long> eventArticleId =
		createColumn(
			"eventArticleId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private RegistrationResourceTable() {
		super(
			"Service_builder_RegistrationResource",
			RegistrationResourceTable::new);
	}

}