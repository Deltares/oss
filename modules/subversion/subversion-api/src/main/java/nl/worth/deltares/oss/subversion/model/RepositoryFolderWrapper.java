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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RepositoryFolder}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryFolder
 * @generated
 */
public class RepositoryFolderWrapper
	extends BaseModelWrapper<RepositoryFolder>
	implements ModelWrapper<RepositoryFolder>, RepositoryFolder {

	public RepositoryFolderWrapper(RepositoryFolder repositoryFolder) {
		super(repositoryFolder);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("folderId", getFolderId());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("name", getName());
		attributes.put("worldRead", isWorldRead());
		attributes.put("worldWrite", isWorldWrite());
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
	public RepositoryFolder cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public java.util.List
		<nl.worth.deltares.oss.subversion.model.RepositoryFolder>
			getChildren() {

		return model.getChildren();
	}

	/**
	 * Returns the create date of this repository folder.
	 *
	 * @return the create date of this repository folder
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the folder ID of this repository folder.
	 *
	 * @return the folder ID of this repository folder
	 */
	@Override
	public long getFolderId() {
		return model.getFolderId();
	}

	/**
	 * Returns the modified date of this repository folder.
	 *
	 * @return the modified date of this repository folder
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this repository folder.
	 *
	 * @return the name of this repository folder
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this repository folder.
	 *
	 * @return the primary key of this repository folder
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the repository ID of this repository folder.
	 *
	 * @return the repository ID of this repository folder
	 */
	@Override
	public long getRepositoryId() {
		return model.getRepositoryId();
	}

	/**
	 * Returns the world read of this repository folder.
	 *
	 * @return the world read of this repository folder
	 */
	@Override
	public boolean getWorldRead() {
		return model.getWorldRead();
	}

	/**
	 * Returns the world write of this repository folder.
	 *
	 * @return the world write of this repository folder
	 */
	@Override
	public boolean getWorldWrite() {
		return model.getWorldWrite();
	}

	/**
	 * Returns <code>true</code> if this repository folder is world read.
	 *
	 * @return <code>true</code> if this repository folder is world read; <code>false</code> otherwise
	 */
	@Override
	public boolean isWorldRead() {
		return model.isWorldRead();
	}

	/**
	 * Returns <code>true</code> if this repository folder is world write.
	 *
	 * @return <code>true</code> if this repository folder is world write; <code>false</code> otherwise
	 */
	@Override
	public boolean isWorldWrite() {
		return model.isWorldWrite();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the create date of this repository folder.
	 *
	 * @param createDate the create date of this repository folder
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the folder ID of this repository folder.
	 *
	 * @param folderId the folder ID of this repository folder
	 */
	@Override
	public void setFolderId(long folderId) {
		model.setFolderId(folderId);
	}

	/**
	 * Sets the modified date of this repository folder.
	 *
	 * @param modifiedDate the modified date of this repository folder
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this repository folder.
	 *
	 * @param name the name of this repository folder
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this repository folder.
	 *
	 * @param primaryKey the primary key of this repository folder
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the repository ID of this repository folder.
	 *
	 * @param repositoryId the repository ID of this repository folder
	 */
	@Override
	public void setRepositoryId(long repositoryId) {
		model.setRepositoryId(repositoryId);
	}

	/**
	 * Sets whether this repository folder is world read.
	 *
	 * @param worldRead the world read of this repository folder
	 */
	@Override
	public void setWorldRead(boolean worldRead) {
		model.setWorldRead(worldRead);
	}

	/**
	 * Sets whether this repository folder is world write.
	 *
	 * @param worldWrite the world write of this repository folder
	 */
	@Override
	public void setWorldWrite(boolean worldWrite) {
		model.setWorldWrite(worldWrite);
	}

	@Override
	protected RepositoryFolderWrapper wrap(RepositoryFolder repositoryFolder) {
		return new RepositoryFolderWrapper(repositoryFolder);
	}

}