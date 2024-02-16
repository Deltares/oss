/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.dsd.registration.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import nl.deltares.dsd.registration.model.Registration;

/**
 * The cache model class for representing Registration in entity cache.
 *
 * @author Erik de Rooij @ Deltares
 * @generated
 */
public class RegistrationCacheModel
	implements CacheModel<Registration>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RegistrationCacheModel)) {
			return false;
		}

		RegistrationCacheModel registrationCacheModel =
			(RegistrationCacheModel)object;

		if (registrationId == registrationCacheModel.registrationId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, registrationId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{registrationId=");
		sb.append(registrationId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", eventResourcePrimaryKey=");
		sb.append(eventResourcePrimaryKey);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", resourcePrimaryKey=");
		sb.append(resourcePrimaryKey);
		sb.append(", userPreferences=");
		sb.append(userPreferences);
		sb.append(", startTime=");
		sb.append(startTime);
		sb.append(", endTime=");
		sb.append(endTime);
		sb.append(", parentResourcePrimaryKey=");
		sb.append(parentResourcePrimaryKey);
		sb.append(", registeredByUserId=");
		sb.append(registeredByUserId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Registration toEntityModel() {
		RegistrationImpl registrationImpl = new RegistrationImpl();

		registrationImpl.setRegistrationId(registrationId);
		registrationImpl.setGroupId(groupId);
		registrationImpl.setEventResourcePrimaryKey(eventResourcePrimaryKey);
		registrationImpl.setCompanyId(companyId);
		registrationImpl.setUserId(userId);
		registrationImpl.setResourcePrimaryKey(resourcePrimaryKey);

		if (userPreferences == null) {
			registrationImpl.setUserPreferences("");
		}
		else {
			registrationImpl.setUserPreferences(userPreferences);
		}

		if (startTime == Long.MIN_VALUE) {
			registrationImpl.setStartTime(null);
		}
		else {
			registrationImpl.setStartTime(new Date(startTime));
		}

		if (endTime == Long.MIN_VALUE) {
			registrationImpl.setEndTime(null);
		}
		else {
			registrationImpl.setEndTime(new Date(endTime));
		}

		registrationImpl.setParentResourcePrimaryKey(parentResourcePrimaryKey);
		registrationImpl.setRegisteredByUserId(registeredByUserId);

		registrationImpl.resetOriginalValues();

		return registrationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		registrationId = objectInput.readLong();

		groupId = objectInput.readLong();

		eventResourcePrimaryKey = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		resourcePrimaryKey = objectInput.readLong();
		userPreferences = objectInput.readUTF();
		startTime = objectInput.readLong();
		endTime = objectInput.readLong();

		parentResourcePrimaryKey = objectInput.readLong();

		registeredByUserId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(registrationId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(eventResourcePrimaryKey);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(resourcePrimaryKey);

		if (userPreferences == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userPreferences);
		}

		objectOutput.writeLong(startTime);
		objectOutput.writeLong(endTime);

		objectOutput.writeLong(parentResourcePrimaryKey);

		objectOutput.writeLong(registeredByUserId);
	}

	public long registrationId;
	public long groupId;
	public long eventResourcePrimaryKey;
	public long companyId;
	public long userId;
	public long resourcePrimaryKey;
	public String userPreferences;
	public long startTime;
	public long endTime;
	public long parentResourcePrimaryKey;
	public long registeredByUserId;

}