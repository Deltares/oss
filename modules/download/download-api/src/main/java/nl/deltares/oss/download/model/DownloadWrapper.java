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

package nl.deltares.oss.download.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Download}.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see Download
 * @generated
 */
public class DownloadWrapper
	extends BaseModelWrapper<Download>
	implements Download, ModelWrapper<Download> {

	public DownloadWrapper(Download download) {
		super(download);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("id", getId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("downloadId", getDownloadId());
		attributes.put("fileName", getFileName());
		attributes.put("expiryDate", getExpiryDate());
		attributes.put("organization", getOrganization());
		attributes.put("geoLocationId", getGeoLocationId());
		attributes.put("fileShareUrl", getFileShareUrl());
		attributes.put("licenseDownloadUrl", getLicenseDownloadUrl());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long id = (Long)attributes.get("id");

		if (id != null) {
			setId(id);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long downloadId = (Long)attributes.get("downloadId");

		if (downloadId != null) {
			setDownloadId(downloadId);
		}

		String fileName = (String)attributes.get("fileName");

		if (fileName != null) {
			setFileName(fileName);
		}

		Date expiryDate = (Date)attributes.get("expiryDate");

		if (expiryDate != null) {
			setExpiryDate(expiryDate);
		}

		String organization = (String)attributes.get("organization");

		if (organization != null) {
			setOrganization(organization);
		}

		Long geoLocationId = (Long)attributes.get("geoLocationId");

		if (geoLocationId != null) {
			setGeoLocationId(geoLocationId);
		}

		String fileShareUrl = (String)attributes.get("fileShareUrl");

		if (fileShareUrl != null) {
			setFileShareUrl(fileShareUrl);
		}

		String licenseDownloadUrl = (String)attributes.get(
			"licenseDownloadUrl");

		if (licenseDownloadUrl != null) {
			setLicenseDownloadUrl(licenseDownloadUrl);
		}
	}

	@Override
	public Download cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this download.
	 *
	 * @return the company ID of this download
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this download.
	 *
	 * @return the create date of this download
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the download ID of this download.
	 *
	 * @return the download ID of this download
	 */
	@Override
	public long getDownloadId() {
		return model.getDownloadId();
	}

	/**
	 * Returns the expiry date of this download.
	 *
	 * @return the expiry date of this download
	 */
	@Override
	public Date getExpiryDate() {
		return model.getExpiryDate();
	}

	/**
	 * Returns the file name of this download.
	 *
	 * @return the file name of this download
	 */
	@Override
	public String getFileName() {
		return model.getFileName();
	}

	/**
	 * Returns the file share url of this download.
	 *
	 * @return the file share url of this download
	 */
	@Override
	public String getFileShareUrl() {
		return model.getFileShareUrl();
	}

	/**
	 * Returns the geo location ID of this download.
	 *
	 * @return the geo location ID of this download
	 */
	@Override
	public long getGeoLocationId() {
		return model.getGeoLocationId();
	}

	/**
	 * Returns the group ID of this download.
	 *
	 * @return the group ID of this download
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the ID of this download.
	 *
	 * @return the ID of this download
	 */
	@Override
	public long getId() {
		return model.getId();
	}

	/**
	 * Returns the license download url of this download.
	 *
	 * @return the license download url of this download
	 */
	@Override
	public String getLicenseDownloadUrl() {
		return model.getLicenseDownloadUrl();
	}

	/**
	 * Returns the modified date of this download.
	 *
	 * @return the modified date of this download
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the organization of this download.
	 *
	 * @return the organization of this download
	 */
	@Override
	public String getOrganization() {
		return model.getOrganization();
	}

	/**
	 * Returns the primary key of this download.
	 *
	 * @return the primary key of this download
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this download.
	 *
	 * @return the user ID of this download
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this download.
	 *
	 * @return the user uuid of this download
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this download.
	 *
	 * @param companyId the company ID of this download
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this download.
	 *
	 * @param createDate the create date of this download
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the download ID of this download.
	 *
	 * @param downloadId the download ID of this download
	 */
	@Override
	public void setDownloadId(long downloadId) {
		model.setDownloadId(downloadId);
	}

	/**
	 * Sets the expiry date of this download.
	 *
	 * @param expiryDate the expiry date of this download
	 */
	@Override
	public void setExpiryDate(Date expiryDate) {
		model.setExpiryDate(expiryDate);
	}

	/**
	 * Sets the file name of this download.
	 *
	 * @param fileName the file name of this download
	 */
	@Override
	public void setFileName(String fileName) {
		model.setFileName(fileName);
	}

	/**
	 * Sets the file share url of this download.
	 *
	 * @param fileShareUrl the file share url of this download
	 */
	@Override
	public void setFileShareUrl(String fileShareUrl) {
		model.setFileShareUrl(fileShareUrl);
	}

	/**
	 * Sets the geo location ID of this download.
	 *
	 * @param geoLocationId the geo location ID of this download
	 */
	@Override
	public void setGeoLocationId(long geoLocationId) {
		model.setGeoLocationId(geoLocationId);
	}

	/**
	 * Sets the group ID of this download.
	 *
	 * @param groupId the group ID of this download
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the ID of this download.
	 *
	 * @param id the ID of this download
	 */
	@Override
	public void setId(long id) {
		model.setId(id);
	}

	/**
	 * Sets the license download url of this download.
	 *
	 * @param licenseDownloadUrl the license download url of this download
	 */
	@Override
	public void setLicenseDownloadUrl(String licenseDownloadUrl) {
		model.setLicenseDownloadUrl(licenseDownloadUrl);
	}

	/**
	 * Sets the modified date of this download.
	 *
	 * @param modifiedDate the modified date of this download
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the organization of this download.
	 *
	 * @param organization the organization of this download
	 */
	@Override
	public void setOrganization(String organization) {
		model.setOrganization(organization);
	}

	/**
	 * Sets the primary key of this download.
	 *
	 * @param primaryKey the primary key of this download
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this download.
	 *
	 * @param userId the user ID of this download
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this download.
	 *
	 * @param userUuid the user uuid of this download
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected DownloadWrapper wrap(Download download) {
		return new DownloadWrapper(download);
	}

}