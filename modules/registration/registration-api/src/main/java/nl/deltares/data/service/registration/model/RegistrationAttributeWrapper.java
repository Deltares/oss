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
 * This class is a wrapper for {@link RegistrationAttribute}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationAttribute
 * @generated
 */
public class RegistrationAttributeWrapper
	extends BaseModelWrapper<RegistrationAttribute>
	implements ModelWrapper<RegistrationAttribute>, RegistrationAttribute {

	public RegistrationAttributeWrapper(
		RegistrationAttribute registrationAttribute) {

		super(registrationAttribute);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("registrationAttributeId", getRegistrationAttributeId());
		attributes.put("registrationId", getRegistrationId());
		attributes.put("name", getName());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long registrationAttributeId = (Long)attributes.get(
			"registrationAttributeId");

		if (registrationAttributeId != null) {
			setRegistrationAttributeId(registrationAttributeId);
		}

		Long registrationId = (Long)attributes.get("registrationId");

		if (registrationId != null) {
			setRegistrationId(registrationId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public RegistrationAttribute cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the name of this registration attribute.
	 *
	 * @return the name of this registration attribute
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this registration attribute.
	 *
	 * @return the primary key of this registration attribute
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the registration attribute ID of this registration attribute.
	 *
	 * @return the registration attribute ID of this registration attribute
	 */
	@Override
	public long getRegistrationAttributeId() {
		return model.getRegistrationAttributeId();
	}

	/**
	 * Returns the registration ID of this registration attribute.
	 *
	 * @return the registration ID of this registration attribute
	 */
	@Override
	public long getRegistrationId() {
		return model.getRegistrationId();
	}

	/**
	 * Returns the value of this registration attribute.
	 *
	 * @return the value of this registration attribute
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the name of this registration attribute.
	 *
	 * @param name the name of this registration attribute
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this registration attribute.
	 *
	 * @param primaryKey the primary key of this registration attribute
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the registration attribute ID of this registration attribute.
	 *
	 * @param registrationAttributeId the registration attribute ID of this registration attribute
	 */
	@Override
	public void setRegistrationAttributeId(long registrationAttributeId) {
		model.setRegistrationAttributeId(registrationAttributeId);
	}

	/**
	 * Sets the registration ID of this registration attribute.
	 *
	 * @param registrationId the registration ID of this registration attribute
	 */
	@Override
	public void setRegistrationId(long registrationId) {
		model.setRegistrationId(registrationId);
	}

	/**
	 * Sets the value of this registration attribute.
	 *
	 * @param value the value of this registration attribute
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected RegistrationAttributeWrapper wrap(
		RegistrationAttribute registrationAttribute) {

		return new RegistrationAttributeWrapper(registrationAttribute);
	}

}