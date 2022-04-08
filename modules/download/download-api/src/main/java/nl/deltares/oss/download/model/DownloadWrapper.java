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

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link Download}.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see Download
 * @generated
 */
@ProviderType
public class DownloadWrapper implements Download, ModelWrapper<Download> {

	public DownloadWrapper(Download download) {
		_download = download;
	}

	@Override
	public Class<?> getModelClass() {
		return Download.class;
	}

	@Override
	public String getModelClassName() {
		return Download.class.getName();
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

		Long shareId = (Long)attributes.get("shareId");

		if (shareId != null) {
			setShareId(shareId);
		}

		String directDownloadUrl = (String)attributes.get("directDownloadUrl");

		if (directDownloadUrl != null) {
			setDirectDownloadUrl(directDownloadUrl);
		}
	}

	@Override
	public Object clone() {
		return new DownloadWrapper((Download)_download.clone());
	}

	@Override
	public int compareTo(nl.deltares.oss.download.model.Download download) {
		return _download.compareTo(download);
	}

	/**
	 * Returns the city of this download.
	 *
	 * @return the city of this download
	 */
	@Override
	public String getCity() {
		return _download.getCity();
	}

	/**
	 * Returns the company ID of this download.
	 *
	 * @return the company ID of this download
	 */
	@Override
	public long getCompanyId() {
		return _download.getCompanyId();
	}

	/**
	 * Returns the country code of this download.
	 *
	 * @return the country code of this download
	 */
	@Override
	public String getCountryCode() {
		return _download.getCountryCode();
	}

	/**
	 * Returns the create date of this download.
	 *
	 * @return the create date of this download
	 */
	@Override
	public Date getCreateDate() {
		return _download.getCreateDate();
	}

	/**
	 * Returns the direct download url of this download.
	 *
	 * @return the direct download url of this download
	 */
	@Override
	public String getDirectDownloadUrl() {
		return _download.getDirectDownloadUrl();
	}

	/**
	 * Returns the download ID of this download.
	 *
	 * @return the download ID of this download
	 */
	@Override
	public long getDownloadId() {
		return _download.getDownloadId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _download.getExpandoBridge();
	}

	/**
	 * Returns the expiry date of this download.
	 *
	 * @return the expiry date of this download
	 */
	@Override
	public Date getExpiryDate() {
		return _download.getExpiryDate();
	}

	/**
	 * Returns the file path of this download.
	 *
	 * @return the file path of this download
	 */
	@Override
	public String getFilePath() {
		return _download.getFilePath();
	}

	/**
	 * Returns the group ID of this download.
	 *
	 * @return the group ID of this download
	 */
	@Override
	public long getGroupId() {
		return _download.getGroupId();
	}

	/**
	 * Returns the ID of this download.
	 *
	 * @return the ID of this download
	 */
	@Override
	public long getId() {
		return _download.getId();
	}

	/**
	 * Returns the modified date of this download.
	 *
	 * @return the modified date of this download
	 */
	@Override
	public Date getModifiedDate() {
		return _download.getModifiedDate();
	}

	/**
	 * Returns the organization of this download.
	 *
	 * @return the organization of this download
	 */
	@Override
	public String getOrganization() {
		return _download.getOrganization();
	}

	/**
	 * Returns the primary key of this download.
	 *
	 * @return the primary key of this download
	 */
	@Override
	public long getPrimaryKey() {
		return _download.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _download.getPrimaryKeyObj();
	}

	/**
	 * Returns the share ID of this download.
	 *
	 * @return the share ID of this download
	 */
	@Override
	public long getShareId() {
		return _download.getShareId();
	}

	/**
	 * Returns the user ID of this download.
	 *
	 * @return the user ID of this download
	 */
	@Override
	public long getUserId() {
		return _download.getUserId();
	}

	/**
	 * Returns the user uuid of this download.
	 *
	 * @return the user uuid of this download
	 */
	@Override
	public String getUserUuid() {
		return _download.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _download.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _download.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _download.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _download.isNew();
	}

	@Override
	public void persist() {
		_download.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_download.setCachedModel(cachedModel);
	}

	/**
	 * Sets the city of this download.
	 *
	 * @param city the city of this download
	 */
	@Override
	public void setCity(String city) {
		_download.setCity(city);
	}

	/**
	 * Sets the company ID of this download.
	 *
	 * @param companyId the company ID of this download
	 */
	@Override
	public void setCompanyId(long companyId) {
		_download.setCompanyId(companyId);
	}

	/**
	 * Sets the country code of this download.
	 *
	 * @param countryCode the country code of this download
	 */
	@Override
	public void setCountryCode(String countryCode) {
		_download.setCountryCode(countryCode);
	}

	/**
	 * Sets the create date of this download.
	 *
	 * @param createDate the create date of this download
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_download.setCreateDate(createDate);
	}

	/**
	 * Sets the direct download url of this download.
	 *
	 * @param directDownloadUrl the direct download url of this download
	 */
	@Override
	public void setDirectDownloadUrl(String directDownloadUrl) {
		_download.setDirectDownloadUrl(directDownloadUrl);
	}

	/**
	 * Sets the download ID of this download.
	 *
	 * @param downloadId the download ID of this download
	 */
	@Override
	public void setDownloadId(long downloadId) {
		_download.setDownloadId(downloadId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_download.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_download.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_download.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the expiry date of this download.
	 *
	 * @param expiryDate the expiry date of this download
	 */
	@Override
	public void setExpiryDate(Date expiryDate) {
		_download.setExpiryDate(expiryDate);
	}

	/**
	 * Sets the file path of this download.
	 *
	 * @param filePath the file path of this download
	 */
	@Override
	public void setFilePath(String filePath) {
		_download.setFilePath(filePath);
	}

	/**
	 * Sets the group ID of this download.
	 *
	 * @param groupId the group ID of this download
	 */
	@Override
	public void setGroupId(long groupId) {
		_download.setGroupId(groupId);
	}

	/**
	 * Sets the ID of this download.
	 *
	 * @param id the ID of this download
	 */
	@Override
	public void setId(long id) {
		_download.setId(id);
	}

	/**
	 * Sets the modified date of this download.
	 *
	 * @param modifiedDate the modified date of this download
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_download.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_download.setNew(n);
	}

	/**
	 * Sets the organization of this download.
	 *
	 * @param organization the organization of this download
	 */
	@Override
	public void setOrganization(String organization) {
		_download.setOrganization(organization);
	}

	/**
	 * Sets the primary key of this download.
	 *
	 * @param primaryKey the primary key of this download
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_download.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_download.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the share ID of this download.
	 *
	 * @param shareId the share ID of this download
	 */
	@Override
	public void setShareId(long shareId) {
		_download.setShareId(shareId);
	}

	/**
	 * Sets the user ID of this download.
	 *
	 * @param userId the user ID of this download
	 */
	@Override
	public void setUserId(long userId) {
		_download.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this download.
	 *
	 * @param userUuid the user uuid of this download
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_download.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<nl.deltares.oss.download.model.Download> toCacheModel() {

		return _download.toCacheModel();
	}

	@Override
	public nl.deltares.oss.download.model.Download toEscapedModel() {
		return new DownloadWrapper(_download.toEscapedModel());
	}

	@Override
	public String toString() {
		return _download.toString();
	}

	@Override
	public nl.deltares.oss.download.model.Download toUnescapedModel() {
		return new DownloadWrapper(_download.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _download.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DownloadWrapper)) {
			return false;
		}

		DownloadWrapper downloadWrapper = (DownloadWrapper)obj;

		if (Objects.equals(_download, downloadWrapper._download)) {
			return true;
		}

		return false;
	}

	@Override
	public Download getWrappedModel() {
		return _download;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _download.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _download.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_download.resetOriginalValues();
	}

	private final Download _download;

}