/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RegistrationResource}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationResource
 * @generated
 */
public class RegistrationResourceWrapper
	extends BaseModelWrapper<RegistrationResource>
	implements ModelWrapper<RegistrationResource>, RegistrationResource {

	public RegistrationResourceWrapper(
		RegistrationResource registrationResource) {

		super(registrationResource);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("registrationResourceId", getRegistrationResourceId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("eventResourceId", getEventResourceId());
		attributes.put("parentResourceId", getParentResourceId());
		attributes.put("resourceName", getResourceName());
		attributes.put("eventResourceName", getEventResourceName());
		attributes.put("eventArticleId", getEventArticleId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long registrationResourceId = (Long)attributes.get(
			"registrationResourceId");

		if (registrationResourceId != null) {
			setRegistrationResourceId(registrationResourceId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long eventResourceId = (Long)attributes.get("eventResourceId");

		if (eventResourceId != null) {
			setEventResourceId(eventResourceId);
		}

		Long parentResourceId = (Long)attributes.get("parentResourceId");

		if (parentResourceId != null) {
			setParentResourceId(parentResourceId);
		}

		String resourceName = (String)attributes.get("resourceName");

		if (resourceName != null) {
			setResourceName(resourceName);
		}

		String eventResourceName = (String)attributes.get("eventResourceName");

		if (eventResourceName != null) {
			setEventResourceName(eventResourceName);
		}

		Long eventArticleId = (Long)attributes.get("eventArticleId");

		if (eventArticleId != null) {
			setEventArticleId(eventArticleId);
		}
	}

	@Override
	public RegistrationResource cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this registration resource.
	 *
	 * @return the company ID of this registration resource
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the event article ID of this registration resource.
	 *
	 * @return the event article ID of this registration resource
	 */
	@Override
	public long getEventArticleId() {
		return model.getEventArticleId();
	}

	/**
	 * Returns the event resource ID of this registration resource.
	 *
	 * @return the event resource ID of this registration resource
	 */
	@Override
	public long getEventResourceId() {
		return model.getEventResourceId();
	}

	/**
	 * Returns the event resource name of this registration resource.
	 *
	 * @return the event resource name of this registration resource
	 */
	@Override
	public String getEventResourceName() {
		return model.getEventResourceName();
	}

	/**
	 * Returns the group ID of this registration resource.
	 *
	 * @return the group ID of this registration resource
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the parent resource ID of this registration resource.
	 *
	 * @return the parent resource ID of this registration resource
	 */
	@Override
	public long getParentResourceId() {
		return model.getParentResourceId();
	}

	/**
	 * Returns the primary key of this registration resource.
	 *
	 * @return the primary key of this registration resource
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the registration resource ID of this registration resource.
	 *
	 * @return the registration resource ID of this registration resource
	 */
	@Override
	public long getRegistrationResourceId() {
		return model.getRegistrationResourceId();
	}

	/**
	 * Returns the resource name of this registration resource.
	 *
	 * @return the resource name of this registration resource
	 */
	@Override
	public String getResourceName() {
		return model.getResourceName();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this registration resource.
	 *
	 * @param companyId the company ID of this registration resource
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the event article ID of this registration resource.
	 *
	 * @param eventArticleId the event article ID of this registration resource
	 */
	@Override
	public void setEventArticleId(long eventArticleId) {
		model.setEventArticleId(eventArticleId);
	}

	/**
	 * Sets the event resource ID of this registration resource.
	 *
	 * @param eventResourceId the event resource ID of this registration resource
	 */
	@Override
	public void setEventResourceId(long eventResourceId) {
		model.setEventResourceId(eventResourceId);
	}

	/**
	 * Sets the event resource name of this registration resource.
	 *
	 * @param eventResourceName the event resource name of this registration resource
	 */
	@Override
	public void setEventResourceName(String eventResourceName) {
		model.setEventResourceName(eventResourceName);
	}

	/**
	 * Sets the group ID of this registration resource.
	 *
	 * @param groupId the group ID of this registration resource
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the parent resource ID of this registration resource.
	 *
	 * @param parentResourceId the parent resource ID of this registration resource
	 */
	@Override
	public void setParentResourceId(long parentResourceId) {
		model.setParentResourceId(parentResourceId);
	}

	/**
	 * Sets the primary key of this registration resource.
	 *
	 * @param primaryKey the primary key of this registration resource
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the registration resource ID of this registration resource.
	 *
	 * @param registrationResourceId the registration resource ID of this registration resource
	 */
	@Override
	public void setRegistrationResourceId(long registrationResourceId) {
		model.setRegistrationResourceId(registrationResourceId);
	}

	/**
	 * Sets the resource name of this registration resource.
	 *
	 * @param resourceName the resource name of this registration resource
	 */
	@Override
	public void setResourceName(String resourceName) {
		model.setResourceName(resourceName);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected RegistrationResourceWrapper wrap(
		RegistrationResource registrationResource) {

		return new RegistrationResourceWrapper(registrationResource);
	}

}