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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link RepositoryLog}.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLog
 * @generated
 */
@ProviderType
public class RepositoryLogWrapper
	implements RepositoryLog, ModelWrapper<RepositoryLog> {

	public RepositoryLogWrapper(RepositoryLog repositoryLog) {
		_repositoryLog = repositoryLog;
	}

	@Override
	public Class<?> getModelClass() {
		return RepositoryLog.class;
	}

	@Override
	public String getModelClassName() {
		return RepositoryLog.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("logId", getLogId());
		attributes.put("ipAddress", getIpAddress());
		attributes.put("screenName", getScreenName());
		attributes.put("action", getAction());
		attributes.put("createDate", getCreateDate());
		attributes.put("repository", getRepository());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long logId = (Long)attributes.get("logId");

		if (logId != null) {
			setLogId(logId);
		}

		String ipAddress = (String)attributes.get("ipAddress");

		if (ipAddress != null) {
			setIpAddress(ipAddress);
		}

		String screenName = (String)attributes.get("screenName");

		if (screenName != null) {
			setScreenName(screenName);
		}

		String action = (String)attributes.get("action");

		if (action != null) {
			setAction(action);
		}

		Long createDate = (Long)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String repository = (String)attributes.get("repository");

		if (repository != null) {
			setRepository(repository);
		}
	}

	@Override
	public Object clone() {
		return new RepositoryLogWrapper((RepositoryLog)_repositoryLog.clone());
	}

	@Override
	public int compareTo(
		nl.worth.deltares.oss.subversion.model.RepositoryLog repositoryLog) {

		return _repositoryLog.compareTo(repositoryLog);
	}

	/**
	 * Returns the action of this repository log.
	 *
	 * @return the action of this repository log
	 */
	@Override
	public String getAction() {
		return _repositoryLog.getAction();
	}

	/**
	 * Returns the create date of this repository log.
	 *
	 * @return the create date of this repository log
	 */
	@Override
	public long getCreateDate() {
		return _repositoryLog.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _repositoryLog.getExpandoBridge();
	}

	/**
	 * Returns the ip address of this repository log.
	 *
	 * @return the ip address of this repository log
	 */
	@Override
	public String getIpAddress() {
		return _repositoryLog.getIpAddress();
	}

	/**
	 * Returns the log ID of this repository log.
	 *
	 * @return the log ID of this repository log
	 */
	@Override
	public long getLogId() {
		return _repositoryLog.getLogId();
	}

	/**
	 * Returns the primary key of this repository log.
	 *
	 * @return the primary key of this repository log
	 */
	@Override
	public long getPrimaryKey() {
		return _repositoryLog.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _repositoryLog.getPrimaryKeyObj();
	}

	/**
	 * Returns the repository of this repository log.
	 *
	 * @return the repository of this repository log
	 */
	@Override
	public String getRepository() {
		return _repositoryLog.getRepository();
	}

	/**
	 * Returns the screen name of this repository log.
	 *
	 * @return the screen name of this repository log
	 */
	@Override
	public String getScreenName() {
		return _repositoryLog.getScreenName();
	}

	@Override
	public int hashCode() {
		return _repositoryLog.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _repositoryLog.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _repositoryLog.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _repositoryLog.isNew();
	}

	@Override
	public void persist() {
		_repositoryLog.persist();
	}

	/**
	 * Sets the action of this repository log.
	 *
	 * @param action the action of this repository log
	 */
	@Override
	public void setAction(String action) {
		_repositoryLog.setAction(action);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_repositoryLog.setCachedModel(cachedModel);
	}

	/**
	 * Sets the create date of this repository log.
	 *
	 * @param createDate the create date of this repository log
	 */
	@Override
	public void setCreateDate(long createDate) {
		_repositoryLog.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_repositoryLog.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_repositoryLog.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_repositoryLog.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the ip address of this repository log.
	 *
	 * @param ipAddress the ip address of this repository log
	 */
	@Override
	public void setIpAddress(String ipAddress) {
		_repositoryLog.setIpAddress(ipAddress);
	}

	/**
	 * Sets the log ID of this repository log.
	 *
	 * @param logId the log ID of this repository log
	 */
	@Override
	public void setLogId(long logId) {
		_repositoryLog.setLogId(logId);
	}

	@Override
	public void setNew(boolean n) {
		_repositoryLog.setNew(n);
	}

	/**
	 * Sets the primary key of this repository log.
	 *
	 * @param primaryKey the primary key of this repository log
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_repositoryLog.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_repositoryLog.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the repository of this repository log.
	 *
	 * @param repository the repository of this repository log
	 */
	@Override
	public void setRepository(String repository) {
		_repositoryLog.setRepository(repository);
	}

	/**
	 * Sets the screen name of this repository log.
	 *
	 * @param screenName the screen name of this repository log
	 */
	@Override
	public void setScreenName(String screenName) {
		_repositoryLog.setScreenName(screenName);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<nl.worth.deltares.oss.subversion.model.RepositoryLog> toCacheModel() {

		return _repositoryLog.toCacheModel();
	}

	@Override
	public nl.worth.deltares.oss.subversion.model.RepositoryLog
		toEscapedModel() {

		return new RepositoryLogWrapper(_repositoryLog.toEscapedModel());
	}

	@Override
	public String toString() {
		return _repositoryLog.toString();
	}

	@Override
	public nl.worth.deltares.oss.subversion.model.RepositoryLog
		toUnescapedModel() {

		return new RepositoryLogWrapper(_repositoryLog.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _repositoryLog.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RepositoryLogWrapper)) {
			return false;
		}

		RepositoryLogWrapper repositoryLogWrapper = (RepositoryLogWrapper)obj;

		if (Objects.equals(
				_repositoryLog, repositoryLogWrapper._repositoryLog)) {

			return true;
		}

		return false;
	}

	@Override
	public RepositoryLog getWrappedModel() {
		return _repositoryLog;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _repositoryLog.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _repositoryLog.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_repositoryLog.resetOriginalValues();
	}

	private final RepositoryLog _repositoryLog;

}