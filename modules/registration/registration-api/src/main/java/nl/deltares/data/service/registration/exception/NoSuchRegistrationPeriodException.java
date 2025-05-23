/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package nl.deltares.data.service.registration.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchRegistrationPeriodException extends NoSuchModelException {

	public NoSuchRegistrationPeriodException() {
	}

	public NoSuchRegistrationPeriodException(String msg) {
		super(msg);
	}

	public NoSuchRegistrationPeriodException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchRegistrationPeriodException(Throwable throwable) {
		super(throwable);
	}

}