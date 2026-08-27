/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public final Column<DownloadTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Long> downloadId = createColumn(
		"downloadId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, String> fileName = createColumn(
		"fileName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Date> expiryDate = createColumn(
		"expiryDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, String> organization = createColumn(
		"organization", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, Long> geoLocationId = createColumn(
		"geoLocationId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, String> fileShareUrl = createColumn(
		"fileShareUrl", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DownloadTable, String> licenseDownloadUrl =
		createColumn(
			"licenseDownloadUrl", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private DownloadTable() {
		super("Downloads_Download", DownloadTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:750704451