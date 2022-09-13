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
 * The table class for the &quot;Subversion_RepositoryFolderPermission&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryFolderPermission
 * @generated
 */
public class RepositoryFolderPermissionTable
	extends BaseTable<RepositoryFolderPermissionTable> {

	public static final RepositoryFolderPermissionTable INSTANCE =
		new RepositoryFolderPermissionTable();

	public final Column<RepositoryFolderPermissionTable, Long> permissionId =
		createColumn(
			"permissionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RepositoryFolderPermissionTable, Long> folderId =
		createColumn("folderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RepositoryFolderPermissionTable, String> permission =
		createColumn(
			"permission", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RepositoryFolderPermissionTable, Boolean> recurse =
		createColumn(
			"recurse", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<RepositoryFolderPermissionTable, String> entityType =
		createColumn(
			"entityType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RepositoryFolderPermissionTable, Long> entityId =
		createColumn("entityId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RepositoryFolderPermissionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RepositoryFolderPermissionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private RepositoryFolderPermissionTable() {
		super(
			"Subversion_RepositoryFolderPermission",
			RepositoryFolderPermissionTable::new);
	}

}