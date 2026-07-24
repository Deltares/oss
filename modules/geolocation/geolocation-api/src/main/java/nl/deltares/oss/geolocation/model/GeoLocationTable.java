/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.oss.geolocation.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;GeoLocations_GeoLocation&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see GeoLocation
 * @generated
 */
public class GeoLocationTable extends BaseTable<GeoLocationTable> {

	public static final GeoLocationTable INSTANCE = new GeoLocationTable();

	public final Column<GeoLocationTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<GeoLocationTable, Long> locationId = createColumn(
		"locationId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<GeoLocationTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<GeoLocationTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<GeoLocationTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<GeoLocationTable, Long> countryId = createColumn(
		"countryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<GeoLocationTable, String> cityName = createColumn(
		"cityName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<GeoLocationTable, Double> latitude = createColumn(
		"latitude", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<GeoLocationTable, Double> longitude = createColumn(
		"longitude", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private GeoLocationTable() {
		super("GeoLocations_GeoLocation", GeoLocationTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:136425605