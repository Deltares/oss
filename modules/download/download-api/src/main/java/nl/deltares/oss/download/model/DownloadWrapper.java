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
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("downloadId", getDownloadId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("filePath", getFilePath());
		attributes.put("expiryDate", getExpiryDate());
		attributes.put("organization", getOrganization());
		attributes.put("countryCode", getCountryCode());
		attributes.put("city", getCity());
		attributes.put("shareId", getShareId());
		attributes.put("directDownloadUrl", getDirectDownloadUrl());
		attributes.put("licenseDownloadUrl", getLicenseDownloadUrl());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long id = (Long)attributes.get("id");

		if (id != null) {
			setId(id);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long downloadId = (Long)attributes.get("downloadId");

		if (downloadId != null) {
			setDownloadId(downloadId);
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

		String filePath = (String)attributes.get("filePath");

		if (filePath != null) {
			setFilePath(filePath);
		}

		Date expiryDate = (Date)attributes.get("expiryDate");

		if (expiryDate != null) {
			setExpiryDate(expiryDate);
		}

		String organization = (String)attributes.get("organization");

		if (organization != null) {
			setOrganization(organization);
		}

		String countryCode = (String)attributes.get("countryCode");

		if (countryCode != null) {
			setCountryCode(countryCode);
		}

		String city = (String)attributes.get("city");

		if (city != null) {
			setCity(city);
		}

		Integer shareId = (Integer)attributes.get("shareId");

		if (shareId != null) {
			setShareId(shareId);
		}

		String directDownloadUrl = (String)attributes.get("directDownloadUrl");

		if (directDownloadUrl != null) {
			setDirectDownloadUrl(directDownloadUrl);
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
	 * Returns the city of this download.
	 *
	 * @return the city of this download
	 */
	@Override
	public String getCity() {
		return model.getCity();
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
	 * Returns the country code of this download.
	 *
	 * @return the country code of this download
	 */
	@Override
	public String getCountryCode() {
		return model.getCountryCode();
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
	 * Returns the direct download url of this download.
	 *
	 * @return the direct download url of this download
	 */
	@Override
	public String getDirectDownloadUrl() {
		return model.getDirectDownloadUrl();
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
	 * Returns the file path of this download.
	 *
	 * @return the file path of this download
	 */
	@Override
	public String getFilePath() {
		return model.getFilePath();
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
	 * Returns the share ID of this download.
	 *
	 * @return the share ID of this download
	 */
	@Override
	public int getShareId() {
		return model.getShareId();
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
	 * Sets the city of this download.
	 *
	 * @param city the city of this download
	 */
	@Override
	public void setCity(String city) {
		model.setCity(city);
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
	 * Sets the country code of this download.
	 *
	 * @param countryCode the country code of this download
	 */
	@Override
	public void setCountryCode(String countryCode) {
		model.setCountryCode(countryCode);
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
	 * Sets the direct download url of this download.
	 *
	 * @param directDownloadUrl the direct download url of this download
	 */
	@Override
	public void setDirectDownloadUrl(String directDownloadUrl) {
		model.setDirectDownloadUrl(directDownloadUrl);
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
	 * Sets the file path of this download.
	 *
	 * @param filePath the file path of this download
	 */
	@Override
	public void setFilePath(String filePath) {
		model.setFilePath(filePath);
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
	 * Sets the share ID of this download.
	 *
	 * @param shareId the share ID of this download
	 */
	@Override
	public void setShareId(int shareId) {
		model.setShareId(shareId);
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