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
 * The table class for the &quot;Subversion_RepositoryFolder&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryFolder
 * @generated
 */
public class RepositoryFolderTable extends BaseTable<RepositoryFolderTable> {

	public static final RepositoryFolderTable INSTANCE =
		new RepositoryFolderTable();

	public final Column<RepositoryFolderTable, Long> folderId = createColumn(
		"folderId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RepositoryFolderTable, Long> repositoryId =
		createColumn(
			"repositoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RepositoryFolderTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RepositoryFolderTable, Boolean> worldRead =
		createColumn(
			"worldRead", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<RepositoryFolderTable, Boolean> worldWrite =
		createColumn(
			"worldWrite", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<RepositoryFolderTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RepositoryFolderTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private RepositoryFolderTable() {
		super("Subversion_RepositoryFolder", RepositoryFolderTable::new);
	}

}