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