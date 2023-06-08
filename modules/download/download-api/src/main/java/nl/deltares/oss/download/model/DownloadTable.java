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

import java.util.Date;

/**
 * The table class for the &quot;Downloads_Download&quot; database table.
 *
 * @author Erik de Rooij @ Deltares
 * @see Download
 * @generated
 */
public class DownloadTable extends BaseTable<DownloadTable> {

	public static final DownloadTable INSTANCE = new DownloadTable();

	public final Column<DownloadTable, Long> id = createColumn(
		"id_", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DownloadTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Long> downloadId = createColumn(
		"downloadId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, String> filePath = createColumn(
		"filePath", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Date> expiryDate = createColumn(
		"expiryDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, String> organization = createColumn(
		"organization", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Long> geoLocationId = createColumn(
		"geoLocationId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Integer> shareId = createColumn(
		"shareId", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, String> directDownloadUrl = createColumn(
		"directDownloadUrl", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, String> licenseDownloadUrl =
		createColumn(
			"licenseDownloadUrl", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private DownloadTable() {
		super("Downloads_Download", DownloadTable::new);
	}

}