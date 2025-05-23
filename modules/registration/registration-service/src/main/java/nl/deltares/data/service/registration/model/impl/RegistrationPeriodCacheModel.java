/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import nl.deltares.data.service.registration.model.RegistrationPeriod;

/**
 * The cache model class for representing RegistrationPeriod in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RegistrationPeriodCacheModel
	implements CacheModel<RegistrationPeriod>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RegistrationPeriodCacheModel)) {
			return false;
		}

		RegistrationPeriodCacheModel registrationPeriodCacheModel =
			(RegistrationPeriodCacheModel)object;

		if (registrationPeriodId ==
				registrationPeriodCacheModel.registrationPeriodId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, registrationPeriodId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{registrationPeriodId=");
		sb.append(registrationPeriodId);
		sb.append(", registrationResourceId=");
		sb.append(registrationResourceId);
		sb.append(", startTime=");
		sb.append(startTime);
		sb.append(", endTime=");
		sb.append(endTime);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RegistrationPeriod toEntityModel() {
		RegistrationPeriodImpl registrationPeriodImpl =
			new RegistrationPeriodImpl();

		registrationPeriodImpl.setRegistrationPeriodId(registrationPeriodId);
		registrationPeriodImpl.setRegistrationResourceId(
			registrationResourceId);

		if (startTime == Long.MIN_VALUE) {
			registrationPeriodImpl.setStartTime(null);
		}
		else {
			registrationPeriodImpl.setStartTime(new Date(startTime));
		}

		if (endTime == Long.MIN_VALUE) {
			registrationPeriodImpl.setEndTime(null);
		}
		else {
			registrationPeriodImpl.setEndTime(new Date(endTime));
		}

		registrationPeriodImpl.resetOriginalValues();

		return registrationPeriodImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		registrationPeriodId = objectInput.readLong();

		registrationResourceId = objectInput.readLong();
		startTime = objectInput.readLong();
		endTime = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(registrationPeriodId);

		objectOutput.writeLong(registrationResourceId);
		objectOutput.writeLong(startTime);
		objectOutput.writeLong(endTime);
	}

	public long registrationPeriodId;
	public long registrationResourceId;
	public long startTime;
	public long endTime;

}