/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package nl.deltares.dsd.registration.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Registrations_Registration&quot; database table.
 *
 * @author Erik de Rooij @ Deltares
 * @see Registration
 * @generated
 */
public class RegistrationTable extends BaseTable<RegistrationTable> {

	public static final RegistrationTable INSTANCE = new RegistrationTable();

	public final Column<RegistrationTable, Long> registrationId = createColumn(
		"registrationId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RegistrationTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegistrationTable, Long> eventResourcePrimaryKey =
		createColumn(
			"eventResourcePrimaryKey", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<RegistrationTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegistrationTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegistrationTable, Long> resourcePrimaryKey =
		createColumn(
			"resourcePrimaryKey", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<RegistrationTable, String> userPreferences =
		createColumn(
			"userPreferences", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<RegistrationTable, Date> startTime = createColumn(
		"startTime", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RegistrationTable, Date> endTime = createColumn(
		"endTime", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RegistrationTable, Long> parentResourcePrimaryKey =
		createColumn(
			"parentResourcePrimaryKey", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<RegistrationTable, Long> registeredByUserId =
		createColumn(
			"registeredByUserId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private RegistrationTable() {
		super("Registrations_Registration", RegistrationTable::new);
	}

}