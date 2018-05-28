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

package com.worth.deltares.subversion.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import com.worth.deltares.subversion.model.RepositoryFolderPermission;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing RepositoryFolderPermission in entity cache.
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolderPermission
 * @generated
 */
@ProviderType
public class RepositoryFolderPermissionCacheModel implements CacheModel<RepositoryFolderPermission>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RepositoryFolderPermissionCacheModel)) {
			return false;
		}

		RepositoryFolderPermissionCacheModel repositoryFolderPermissionCacheModel =
			(RepositoryFolderPermissionCacheModel)obj;

		if (permissionId == repositoryFolderPermissionCacheModel.permissionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, permissionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{permissionId=");
		sb.append(permissionId);
		sb.append(", folderId=");
		sb.append(folderId);
		sb.append(", permission=");
		sb.append(permission);
		sb.append(", recurse=");
		sb.append(recurse);
		sb.append(", entityType=");
		sb.append(entityType);
		sb.append(", entityId=");
		sb.append(entityId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RepositoryFolderPermission toEntityModel() {
		RepositoryFolderPermissionImpl repositoryFolderPermissionImpl = new RepositoryFolderPermissionImpl();

		repositoryFolderPermissionImpl.setPermissionId(permissionId);
		repositoryFolderPermissionImpl.setFolderId(folderId);

		if (permission == null) {
			repositoryFolderPermissionImpl.setPermission("");
		}
		else {
			repositoryFolderPermissionImpl.setPermission(permission);
		}

		repositoryFolderPermissionImpl.setRecurse(recurse);

		if (entityType == null) {
			repositoryFolderPermissionImpl.setEntityType("");
		}
		else {
			repositoryFolderPermissionImpl.setEntityType(entityType);
		}

		repositoryFolderPermissionImpl.setEntityId(entityId);

		if (createDate == Long.MIN_VALUE) {
			repositoryFolderPermissionImpl.setCreateDate(null);
		}
		else {
			repositoryFolderPermissionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			repositoryFolderPermissionImpl.setModifiedDate(null);
		}
		else {
			repositoryFolderPermissionImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		repositoryFolderPermissionImpl.resetOriginalValues();

		return repositoryFolderPermissionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		permissionId = objectInput.readLong();

		folderId = objectInput.readLong();
		permission = objectInput.readUTF();

		recurse = objectInput.readBoolean();
		entityType = objectInput.readUTF();

		entityId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(permissionId);

		objectOutput.writeLong(folderId);

		if (permission == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(permission);
		}

		objectOutput.writeBoolean(recurse);

		if (entityType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(entityType);
		}

		objectOutput.writeLong(entityId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
	}

	public long permissionId;
	public long folderId;
	public String permission;
	public boolean recurse;
	public String entityType;
	public long entityId;
	public long createDate;
	public long modifiedDate;
}