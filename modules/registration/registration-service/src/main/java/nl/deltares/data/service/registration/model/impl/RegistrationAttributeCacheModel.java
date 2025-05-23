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

import nl.deltares.data.service.registration.model.RegistrationAttribute;

/**
 * The cache model class for representing RegistrationAttribute in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RegistrationAttributeCacheModel
	implements CacheModel<RegistrationAttribute>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RegistrationAttributeCacheModel)) {
			return false;
		}

		RegistrationAttributeCacheModel registrationAttributeCacheModel =
			(RegistrationAttributeCacheModel)object;

		if (registrationAttributeId ==
				registrationAttributeCacheModel.registrationAttributeId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, registrationAttributeId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{registrationAttributeId=");
		sb.append(registrationAttributeId);
		sb.append(", registrationId=");
		sb.append(registrationId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RegistrationAttribute toEntityModel() {
		RegistrationAttributeImpl registrationAttributeImpl =
			new RegistrationAttributeImpl();

		registrationAttributeImpl.setRegistrationAttributeId(
			registrationAttributeId);
		registrationAttributeImpl.setRegistrationId(registrationId);

		if (name == null) {
			registrationAttributeImpl.setName("");
		}
		else {
			registrationAttributeImpl.setName(name);
		}

		if (value == null) {
			registrationAttributeImpl.setValue("");
		}
		else {
			registrationAttributeImpl.setValue(value);
		}

		registrationAttributeImpl.resetOriginalValues();

		return registrationAttributeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		registrationAttributeId = objectInput.readLong();

		registrationId = objectInput.readLong();
		name = objectInput.readUTF();
		value = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(registrationAttributeId);

		objectOutput.writeLong(registrationId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}
	}

	public long registrationAttributeId;
	public long registrationId;
	public String name;
	public String value;

}