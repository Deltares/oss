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

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import nl.deltares.oss.download.model.Download;

/**
 * The cache model class for representing Download in entity cache.
 *
 * @author Erik de Rooij @ Deltares
 * @generated
 */
public class DownloadCacheModel
	implements CacheModel<Download>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DownloadCacheModel)) {
			return false;
		}

		DownloadCacheModel downloadCacheModel = (DownloadCacheModel)object;

		if (id == downloadCacheModel.id) {
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
		StringBundler sb = new StringBundler(27);

		sb.append("{id=");
		sb.append(id);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", downloadId=");
		sb.append(downloadId);
		sb.append(", fileName=");
		sb.append(fileName);
		sb.append(", expiryDate=");
		sb.append(expiryDate);
		sb.append(", organization=");
		sb.append(organization);
		sb.append(", geoLocationId=");
		sb.append(geoLocationId);
		sb.append(", fileShareUrl=");
		sb.append(fileShareUrl);
		sb.append(", licenseDownloadUrl=");
		sb.append(licenseDownloadUrl);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Download toEntityModel() {
		DownloadImpl downloadImpl = new DownloadImpl();

		downloadImpl.setId(id);
		downloadImpl.setGroupId(groupId);
		downloadImpl.setCompanyId(companyId);
		downloadImpl.setUserId(userId);

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

		downloadImpl.setDownloadId(downloadId);

		if (fileName == null) {
			downloadImpl.setFileName("");
		}
		else {
			downloadImpl.setFileName(fileName);
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

		downloadImpl.setGeoLocationId(geoLocationId);

		if (fileShareUrl == null) {
			downloadImpl.setFileShareUrl("");
		}
		else {
			downloadImpl.setFileShareUrl(fileShareUrl);
		}

		if (licenseDownloadUrl == null) {
			downloadImpl.setLicenseDownloadUrl("");
		}
		else {
			downloadImpl.setLicenseDownloadUrl(licenseDownloadUrl);
		}

		downloadImpl.resetOriginalValues();

		return downloadImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		id = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		downloadId = objectInput.readLong();
		fileName = objectInput.readUTF();
		expiryDate = objectInput.readLong();
		organization = objectInput.readUTF();

		geoLocationId = objectInput.readLong();
		fileShareUrl = objectInput.readUTF();
		licenseDownloadUrl = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(id);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(downloadId);

		if (fileName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fileName);
		}

		objectOutput.writeLong(expiryDate);

		if (organization == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(organization);
		}

		objectOutput.writeLong(geoLocationId);

		if (fileShareUrl == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fileShareUrl);
		}

		if (licenseDownloadUrl == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(licenseDownloadUrl);
		}
	}

	public long id;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public long downloadId;
	public String fileName;
	public long expiryDate;
	public String organization;
	public long geoLocationId;
	public String fileShareUrl;
	public String licenseDownloadUrl;

}