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

import com.worth.deltares.subversion.model.Repository;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Repository in entity cache.
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see Repository
 * @generated
 */
@ProviderType
public class RepositoryCacheModel implements CacheModel<Repository>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RepositoryCacheModel)) {
			return false;
		}

		RepositoryCacheModel repositoryCacheModel = (RepositoryCacheModel)obj;

		if (repositoryId == repositoryCacheModel.repositoryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, repositoryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{repositoryId=");
		sb.append(repositoryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", repositoryName=");
		sb.append(repositoryName);
		sb.append(", createdDate=");
		sb.append(createdDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Repository toEntityModel() {
		RepositoryImpl repositoryImpl = new RepositoryImpl();

		repositoryImpl.setRepositoryId(repositoryId);
		repositoryImpl.setCompanyId(companyId);
		repositoryImpl.setGroupId(groupId);
		repositoryImpl.setClassNameId(classNameId);
		repositoryImpl.setClassPK(classPK);

		if (repositoryName == null) {
			repositoryImpl.setRepositoryName("");
		}
		else {
			repositoryImpl.setRepositoryName(repositoryName);
		}

		if (createdDate == Long.MIN_VALUE) {
			repositoryImpl.setCreatedDate(null);
		}
		else {
			repositoryImpl.setCreatedDate(new Date(createdDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			repositoryImpl.setModifiedDate(null);
		}
		else {
			repositoryImpl.setModifiedDate(new Date(modifiedDate));
		}

		repositoryImpl.resetOriginalValues();

		return repositoryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		repositoryId = objectInput.readLong();

		companyId = objectInput.readLong();

		groupId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		repositoryName = objectInput.readUTF();
		createdDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(repositoryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (repositoryName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(repositoryName);
		}

		objectOutput.writeLong(createdDate);
		objectOutput.writeLong(modifiedDate);
	}

	public long repositoryId;
	public long companyId;
	public long groupId;
	public long classNameId;
	public long classPK;
	public String repositoryName;
	public long createdDate;
	public long modifiedDate;
}