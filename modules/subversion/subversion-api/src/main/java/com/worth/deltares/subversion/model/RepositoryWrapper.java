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
 * This class is a wrapper for {@link Repository}.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see Repository
 * @generated
 */
@ProviderType
public class RepositoryWrapper implements Repository, ModelWrapper<Repository> {
	public RepositoryWrapper(Repository repository) {
		_repository = repository;
	}

	@Override
	public Class<?> getModelClass() {
		return Repository.class;
	}

	@Override
	public String getModelClassName() {
		return Repository.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("repositoryId", getRepositoryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("repositoryName", getRepositoryName());
		attributes.put("createdDate", getCreatedDate());
		attributes.put("modifiedDate", getModifiedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String repositoryName = (String)attributes.get("repositoryName");

		if (repositoryName != null) {
			setRepositoryName(repositoryName);
		}

		Date createdDate = (Date)attributes.get("createdDate");

		if (createdDate != null) {
			setCreatedDate(createdDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new RepositoryWrapper((Repository)_repository.clone());
	}

	@Override
	public int compareTo(Repository repository) {
		return _repository.compareTo(repository);
	}

	/**
	* Returns the fully qualified class name of this repository.
	*
	* @return the fully qualified class name of this repository
	*/
	@Override
	public java.lang.String getClassName() {
		return _repository.getClassName();
	}

	/**
	* Returns the class name ID of this repository.
	*
	* @return the class name ID of this repository
	*/
	@Override
	public long getClassNameId() {
		return _repository.getClassNameId();
	}

	/**
	* Returns the class pk of this repository.
	*
	* @return the class pk of this repository
	*/
	@Override
	public long getClassPK() {
		return _repository.getClassPK();
	}

	/**
	* Returns the company ID of this repository.
	*
	* @return the company ID of this repository
	*/
	@Override
	public long getCompanyId() {
		return _repository.getCompanyId();
	}

	/**
	* Returns the created date of this repository.
	*
	* @return the created date of this repository
	*/
	@Override
	public Date getCreatedDate() {
		return _repository.getCreatedDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _repository.getExpandoBridge();
	}

	/**
	* Returns the group ID of this repository.
	*
	* @return the group ID of this repository
	*/
	@Override
	public long getGroupId() {
		return _repository.getGroupId();
	}

	/**
	* Returns the modified date of this repository.
	*
	* @return the modified date of this repository
	*/
	@Override
	public Date getModifiedDate() {
		return _repository.getModifiedDate();
	}

	/**
	* Returns the primary key of this repository.
	*
	* @return the primary key of this repository
	*/
	@Override
	public long getPrimaryKey() {
		return _repository.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _repository.getPrimaryKeyObj();
	}

	/**
	* Returns the repository ID of this repository.
	*
	* @return the repository ID of this repository
	*/
	@Override
	public long getRepositoryId() {
		return _repository.getRepositoryId();
	}

	/**
	* Returns the repository name of this repository.
	*
	* @return the repository name of this repository
	*/
	@Override
	public java.lang.String getRepositoryName() {
		return _repository.getRepositoryName();
	}

	@Override
	public java.lang.String getRepositoryRoot() {
		return _repository.getRepositoryRoot();
	}

	@Override
	public int hashCode() {
		return _repository.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _repository.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _repository.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _repository.isNew();
	}

	@Override
	public void persist() {
		_repository.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_repository.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_repository.setClassName(className);
	}

	/**
	* Sets the class name ID of this repository.
	*
	* @param classNameId the class name ID of this repository
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_repository.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this repository.
	*
	* @param classPK the class pk of this repository
	*/
	@Override
	public void setClassPK(long classPK) {
		_repository.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this repository.
	*
	* @param companyId the company ID of this repository
	*/
	@Override
	public void setCompanyId(long companyId) {
		_repository.setCompanyId(companyId);
	}

	/**
	* Sets the created date of this repository.
	*
	* @param createdDate the created date of this repository
	*/
	@Override
	public void setCreatedDate(Date createdDate) {
		_repository.setCreatedDate(createdDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_repository.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_repository.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_repository.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this repository.
	*
	* @param groupId the group ID of this repository
	*/
	@Override
	public void setGroupId(long groupId) {
		_repository.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this repository.
	*
	* @param modifiedDate the modified date of this repository
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_repository.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_repository.setNew(n);
	}

	/**
	* Sets the primary key of this repository.
	*
	* @param primaryKey the primary key of this repository
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_repository.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_repository.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the repository ID of this repository.
	*
	* @param repositoryId the repository ID of this repository
	*/
	@Override
	public void setRepositoryId(long repositoryId) {
		_repository.setRepositoryId(repositoryId);
	}

	/**
	* Sets the repository name of this repository.
	*
	* @param repositoryName the repository name of this repository
	*/
	@Override
	public void setRepositoryName(java.lang.String repositoryName) {
		_repository.setRepositoryName(repositoryName);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<Repository> toCacheModel() {
		return _repository.toCacheModel();
	}

	@Override
	public Repository toEscapedModel() {
		return new RepositoryWrapper(_repository.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _repository.toString();
	}

	@Override
	public Repository toUnescapedModel() {
		return new RepositoryWrapper(_repository.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _repository.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RepositoryWrapper)) {
			return false;
		}

		RepositoryWrapper repositoryWrapper = (RepositoryWrapper)obj;

		if (Objects.equals(_repository, repositoryWrapper._repository)) {
			return true;
		}

		return false;
	}

	@Override
	public Repository getWrappedModel() {
		return _repository;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _repository.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _repository.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_repository.resetOriginalValues();
	}

	private final Repository _repository;
}