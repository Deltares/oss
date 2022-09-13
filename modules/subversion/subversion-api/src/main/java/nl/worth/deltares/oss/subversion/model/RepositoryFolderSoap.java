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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link nl.worth.deltares.oss.subversion.service.http.RepositoryFolderServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class RepositoryFolderSoap implements Serializable {

	public static RepositoryFolderSoap toSoapModel(RepositoryFolder model) {
		RepositoryFolderSoap soapModel = new RepositoryFolderSoap();

		soapModel.setFolderId(model.getFolderId());
		soapModel.setRepositoryId(model.getRepositoryId());
		soapModel.setName(model.getName());
		soapModel.setWorldRead(model.isWorldRead());
		soapModel.setWorldWrite(model.isWorldWrite());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());

		return soapModel;
	}

	public static RepositoryFolderSoap[] toSoapModels(
		RepositoryFolder[] models) {

		RepositoryFolderSoap[] soapModels =
			new RepositoryFolderSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RepositoryFolderSoap[][] toSoapModels(
		RepositoryFolder[][] models) {

		RepositoryFolderSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new RepositoryFolderSoap[models.length][models[0].length];
		}
		else {
			soapModels = new RepositoryFolderSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RepositoryFolderSoap[] toSoapModels(
		List<RepositoryFolder> models) {

		List<RepositoryFolderSoap> soapModels =
			new ArrayList<RepositoryFolderSoap>(models.size());

		for (RepositoryFolder model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RepositoryFolderSoap[soapModels.size()]);
	}

	public RepositoryFolderSoap() {
	}

	public long getPrimaryKey() {
		return _folderId;
	}

	public void setPrimaryKey(long pk) {
		setFolderId(pk);
	}

	public long getFolderId() {
		return _folderId;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public boolean getWorldRead() {
		return _worldRead;
	}

	public boolean isWorldRead() {
		return _worldRead;
	}

	public void setWorldRead(boolean worldRead) {
		_worldRead = worldRead;
	}

	public boolean getWorldWrite() {
		return _worldWrite;
	}

	public boolean isWorldWrite() {
		return _worldWrite;
	}

	public void setWorldWrite(boolean worldWrite) {
		_worldWrite = worldWrite;
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

	private long _folderId;
	private long _repositoryId;
	private String _name;
	private boolean _worldRead;
	private boolean _worldWrite;
	private Date _createDate;
	private Date _modifiedDate;

}