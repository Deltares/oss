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
 * This class is a wrapper for {@link Registration}.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see Registration
 * @generated
 */
@ProviderType
public class RegistrationWrapper
	implements Registration, ModelWrapper<Registration> {

	public RegistrationWrapper(Registration registration) {
		_registration = registration;
	}

	@Override
	public Class<?> getModelClass() {
		return Registration.class;
	}

	@Override
	public String getModelClassName() {
		return Registration.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("registrationId", getRegistrationId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("articleId", getArticleId());
		attributes.put("userPreferences", getUserPreferences());
		attributes.put("startTime", getStartTime());
		attributes.put("endTime", getEndTime());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long registrationId = (Long)attributes.get("registrationId");

		if (registrationId != null) {
			setRegistrationId(registrationId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long articleId = (Long)attributes.get("articleId");

		if (articleId != null) {
			setArticleId(articleId);
		}

		String userPreferences = (String)attributes.get("userPreferences");

		if (userPreferences != null) {
			setUserPreferences(userPreferences);
		}

		Date startTime = (Date)attributes.get("startTime");

		if (startTime != null) {
			setStartTime(startTime);
		}

		Date endTime = (Date)attributes.get("endTime");

		if (endTime != null) {
			setEndTime(endTime);
		}
	}

	@Override
	public Object clone() {
		return new RegistrationWrapper((Registration)_registration.clone());
	}

	@Override
	public int compareTo(
		nl.deltares.dsd.registration.model.Registration registration) {

		return _registration.compareTo(registration);
	}

	/**
	 * Returns the article ID of this registration.
	 *
	 * @return the article ID of this registration
	 */
	@Override
	public long getArticleId() {
		return _registration.getArticleId();
	}

	/**
	 * Returns the company ID of this registration.
	 *
	 * @return the company ID of this registration
	 */
	@Override
	public long getCompanyId() {
		return _registration.getCompanyId();
	}

	/**
	 * Returns the end time of this registration.
	 *
	 * @return the end time of this registration
	 */
	@Override
	public Date getEndTime() {
		return _registration.getEndTime();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _registration.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this registration.
	 *
	 * @return the group ID of this registration
	 */
	@Override
	public long getGroupId() {
		return _registration.getGroupId();
	}

	/**
	 * Returns the primary key of this registration.
	 *
	 * @return the primary key of this registration
	 */
	@Override
	public long getPrimaryKey() {
		return _registration.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _registration.getPrimaryKeyObj();
	}

	/**
	 * Returns the registration ID of this registration.
	 *
	 * @return the registration ID of this registration
	 */
	@Override
	public long getRegistrationId() {
		return _registration.getRegistrationId();
	}

	/**
	 * Returns the start time of this registration.
	 *
	 * @return the start time of this registration
	 */
	@Override
	public Date getStartTime() {
		return _registration.getStartTime();
	}

	/**
	 * Returns the user ID of this registration.
	 *
	 * @return the user ID of this registration
	 */
	@Override
	public long getUserId() {
		return _registration.getUserId();
	}

	/**
	 * Returns the user preferences of this registration.
	 *
	 * @return the user preferences of this registration
	 */
	@Override
	public String getUserPreferences() {
		return _registration.getUserPreferences();
	}

	/**
	 * Returns the user uuid of this registration.
	 *
	 * @return the user uuid of this registration
	 */
	@Override
	public String getUserUuid() {
		return _registration.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _registration.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _registration.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _registration.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _registration.isNew();
	}

	@Override
	public void persist() {
		_registration.persist();
	}

	/**
	 * Sets the article ID of this registration.
	 *
	 * @param articleId the article ID of this registration
	 */
	@Override
	public void setArticleId(long articleId) {
		_registration.setArticleId(articleId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_registration.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this registration.
	 *
	 * @param companyId the company ID of this registration
	 */
	@Override
	public void setCompanyId(long companyId) {
		_registration.setCompanyId(companyId);
	}

	/**
	 * Sets the end time of this registration.
	 *
	 * @param endTime the end time of this registration
	 */
	@Override
	public void setEndTime(Date endTime) {
		_registration.setEndTime(endTime);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_registration.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_registration.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_registration.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this registration.
	 *
	 * @param groupId the group ID of this registration
	 */
	@Override
	public void setGroupId(long groupId) {
		_registration.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_registration.setNew(n);
	}

	/**
	 * Sets the primary key of this registration.
	 *
	 * @param primaryKey the primary key of this registration
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_registration.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_registration.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the registration ID of this registration.
	 *
	 * @param registrationId the registration ID of this registration
	 */
	@Override
	public void setRegistrationId(long registrationId) {
		_registration.setRegistrationId(registrationId);
	}

	/**
	 * Sets the start time of this registration.
	 *
	 * @param startTime the start time of this registration
	 */
	@Override
	public void setStartTime(Date startTime) {
		_registration.setStartTime(startTime);
	}

	/**
	 * Sets the user ID of this registration.
	 *
	 * @param userId the user ID of this registration
	 */
	@Override
	public void setUserId(long userId) {
		_registration.setUserId(userId);
	}

	/**
	 * Sets the user preferences of this registration.
	 *
	 * @param userPreferences the user preferences of this registration
	 */
	@Override
	public void setUserPreferences(String userPreferences) {
		_registration.setUserPreferences(userPreferences);
	}

	/**
	 * Sets the user uuid of this registration.
	 *
	 * @param userUuid the user uuid of this registration
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_registration.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<nl.deltares.dsd.registration.model.Registration> toCacheModel() {

		return _registration.toCacheModel();
	}

	@Override
	public nl.deltares.dsd.registration.model.Registration toEscapedModel() {
		return new RegistrationWrapper(_registration.toEscapedModel());
	}

	@Override
	public String toString() {
		return _registration.toString();
	}

	@Override
	public nl.deltares.dsd.registration.model.Registration toUnescapedModel() {
		return new RegistrationWrapper(_registration.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _registration.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RegistrationWrapper)) {
			return false;
		}

		RegistrationWrapper registrationWrapper = (RegistrationWrapper)obj;

		if (Objects.equals(_registration, registrationWrapper._registration)) {
			return true;
		}

		return false;
	}

	@Override
	public Registration getWrappedModel() {
		return _registration;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _registration.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _registration.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_registration.resetOriginalValues();
	}

	private final Registration _registration;

}