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

package nl.worth.deltares.oss.subversion.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Subversion_RepositoryLog&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryLog
 * @generated
 */
public class RepositoryLogTable extends BaseTable<RepositoryLogTable> {

	public static final RepositoryLogTable INSTANCE = new RepositoryLogTable();

	public final Column<RepositoryLogTable, Long> logId = createColumn(
		"logId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RepositoryLogTable, String> ipAddress = createColumn(
		"ipAddress", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RepositoryLogTable, String> screenName = createColumn(
		"screenName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RepositoryLogTable, String> action = createColumn(
		"action", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RepositoryLogTable, Long> createDate = createColumn(
		"createDate", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RepositoryLogTable, String> repository = createColumn(
		"repository", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private RepositoryLogTable() {
		super("Subversion_RepositoryLog", RepositoryLogTable::new);
	}

}