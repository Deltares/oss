/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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