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

import nl.deltares.oss.download.service.persistence.DownloadPK;

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

		soapModel.setDownloadId(model.getDownloadId());
		soapModel.setUserId(model.getUserId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setPath(model.getPath());
		soapModel.setExpiryDate(model.getExpiryDate());
		soapModel.setOrganization(model.getOrganization());
		soapModel.setCountryCode(model.getCountryCode());
		soapModel.setCity(model.getCity());
		soapModel.setShareId(model.getShareId());
		soapModel.setDirectDownloadUrl(model.getDirectDownloadUrl());

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

	public DownloadPK getPrimaryKey() {
		return new DownloadPK(_downloadId, _userId);
	}

	public void setPrimaryKey(DownloadPK pk) {
		setDownloadId(pk.downloadId);
		setUserId(pk.userId);
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

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
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

	public String getPath() {
		return _path;
	}

	public void setPath(String path) {
		_path = path;
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

	public long getShareId() {
		return _shareId;
	}

	public void setShareId(long shareId) {
		_shareId = shareId;
	}

	public String getDirectDownloadUrl() {
		return _directDownloadUrl;
	}

	public void setDirectDownloadUrl(String directDownloadUrl) {
		_directDownloadUrl = directDownloadUrl;
	}

	private long _downloadId;
	private long _userId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _path;
	private Date _expiryDate;
	private String _organization;
	private String _countryCode;
	private String _city;
	private long _shareId;
	private String _directDownloadUrl;

}