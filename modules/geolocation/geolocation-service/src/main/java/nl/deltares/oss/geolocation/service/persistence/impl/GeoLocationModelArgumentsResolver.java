/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.oss.geolocation.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nl.deltares.oss.geolocation.model.GeoLocationTable;
import nl.deltares.oss.geolocation.model.impl.GeoLocationImpl;
import nl.deltares.oss.geolocation.model.impl.GeoLocationModelImpl;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from GeoLocation.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	property = {
		"class.name=nl.deltares.oss.geolocation.model.impl.GeoLocationImpl",
		"table.name=GeoLocations_GeoLocation"
	},
	service = ArgumentsResolver.class
)
public class GeoLocationModelArgumentsResolver implements ArgumentsResolver {

	@Override
	public Object[] getArguments(
		FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
		boolean original) {

		String[] columnNames = finderPath.getColumnNames();

		if ((columnNames == null) || (columnNames.length == 0)) {
			if (baseModel.isNew()) {
				return new Object[0];
			}

			return null;
		}

		GeoLocationModelImpl geoLocationModelImpl =
			(GeoLocationModelImpl)baseModel;

		long columnBitmask = geoLocationModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(geoLocationModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					geoLocationModelImpl.getColumnBitmask(columnName);
			}

			if (finderPath.isBaseModelResult() &&
				(GeoLocationPersistenceImpl.
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

				finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(geoLocationModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return GeoLocationImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return GeoLocationTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		GeoLocationModelImpl geoLocationModelImpl, String[] columnNames,
		boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = geoLocationModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				arguments[i] = geoLocationModelImpl.getColumnValue(columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

	private static final long _ORDER_BY_COLUMNS_BITMASK;

	static {
		long orderByColumnsBitmask = 0;

		orderByColumnsBitmask |= GeoLocationModelImpl.getColumnBitmask(
			"countryId");

		_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:227412552