/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package nl.deltares.oss.geolocation.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchGeoLocationException extends NoSuchModelException {

	public NoSuchGeoLocationException() {
	}

	public NoSuchGeoLocationException(String msg) {
		super(msg);
	}

	public NoSuchGeoLocationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchGeoLocationException(Throwable throwable) {
		super(throwable);
	}

}