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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Erik de Rooij @ Deltares
 * @generated
 */
@ProviderType
public class DownloadSoap implements Serializable {

	public static DownloadSoap toSoapModel(Download model) {
		DownloadSoap soapModel = new DownloadSoap();

		soapModel.setId(model.getId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setDownloadId(model.getDownloadId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setFilePath(model.getFilePath());
		soapModel.setExpiryDate(model.getExpiryDate());
		soapModel.setOrganization(model.getOrganization());
		soapModel.setCountryCode(model.getCountryCode());
		soapModel.setCity(model.getCity());
		soapModel.setShareId(model.getShareId());
		soapModel.setDirectDownloadUrl(model.getDirectDownloadUrl());
		soapModel.setLicenseDownloadUrl(model.getLicenseDownloadUrl());

		return soapModel;
	}

	public static DownloadSoap[] toSoapModels(Download[] models) {
		DownloadSoap[] soapModels = new DownloadSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DownloadSoap[][] toSoapModels(Download[][] models) {
		DownloadSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DownloadSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DownloadSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DownloadSoap[] toSoapModels(List<Download> models) {
		List<DownloadSoap> soapModels = new ArrayList<DownloadSoap>(
			models.size());

		for (Download model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DownloadSoap[soapModels.size()]);
	}

	public DownloadSoap() {
	}

	public long getPrimaryKey() {
		return _id;
	}

	public void setPrimaryKey(long pk) {
		setId(pk);
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getDownloadId() {
		return _downloadId;
	}

	public void setDownloadId(long downloadId) {
		_downloadId = downloadId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getFilePath() {
		return _filePath;
	}

	public void setFilePath(String filePath) {
		_filePath = filePath;
	}

	public Date getExpiryDate() {
		return _expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		_expiryDate = expiryDate;
	}

	public String getOrganization() {
		return _organization;
	}

	public void setOrganization(String organization) {
		_organization = organization;
	}

	public String getCountryCode() {
		return _countryCode;
	}

	public void setCountryCode(String countryCode) {
		_countryCode = countryCode;
	}

	public String getCity() {
		return _city;
	}

	public void setCity(String city) {
		_city = city;
	}

	public int getShareId() {
		return _shareId;
	}

	public void setShareId(int shareId) {
		_shareId = shareId;
	}

	public String getDirectDownloadUrl() {
		return _directDownloadUrl;
	}

	public void setDirectDownloadUrl(String directDownloadUrl) {
		_directDownloadUrl = directDownloadUrl;
	}

	public String getLicenseDownloadUrl() {
		return _licenseDownloadUrl;
	}

	public void setLicenseDownloadUrl(String licenseDownloadUrl) {
		_licenseDownloadUrl = licenseDownloadUrl;
	}

	private long _id;
	private long _companyId;
	private long _groupId;
	private long _downloadId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _filePath;
	private Date _expiryDate;
	private String _organization;
	private String _countryCode;
	private String _city;
	private int _shareId;
	private String _directDownloadUrl;
	private String _licenseDownloadUrl;

}