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

package nl.deltares.oss.download.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

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
@ProviderType
public class DownloadCountCacheModel
	implements CacheModel<DownloadCount>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DownloadCountCacheModel)) {
			return false;
		}

		DownloadCountCacheModel downloadCountCacheModel =
			(DownloadCountCacheModel)obj;

		if (downloadId == downloadCountCacheModel.downloadId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, downloadId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{downloadId=");
		sb.append(downloadId);
		sb.append(", count=");
		sb.append(count);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DownloadCount toEntityModel() {
		DownloadCountImpl downloadCountImpl = new DownloadCountImpl();

		downloadCountImpl.setDownloadId(downloadId);
		downloadCountImpl.setCount(count);

		downloadCountImpl.resetOriginalValues();

		return downloadCountImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		downloadId = objectInput.readLong();

		count = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(downloadId);

		objectOutput.writeInt(count);
	}

	public long downloadId;
	public int count;

}