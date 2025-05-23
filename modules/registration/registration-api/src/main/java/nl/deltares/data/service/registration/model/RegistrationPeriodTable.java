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
 * The table class for the &quot;Service_builder_RegistrationPeriod&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationPeriod
 * @generated
 */
public class RegistrationPeriodTable
	extends BaseTable<RegistrationPeriodTable> {

	public static final RegistrationPeriodTable INSTANCE =
		new RegistrationPeriodTable();

	public final Column<RegistrationPeriodTable, Long> registrationPeriodId =
		createColumn(
			"registrationPeriodId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<RegistrationPeriodTable, Long> registrationResourceId =
		createColumn(
			"registrationResourceId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<RegistrationPeriodTable, Date> startTime = createColumn(
		"startTime", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RegistrationPeriodTable, Date> endTime = createColumn(
		"endTime", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private RegistrationPeriodTable() {
		super(
			"Service_builder_RegistrationPeriod", RegistrationPeriodTable::new);
	}

}