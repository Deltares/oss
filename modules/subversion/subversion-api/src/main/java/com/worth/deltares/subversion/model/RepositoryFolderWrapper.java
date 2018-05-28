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
 * This class is a wrapper for {@link RepositoryFolder}.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolder
 * @generated
 */
@ProviderType
public class RepositoryFolderWrapper implements RepositoryFolder,
	ModelWrapper<RepositoryFolder> {
	public RepositoryFolderWrapper(RepositoryFolder repositoryFolder) {
		_repositoryFolder = repositoryFolder;
	}

	@Override
	public Class<?> getModelClass() {
		return RepositoryFolder.class;
	}

	@Override
	public String getModelClassName() {
		return RepositoryFolder.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("folderId", getFolderId());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("name", getName());
		attributes.put("worldRead", getWorldRead());
		attributes.put("worldWrite", getWorldWrite());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
		}

		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Boolean worldRead = (Boolean)attributes.get("worldRead");

		if (worldRead != null) {
			setWorldRead(worldRead);
		}

		Boolean worldWrite = (Boolean)attributes.get("worldWrite");

		if (worldWrite != null) {
			setWorldWrite(worldWrite);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new RepositoryFolderWrapper((RepositoryFolder)_repositoryFolder.clone());
	}

	@Override
	public int compareTo(RepositoryFolder repositoryFolder) {
		return _repositoryFolder.compareTo(repositoryFolder);
	}

	@Override
	public java.util.List<RepositoryFolder> getChildren() {
		return _repositoryFolder.getChildren();
	}

	/**
	* Returns the create date of this repository folder.
	*
	* @return the create date of this repository folder
	*/
	@Override
	public Date getCreateDate() {
		return _repositoryFolder.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _repositoryFolder.getExpandoBridge();
	}

	/**
	* Returns the folder ID of this repository folder.
	*
	* @return the folder ID of this repository folder
	*/
	@Override
	public long getFolderId() {
		return _repositoryFolder.getFolderId();
	}

	/**
	* Returns the modified date of this repository folder.
	*
	* @return the modified date of this repository folder
	*/
	@Override
	public Date getModifiedDate() {
		return _repositoryFolder.getModifiedDate();
	}

	/**
	* Returns the name of this repository folder.
	*
	* @return the name of this repository folder
	*/
	@Override
	public java.lang.String getName() {
		return _repositoryFolder.getName();
	}

	/**
	* Returns the primary key of this repository folder.
	*
	* @return the primary key of this repository folder
	*/
	@Override
	public long getPrimaryKey() {
		return _repositoryFolder.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _repositoryFolder.getPrimaryKeyObj();
	}

	/**
	* Returns the repository ID of this repository folder.
	*
	* @return the repository ID of this repository folder
	*/
	@Override
	public long getRepositoryId() {
		return _repositoryFolder.getRepositoryId();
	}

	/**
	* Returns the world read of this repository folder.
	*
	* @return the world read of this repository folder
	*/
	@Override
	public boolean getWorldRead() {
		return _repositoryFolder.getWorldRead();
	}

	/**
	* Returns the world write of this repository folder.
	*
	* @return the world write of this repository folder
	*/
	@Override
	public boolean getWorldWrite() {
		return _repositoryFolder.getWorldWrite();
	}

	@Override
	public int hashCode() {
		return _repositoryFolder.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _repositoryFolder.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _repositoryFolder.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _repositoryFolder.isNew();
	}

	/**
	* Returns <code>true</code> if this repository folder is world read.
	*
	* @return <code>true</code> if this repository folder is world read; <code>false</code> otherwise
	*/
	@Override
	public boolean isWorldRead() {
		return _repositoryFolder.isWorldRead();
	}

	/**
	* Returns <code>true</code> if this repository folder is world write.
	*
	* @return <code>true</code> if this repository folder is world write; <code>false</code> otherwise
	*/
	@Override
	public boolean isWorldWrite() {
		return _repositoryFolder.isWorldWrite();
	}

	@Override
	public void persist() {
		_repositoryFolder.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_repositoryFolder.setCachedModel(cachedModel);
	}

	/**
	* Sets the create date of this repository folder.
	*
	* @param createDate the create date of this repository folder
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_repositoryFolder.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_repositoryFolder.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_repositoryFolder.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_repositoryFolder.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the folder ID of this repository folder.
	*
	* @param folderId the folder ID of this repository folder
	*/
	@Override
	public void setFolderId(long folderId) {
		_repositoryFolder.setFolderId(folderId);
	}

	/**
	* Sets the modified date of this repository folder.
	*
	* @param modifiedDate the modified date of this repository folder
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_repositoryFolder.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this repository folder.
	*
	* @param name the name of this repository folder
	*/
	@Override
	public void setName(java.lang.String name) {
		_repositoryFolder.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_repositoryFolder.setNew(n);
	}

	/**
	* Sets the primary key of this repository folder.
	*
	* @param primaryKey the primary key of this repository folder
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_repositoryFolder.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_repositoryFolder.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the repository ID of this repository folder.
	*
	* @param repositoryId the repository ID of this repository folder
	*/
	@Override
	public void setRepositoryId(long repositoryId) {
		_repositoryFolder.setRepositoryId(repositoryId);
	}

	/**
	* Sets whether this repository folder is world read.
	*
	* @param worldRead the world read of this repository folder
	*/
	@Override
	public void setWorldRead(boolean worldRead) {
		_repositoryFolder.setWorldRead(worldRead);
	}

	/**
	* Sets whether this repository folder is world write.
	*
	* @param worldWrite the world write of this repository folder
	*/
	@Override
	public void setWorldWrite(boolean worldWrite) {
		_repositoryFolder.setWorldWrite(worldWrite);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<RepositoryFolder> toCacheModel() {
		return _repositoryFolder.toCacheModel();
	}

	@Override
	public RepositoryFolder toEscapedModel() {
		return new RepositoryFolderWrapper(_repositoryFolder.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _repositoryFolder.toString();
	}

	@Override
	public RepositoryFolder toUnescapedModel() {
		return new RepositoryFolderWrapper(_repositoryFolder.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _repositoryFolder.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RepositoryFolderWrapper)) {
			return false;
		}

		RepositoryFolderWrapper repositoryFolderWrapper = (RepositoryFolderWrapper)obj;

		if (Objects.equals(_repositoryFolder,
					repositoryFolderWrapper._repositoryFolder)) {
			return true;
		}

		return false;
	}

	@Override
	public RepositoryFolder getWrappedModel() {
		return _repositoryFolder;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _repositoryFolder.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _repositoryFolder.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_repositoryFolder.resetOriginalValues();
	}

	private final RepositoryFolder _repositoryFolder;
}