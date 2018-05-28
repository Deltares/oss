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

package com.worth.deltares.subversion.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.worth.deltares.subversion.service.http.RepositoryFolderPermissionServiceSoap}.
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see com.worth.deltares.subversion.service.http.RepositoryFolderPermissionServiceSoap
 * @generated
 */
@ProviderType
public class RepositoryFolderPermissionSoap implements Serializable {
	public static RepositoryFolderPermissionSoap toSoapModel(
		RepositoryFolderPermission model) {
		RepositoryFolderPermissionSoap soapModel = new RepositoryFolderPermissionSoap();

		soapModel.setPermissionId(model.getPermissionId());
		soapModel.setFolderId(model.getFolderId());
		soapModel.setPermission(model.getPermission());
		soapModel.setRecurse(model.getRecurse());
		soapModel.setEntityType(model.getEntityType());
		soapModel.setEntityId(model.getEntityId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());

		return soapModel;
	}

	public static RepositoryFolderPermissionSoap[] toSoapModels(
		RepositoryFolderPermission[] models) {
		RepositoryFolderPermissionSoap[] soapModels = new RepositoryFolderPermissionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RepositoryFolderPermissionSoap[][] toSoapModels(
		RepositoryFolderPermission[][] models) {
		RepositoryFolderPermissionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RepositoryFolderPermissionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new RepositoryFolderPermissionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RepositoryFolderPermissionSoap[] toSoapModels(
		List<RepositoryFolderPermission> models) {
		List<RepositoryFolderPermissionSoap> soapModels = new ArrayList<RepositoryFolderPermissionSoap>(models.size());

		for (RepositoryFolderPermission model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RepositoryFolderPermissionSoap[soapModels.size()]);
	}

	public RepositoryFolderPermissionSoap() {
	}

	public long getPrimaryKey() {
		return _permissionId;
	}

	public void setPrimaryKey(long pk) {
		setPermissionId(pk);
	}

	public long getPermissionId() {
		return _permissionId;
	}

	public void setPermissionId(long permissionId) {
		_permissionId = permissionId;
	}

	public long getFolderId() {
		return _folderId;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	public String getPermission() {
		return _permission;
	}

	public void setPermission(String permission) {
		_permission = permission;
	}

	public boolean getRecurse() {
		return _recurse;
	}

	public boolean isRecurse() {
		return _recurse;
	}

	public void setRecurse(boolean recurse) {
		_recurse = recurse;
	}

	public String getEntityType() {
		return _entityType;
	}

	public void setEntityType(String entityType) {
		_entityType = entityType;
	}

	public long getEntityId() {
		return _entityId;
	}

	public void setEntityId(long entityId) {
		_entityId = entityId;
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

	private long _permissionId;
	private long _folderId;
	private String _permission;
	private boolean _recurse;
	private String _entityType;
	private long _entityId;
	private Date _createDate;
	private Date _modifiedDate;
}