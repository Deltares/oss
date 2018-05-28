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

import com.worth.deltares.subversion.model.RepositoryLog;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing RepositoryLog in entity cache.
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLog
 * @generated
 */
@ProviderType
public class RepositoryLogCacheModel implements CacheModel<RepositoryLog>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RepositoryLogCacheModel)) {
			return false;
		}

		RepositoryLogCacheModel repositoryLogCacheModel = (RepositoryLogCacheModel)obj;

		if (logId == repositoryLogCacheModel.logId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, logId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{logId=");
		sb.append(logId);
		sb.append(", ipAddress=");
		sb.append(ipAddress);
		sb.append(", screenName=");
		sb.append(screenName);
		sb.append(", action=");
		sb.append(action);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", repository=");
		sb.append(repository);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RepositoryLog toEntityModel() {
		RepositoryLogImpl repositoryLogImpl = new RepositoryLogImpl();

		repositoryLogImpl.setLogId(logId);

		if (ipAddress == null) {
			repositoryLogImpl.setIpAddress("");
		}
		else {
			repositoryLogImpl.setIpAddress(ipAddress);
		}

		if (screenName == null) {
			repositoryLogImpl.setScreenName("");
		}
		else {
			repositoryLogImpl.setScreenName(screenName);
		}

		if (action == null) {
			repositoryLogImpl.setAction("");
		}
		else {
			repositoryLogImpl.setAction(action);
		}

		repositoryLogImpl.setCreateDate(createDate);

		if (repository == null) {
			repositoryLogImpl.setRepository("");
		}
		else {
			repositoryLogImpl.setRepository(repository);
		}

		repositoryLogImpl.resetOriginalValues();

		return repositoryLogImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		logId = objectInput.readLong();
		ipAddress = objectInput.readUTF();
		screenName = objectInput.readUTF();
		action = objectInput.readUTF();

		createDate = objectInput.readLong();
		repository = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(logId);

		if (ipAddress == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(ipAddress);
		}

		if (screenName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(screenName);
		}

		if (action == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(action);
		}

		objectOutput.writeLong(createDate);

		if (repository == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(repository);
		}
	}

	public long logId;
	public String ipAddress;
	public String screenName;
	public String action;
	public long createDate;
	public String repository;
}