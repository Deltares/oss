/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.oss.download.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nl.deltares.oss.download.model.DownloadCountTable;
import nl.deltares.oss.download.model.impl.DownloadCountImpl;
import nl.deltares.oss.download.model.impl.DownloadCountModelImpl;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from DownloadCount.
 *
 * @author Erik de Rooij @ Deltares
 * @generated
 */
@Component(
	property = {
		"class.name=nl.deltares.oss.download.model.impl.DownloadCountImpl",
		"table.name=Downloads_DownloadCount"
	},
	service = ArgumentsResolver.class
)
public class DownloadCountModelArgumentsResolver implements ArgumentsResolver {

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

		DownloadCountModelImpl downloadCountModelImpl =
			(DownloadCountModelImpl)baseModel;

		long columnBitmask = downloadCountModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(downloadCountModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					downloadCountModelImpl.getColumnBitmask(columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(downloadCountModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return DownloadCountImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return DownloadCountTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		DownloadCountModelImpl downloadCountModelImpl, String[] columnNames,
		boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = downloadCountModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				arguments[i] = downloadCountModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}
// LIFERAY-SERVICE-BUILDER-HASH:68663598