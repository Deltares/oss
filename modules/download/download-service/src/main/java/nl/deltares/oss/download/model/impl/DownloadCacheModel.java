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

import java.util.Date;

import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.service.persistence.DownloadPK;

/**
 * The cache model class for representing Download in entity cache.
 *
 * @author Erik de Rooij @ Deltares
 * @generated
 */
@ProviderType
public class DownloadCacheModel
	implements CacheModel<Download>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DownloadCacheModel)) {
			return false;
		}

		DownloadCacheModel downloadCacheModel = (DownloadCacheModel)obj;

		if (downloadPK.equals(downloadCacheModel.downloadPK)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, downloadPK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{downloadId=");
		sb.append(downloadId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", path=");
		sb.append(path);
		sb.append(", expiryDate=");
		sb.append(expiryDate);
		sb.append(", organization=");
		sb.append(organization);
		sb.append(", countryCode=");
		sb.append(countryCode);
		sb.append(", city=");
		sb.append(city);
		sb.append(", shareId=");
		sb.append(shareId);
		sb.append(", directDownloadUrl=");
		sb.append(directDownloadUrl);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Download toEntityModel() {
		DownloadImpl downloadImpl = new DownloadImpl();

		downloadImpl.setDownloadId(downloadId);
		downloadImpl.setUserId(userId);
		downloadImpl.setGroupId(groupId);
		downloadImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			downloadImpl.setCreateDate(null);
		}
		else {
			downloadImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			downloadImpl.setModifiedDate(null);
		}
		else {
			downloadImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (path == null) {
			downloadImpl.setPath("");
		}
		else {
			downloadImpl.setPath(path);
		}

		if (expiryDate == Long.MIN_VALUE) {
			downloadImpl.setExpiryDate(null);
		}
		else {
			downloadImpl.setExpiryDate(new Date(expiryDate));
		}

		if (organization == null) {
			downloadImpl.setOrganization("");
		}
		else {
			downloadImpl.setOrganization(organization);
		}

		if (countryCode == null) {
			downloadImpl.setCountryCode("");
		}
		else {
			downloadImpl.setCountryCode(countryCode);
		}

		if (city == null) {
			downloadImpl.setCity("");
		}
		else {
			downloadImpl.setCity(city);
		}

		downloadImpl.setShareId(shareId);

		if (directDownloadUrl == null) {
			downloadImpl.setDirectDownloadUrl("");
		}
		else {
			downloadImpl.setDirectDownloadUrl(directDownloadUrl);
		}

		downloadImpl.resetOriginalValues();

		return downloadImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		downloadId = objectInput.readLong();

		userId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		path = objectInput.readUTF();
		expiryDate = objectInput.readLong();
		organization = objectInput.readUTF();
		countryCode = objectInput.readUTF();
		city = objectInput.readUTF();

		shareId = objectInput.readLong();
		directDownloadUrl = objectInput.readUTF();

		downloadPK = new DownloadPK(downloadId, userId);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(downloadId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (path == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(path);
		}

		objectOutput.writeLong(expiryDate);

		if (organization == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(organization);
		}

		if (countryCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(countryCode);
		}

		if (city == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(city);
		}

		objectOutput.writeLong(shareId);

		if (directDownloadUrl == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(directDownloadUrl);
		}
	}

	public long downloadId;
	public long userId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public String path;
	public long expiryDate;
	public String organization;
	public String countryCode;
	public String city;
	public long shareId;
	public String directDownloadUrl;
	public transient DownloadPK downloadPK;

}