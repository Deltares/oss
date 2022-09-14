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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Registration}.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see Registration
 * @generated
 */
public class RegistrationWrapper
	extends BaseModelWrapper<Registration>
	implements ModelWrapper<Registration>, Registration {

	public RegistrationWrapper(Registration registration) {
		super(registration);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("registrationId", getRegistrationId());
		attributes.put("groupId", getGroupId());
		attributes.put("eventResourcePrimaryKey", getEventResourcePrimaryKey());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("resourcePrimaryKey", getResourcePrimaryKey());
		attributes.put("userPreferences", getUserPreferences());
		attributes.put("startTime", getStartTime());
		attributes.put("endTime", getEndTime());
		attributes.put(
			"parentResourcePrimaryKey", getParentResourcePrimaryKey());
		attributes.put("registeredByUserId", getRegisteredByUserId());

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

		Long eventResourcePrimaryKey = (Long)attributes.get(
			"eventResourcePrimaryKey");

		if (eventResourcePrimaryKey != null) {
			setEventResourcePrimaryKey(eventResourcePrimaryKey);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long resourcePrimaryKey = (Long)attributes.get("resourcePrimaryKey");

		if (resourcePrimaryKey != null) {
			setResourcePrimaryKey(resourcePrimaryKey);
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

		Long parentResourcePrimaryKey = (Long)attributes.get(
			"parentResourcePrimaryKey");

		if (parentResourcePrimaryKey != null) {
			setParentResourcePrimaryKey(parentResourcePrimaryKey);
		}

		Long registeredByUserId = (Long)attributes.get("registeredByUserId");

		if (registeredByUserId != null) {
			setRegisteredByUserId(registeredByUserId);
		}
	}

	@Override
	public Registration cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this registration.
	 *
	 * @return the company ID of this registration
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the end time of this registration.
	 *
	 * @return the end time of this registration
	 */
	@Override
	public Date getEndTime() {
		return model.getEndTime();
	}

	/**
	 * Returns the event resource primary key of this registration.
	 *
	 * @return the event resource primary key of this registration
	 */
	@Override
	public long getEventResourcePrimaryKey() {
		return model.getEventResourcePrimaryKey();
	}

	/**
	 * Returns the group ID of this registration.
	 *
	 * @return the group ID of this registration
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the parent resource primary key of this registration.
	 *
	 * @return the parent resource primary key of this registration
	 */
	@Override
	public long getParentResourcePrimaryKey() {
		return model.getParentResourcePrimaryKey();
	}

	/**
	 * Returns the primary key of this registration.
	 *
	 * @return the primary key of this registration
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the registered by user ID of this registration.
	 *
	 * @return the registered by user ID of this registration
	 */
	@Override
	public long getRegisteredByUserId() {
		return model.getRegisteredByUserId();
	}

	/**
	 * Returns the registered by user uuid of this registration.
	 *
	 * @return the registered by user uuid of this registration
	 */
	@Override
	public String getRegisteredByUserUuid() {
		return model.getRegisteredByUserUuid();
	}

	/**
	 * Returns the registration ID of this registration.
	 *
	 * @return the registration ID of this registration
	 */
	@Override
	public long getRegistrationId() {
		return model.getRegistrationId();
	}

	/**
	 * Returns the resource primary key of this registration.
	 *
	 * @return the resource primary key of this registration
	 */
	@Override
	public long getResourcePrimaryKey() {
		return model.getResourcePrimaryKey();
	}

	/**
	 * Returns the start time of this registration.
	 *
	 * @return the start time of this registration
	 */
	@Override
	public Date getStartTime() {
		return model.getStartTime();
	}

	/**
	 * Returns the user ID of this registration.
	 *
	 * @return the user ID of this registration
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user preferences of this registration.
	 *
	 * @return the user preferences of this registration
	 */
	@Override
	public String getUserPreferences() {
		return model.getUserPreferences();
	}

	/**
	 * Returns the user uuid of this registration.
	 *
	 * @return the user uuid of this registration
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this registration.
	 *
	 * @param companyId the company ID of this registration
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the end time of this registration.
	 *
	 * @param endTime the end time of this registration
	 */
	@Override
	public void setEndTime(Date endTime) {
		model.setEndTime(endTime);
	}

	/**
	 * Sets the event resource primary key of this registration.
	 *
	 * @param eventResourcePrimaryKey the event resource primary key of this registration
	 */
	@Override
	public void setEventResourcePrimaryKey(long eventResourcePrimaryKey) {
		model.setEventResourcePrimaryKey(eventResourcePrimaryKey);
	}

	/**
	 * Sets the group ID of this registration.
	 *
	 * @param groupId the group ID of this registration
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the parent resource primary key of this registration.
	 *
	 * @param parentResourcePrimaryKey the parent resource primary key of this registration
	 */
	@Override
	public void setParentResourcePrimaryKey(long parentResourcePrimaryKey) {
		model.setParentResourcePrimaryKey(parentResourcePrimaryKey);
	}

	/**
	 * Sets the primary key of this registration.
	 *
	 * @param primaryKey the primary key of this registration
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the registered by user ID of this registration.
	 *
	 * @param registeredByUserId the registered by user ID of this registration
	 */
	@Override
	public void setRegisteredByUserId(long registeredByUserId) {
		model.setRegisteredByUserId(registeredByUserId);
	}

	/**
	 * Sets the registered by user uuid of this registration.
	 *
	 * @param registeredByUserUuid the registered by user uuid of this registration
	 */
	@Override
	public void setRegisteredByUserUuid(String registeredByUserUuid) {
		model.setRegisteredByUserUuid(registeredByUserUuid);
	}

	/**
	 * Sets the registration ID of this registration.
	 *
	 * @param registrationId the registration ID of this registration
	 */
	@Override
	public void setRegistrationId(long registrationId) {
		model.setRegistrationId(registrationId);
	}

	/**
	 * Sets the resource primary key of this registration.
	 *
	 * @param resourcePrimaryKey the resource primary key of this registration
	 */
	@Override
	public void setResourcePrimaryKey(long resourcePrimaryKey) {
		model.setResourcePrimaryKey(resourcePrimaryKey);
	}

	/**
	 * Sets the start time of this registration.
	 *
	 * @param startTime the start time of this registration
	 */
	@Override
	public void setStartTime(Date startTime) {
		model.setStartTime(startTime);
	}

	/**
	 * Sets the user ID of this registration.
	 *
	 * @param userId the user ID of this registration
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user preferences of this registration.
	 *
	 * @param userPreferences the user preferences of this registration
	 */
	@Override
	public void setUserPreferences(String userPreferences) {
		model.setUserPreferences(userPreferences);
	}

	/**
	 * Sets the user uuid of this registration.
	 *
	 * @param userUuid the user uuid of this registration
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected RegistrationWrapper wrap(Registration registration) {
		return new RegistrationWrapper(registration);
	}

}