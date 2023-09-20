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

package nl.deltares.oss.download.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Downloads_DownloadCount&quot; database table.
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadCount
 * @generated
 */
public class DownloadCountTable extends BaseTable<DownloadCountTable> {

	public static final DownloadCountTable INSTANCE = new DownloadCountTable();

	public final Column<DownloadCountTable, Long> id = createColumn(
		"id_", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DownloadCountTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DownloadCountTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DownloadCountTable, Long> downloadId = createColumn(
		"downloadId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DownloadCountTable, Integer> count = createColumn(
		"count", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private DownloadCountTable() {
		super("Downloads_DownloadCount", DownloadCountTable::new);
	}

}