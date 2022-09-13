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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RepositoryLog}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryLog
 * @generated
 */
public class RepositoryLogWrapper
	extends BaseModelWrapper<RepositoryLog>
	implements ModelWrapper<RepositoryLog>, RepositoryLog {

	public RepositoryLogWrapper(RepositoryLog repositoryLog) {
		super(repositoryLog);
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
	public RepositoryLog cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the action of this repository log.
	 *
	 * @return the action of this repository log
	 */
	@Override
	public String getAction() {
		return model.getAction();
	}

	/**
	 * Returns the create date of this repository log.
	 *
	 * @return the create date of this repository log
	 */
	@Override
	public long getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ip address of this repository log.
	 *
	 * @return the ip address of this repository log
	 */
	@Override
	public String getIpAddress() {
		return model.getIpAddress();
	}

	/**
	 * Returns the log ID of this repository log.
	 *
	 * @return the log ID of this repository log
	 */
	@Override
	public long getLogId() {
		return model.getLogId();
	}

	/**
	 * Returns the primary key of this repository log.
	 *
	 * @return the primary key of this repository log
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the repository of this repository log.
	 *
	 * @return the repository of this repository log
	 */
	@Override
	public String getRepository() {
		return model.getRepository();
	}

	/**
	 * Returns the screen name of this repository log.
	 *
	 * @return the screen name of this repository log
	 */
	@Override
	public String getScreenName() {
		return model.getScreenName();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the action of this repository log.
	 *
	 * @param action the action of this repository log
	 */
	@Override
	public void setAction(String action) {
		model.setAction(action);
	}

	/**
	 * Sets the create date of this repository log.
	 *
	 * @param createDate the create date of this repository log
	 */
	@Override
	public void setCreateDate(long createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ip address of this repository log.
	 *
	 * @param ipAddress the ip address of this repository log
	 */
	@Override
	public void setIpAddress(String ipAddress) {
		model.setIpAddress(ipAddress);
	}

	/**
	 * Sets the log ID of this repository log.
	 *
	 * @param logId the log ID of this repository log
	 */
	@Override
	public void setLogId(long logId) {
		model.setLogId(logId);
	}

	/**
	 * Sets the primary key of this repository log.
	 *
	 * @param primaryKey the primary key of this repository log
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the repository of this repository log.
	 *
	 * @param repository the repository of this repository log
	 */
	@Override
	public void setRepository(String repository) {
		model.setRepository(repository);
	}

	/**
	 * Sets the screen name of this repository log.
	 *
	 * @param screenName the screen name of this repository log
	 */
	@Override
	public void setScreenName(String screenName) {
		model.setScreenName(screenName);
	}

	@Override
	protected RepositoryLogWrapper wrap(RepositoryLog repositoryLog) {
		return new RepositoryLogWrapper(repositoryLog);
	}

}