/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.oss.download.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import nl.deltares.oss.download.model.DownloadCount;

/**
 * The cache model class for representing DownloadCount in entity cache.
 *
 * @author Erik de Rooij @ Deltares
 * @generated
 */
public class DownloadCountCacheModel
	implements CacheModel<DownloadCount>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DownloadCountCacheModel)) {
			return false;
		}

		DownloadCountCacheModel downloadCountCacheModel =
			(DownloadCountCacheModel)object;

		if (id == downloadCountCacheModel.id) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, id);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{id=");
		sb.append(id);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", downloadId=");
		sb.append(downloadId);
		sb.append(", count=");
		sb.append(count);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DownloadCount toEntityModel() {
		DownloadCountImpl downloadCountImpl = new DownloadCountImpl();

		downloadCountImpl.setId(id);
		downloadCountImpl.setCompanyId(companyId);
		downloadCountImpl.setGroupId(groupId);
		downloadCountImpl.setDownloadId(downloadId);
		downloadCountImpl.setCount(count);

		downloadCountImpl.resetOriginalValues();

		return downloadCountImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		id = objectInput.readLong();

		companyId = objectInput.readLong();

		groupId = objectInput.readLong();

		downloadId = objectInput.readLong();

		count = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(id);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(downloadId);

		objectOutput.writeInt(count);
	}

	public long id;
	public long companyId;
	public long groupId;
	public long downloadId;
	public int count;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1031717972