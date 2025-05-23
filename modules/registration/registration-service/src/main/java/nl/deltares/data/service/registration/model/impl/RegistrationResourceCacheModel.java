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

import nl.deltares.data.service.registration.model.RegistrationResource;

/**
 * The cache model class for representing RegistrationResource in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RegistrationResourceCacheModel
	implements CacheModel<RegistrationResource>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RegistrationResourceCacheModel)) {
			return false;
		}

		RegistrationResourceCacheModel registrationResourceCacheModel =
			(RegistrationResourceCacheModel)object;

		if (registrationResourceId ==
				registrationResourceCacheModel.registrationResourceId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, registrationResourceId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{registrationResourceId=");
		sb.append(registrationResourceId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", eventResourceId=");
		sb.append(eventResourceId);
		sb.append(", parentResourceId=");
		sb.append(parentResourceId);
		sb.append(", resourceName=");
		sb.append(resourceName);
		sb.append(", eventResourceName=");
		sb.append(eventResourceName);
		sb.append(", eventArticleId=");
		sb.append(eventArticleId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RegistrationResource toEntityModel() {
		RegistrationResourceImpl registrationResourceImpl =
			new RegistrationResourceImpl();

		registrationResourceImpl.setRegistrationResourceId(
			registrationResourceId);
		registrationResourceImpl.setCompanyId(companyId);
		registrationResourceImpl.setGroupId(groupId);
		registrationResourceImpl.setEventResourceId(eventResourceId);
		registrationResourceImpl.setParentResourceId(parentResourceId);

		if (resourceName == null) {
			registrationResourceImpl.setResourceName("");
		}
		else {
			registrationResourceImpl.setResourceName(resourceName);
		}

		if (eventResourceName == null) {
			registrationResourceImpl.setEventResourceName("");
		}
		else {
			registrationResourceImpl.setEventResourceName(eventResourceName);
		}

		registrationResourceImpl.setEventArticleId(eventArticleId);

		registrationResourceImpl.resetOriginalValues();

		return registrationResourceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		registrationResourceId = objectInput.readLong();

		companyId = objectInput.readLong();

		groupId = objectInput.readLong();

		eventResourceId = objectInput.readLong();

		parentResourceId = objectInput.readLong();
		resourceName = objectInput.readUTF();
		eventResourceName = objectInput.readUTF();

		eventArticleId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(registrationResourceId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(eventResourceId);

		objectOutput.writeLong(parentResourceId);

		if (resourceName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(resourceName);
		}

		if (eventResourceName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(eventResourceName);
		}

		objectOutput.writeLong(eventArticleId);
	}

	public long registrationResourceId;
	public long companyId;
	public long groupId;
	public long eventResourceId;
	public long parentResourceId;
	public String resourceName;
	public String eventResourceName;
	public long eventArticleId;

}