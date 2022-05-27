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

package nl.deltares.dsd.registration.model;

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
public class RegistrationSoap implements Serializable {

	public static RegistrationSoap toSoapModel(Registration model) {
		RegistrationSoap soapModel = new RegistrationSoap();

		soapModel.setRegistrationId(model.getRegistrationId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setEventResourcePrimaryKey(
			model.getEventResourcePrimaryKey());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setResourcePrimaryKey(model.getResourcePrimaryKey());
		soapModel.setUserPreferences(model.getUserPreferences());
		soapModel.setStartTime(model.getStartTime());
		soapModel.setEndTime(model.getEndTime());
		soapModel.setParentResourcePrimaryKey(
			model.getParentResourcePrimaryKey());
		soapModel.setRegisteredByUserId(model.getRegisteredByUserId());

		return soapModel;
	}

	public static RegistrationSoap[] toSoapModels(Registration[] models) {
		RegistrationSoap[] soapModels = new RegistrationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RegistrationSoap[][] toSoapModels(Registration[][] models) {
		RegistrationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RegistrationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new RegistrationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RegistrationSoap[] toSoapModels(List<Registration> models) {
		List<RegistrationSoap> soapModels = new ArrayList<RegistrationSoap>(
			models.size());

		for (Registration model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RegistrationSoap[soapModels.size()]);
	}

	public RegistrationSoap() {
	}

	public long getPrimaryKey() {
		return _registrationId;
	}

	public void setPrimaryKey(long pk) {
		setRegistrationId(pk);
	}

	public long getRegistrationId() {
		return _registrationId;
	}

	public void setRegistrationId(long registrationId) {
		_registrationId = registrationId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getEventResourcePrimaryKey() {
		return _eventResourcePrimaryKey;
	}

	public void setEventResourcePrimaryKey(long eventResourcePrimaryKey) {
		_eventResourcePrimaryKey = eventResourcePrimaryKey;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getResourcePrimaryKey() {
		return _resourcePrimaryKey;
	}

	public void setResourcePrimaryKey(long resourcePrimaryKey) {
		_resourcePrimaryKey = resourcePrimaryKey;
	}

	public String getUserPreferences() {
		return _userPreferences;
	}

	public void setUserPreferences(String userPreferences) {
		_userPreferences = userPreferences;
	}

	public Date getStartTime() {
		return _startTime;
	}

	public void setStartTime(Date startTime) {
		_startTime = startTime;
	}

	public Date getEndTime() {
		return _endTime;
	}

	public void setEndTime(Date endTime) {
		_endTime = endTime;
	}

	public long getParentResourcePrimaryKey() {
		return _parentResourcePrimaryKey;
	}

	public void setParentResourcePrimaryKey(long parentResourcePrimaryKey) {
		_parentResourcePrimaryKey = parentResourcePrimaryKey;
	}

	public long getRegisteredByUserId() {
		return _registeredByUserId;
	}

	public void setRegisteredByUserId(long registeredByUserId) {
		_registeredByUserId = registeredByUserId;
	}

	private long _registrationId;
	private long _groupId;
	private long _eventResourcePrimaryKey;
	private long _companyId;
	private long _userId;
	private long _resourcePrimaryKey;
	private String _userPreferences;
	private Date _startTime;
	private Date _endTime;
	private long _parentResourcePrimaryKey;
	private long _registeredByUserId;

}