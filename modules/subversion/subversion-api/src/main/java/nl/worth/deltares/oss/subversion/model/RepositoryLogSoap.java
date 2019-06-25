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

package nl.worth.deltares.oss.subversion.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link nl.worth.deltares.oss.subversion.service.http.RepositoryLogServiceSoap}.
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @generated
 */
@ProviderType
public class RepositoryLogSoap implements Serializable {

	public static RepositoryLogSoap toSoapModel(RepositoryLog model) {
		RepositoryLogSoap soapModel = new RepositoryLogSoap();

		soapModel.setLogId(model.getLogId());
		soapModel.setIpAddress(model.getIpAddress());
		soapModel.setScreenName(model.getScreenName());
		soapModel.setAction(model.getAction());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setRepository(model.getRepository());

		return soapModel;
	}

	public static RepositoryLogSoap[] toSoapModels(RepositoryLog[] models) {
		RepositoryLogSoap[] soapModels = new RepositoryLogSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RepositoryLogSoap[][] toSoapModels(RepositoryLog[][] models) {
		RepositoryLogSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RepositoryLogSoap[models.length][models[0].length];
		}
		else {
			soapModels = new RepositoryLogSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RepositoryLogSoap[] toSoapModels(List<RepositoryLog> models) {
		List<RepositoryLogSoap> soapModels = new ArrayList<RepositoryLogSoap>(
			models.size());

		for (RepositoryLog model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RepositoryLogSoap[soapModels.size()]);
	}

	public RepositoryLogSoap() {
	}

	public long getPrimaryKey() {
		return _logId;
	}

	public void setPrimaryKey(long pk) {
		setLogId(pk);
	}

	public long getLogId() {
		return _logId;
	}

	public void setLogId(long logId) {
		_logId = logId;
	}

	public String getIpAddress() {
		return _ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		_ipAddress = ipAddress;
	}

	public String getScreenName() {
		return _screenName;
	}

	public void setScreenName(String screenName) {
		_screenName = screenName;
	}

	public String getAction() {
		return _action;
	}

	public void setAction(String action) {
		_action = action;
	}

	public long getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(long createDate) {
		_createDate = createDate;
	}

	public String getRepository() {
		return _repository;
	}

	public void setRepository(String repository) {
		_repository = repository;
	}

	private long _logId;
	private String _ipAddress;
	private String _screenName;
	private String _action;
	private long _createDate;
	private String _repository;

}