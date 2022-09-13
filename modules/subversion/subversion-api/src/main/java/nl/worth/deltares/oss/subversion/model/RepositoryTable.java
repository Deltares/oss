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

import java.util.Date;

/**
 * The table class for the &quot;Subversion_Repository&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Repository
 * @generated
 */
public class RepositoryTable extends BaseTable<RepositoryTable> {

	public static final RepositoryTable INSTANCE = new RepositoryTable();

	public final Column<RepositoryTable, Long> repositoryId = createColumn(
		"repositoryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RepositoryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RepositoryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RepositoryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RepositoryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RepositoryTable, String> repositoryName = createColumn(
		"repositoryName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RepositoryTable, Date> createdDate = createColumn(
		"createdDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RepositoryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private RepositoryTable() {
		super("Subversion_Repository", RepositoryTable::new);
	}

}