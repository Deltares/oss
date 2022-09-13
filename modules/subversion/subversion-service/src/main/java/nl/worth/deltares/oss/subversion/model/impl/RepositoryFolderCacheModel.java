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

package nl.worth.deltares.oss.subversion.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import nl.worth.deltares.oss.subversion.model.RepositoryFolder;

/**
 * The cache model class for representing RepositoryFolder in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RepositoryFolderCacheModel
	implements CacheModel<RepositoryFolder>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RepositoryFolderCacheModel)) {
			return false;
		}

		RepositoryFolderCacheModel repositoryFolderCacheModel =
			(RepositoryFolderCacheModel)object;

		if (folderId == repositoryFolderCacheModel.folderId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, folderId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{folderId=");
		sb.append(folderId);
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", worldRead=");
		sb.append(worldRead);
		sb.append(", worldWrite=");
		sb.append(worldWrite);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RepositoryFolder toEntityModel() {
		RepositoryFolderImpl repositoryFolderImpl = new RepositoryFolderImpl();

		repositoryFolderImpl.setFolderId(folderId);
		repositoryFolderImpl.setRepositoryId(repositoryId);

		if (name == null) {
			repositoryFolderImpl.setName("");
		}
		else {
			repositoryFolderImpl.setName(name);
		}

		repositoryFolderImpl.setWorldRead(worldRead);
		repositoryFolderImpl.setWorldWrite(worldWrite);

		if (createDate == Long.MIN_VALUE) {
			repositoryFolderImpl.setCreateDate(null);
		}
		else {
			repositoryFolderImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			repositoryFolderImpl.setModifiedDate(null);
		}
		else {
			repositoryFolderImpl.setModifiedDate(new Date(modifiedDate));
		}

		repositoryFolderImpl.resetOriginalValues();

		return repositoryFolderImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		folderId = objectInput.readLong();

		repositoryId = objectInput.readLong();
		name = objectInput.readUTF();

		worldRead = objectInput.readBoolean();

		worldWrite = objectInput.readBoolean();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(folderId);

		objectOutput.writeLong(repositoryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeBoolean(worldRead);

		objectOutput.writeBoolean(worldWrite);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
	}

	public long folderId;
	public long repositoryId;
	public String name;
	public boolean worldRead;
	public boolean worldWrite;
	public long createDate;
	public long modifiedDate;

}