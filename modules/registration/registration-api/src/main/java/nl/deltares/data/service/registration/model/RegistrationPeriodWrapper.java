/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RegistrationPeriod}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationPeriod
 * @generated
 */
public class RegistrationPeriodWrapper
	extends BaseModelWrapper<RegistrationPeriod>
	implements ModelWrapper<RegistrationPeriod>, RegistrationPeriod {

	public RegistrationPeriodWrapper(RegistrationPeriod registrationPeriod) {
		super(registrationPeriod);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("registrationPeriodId", getRegistrationPeriodId());
		attributes.put("registrationResourceId", getRegistrationResourceId());
		attributes.put("startTime", getStartTime());
		attributes.put("endTime", getEndTime());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long registrationPeriodId = (Long)attributes.get(
			"registrationPeriodId");

		if (registrationPeriodId != null) {
			setRegistrationPeriodId(registrationPeriodId);
		}

		Long registrationResourceId = (Long)attributes.get(
			"registrationResourceId");

		if (registrationResourceId != null) {
			setRegistrationResourceId(registrationResourceId);
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
	public RegistrationPeriod cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the end time of this registration period.
	 *
	 * @return the end time of this registration period
	 */
	@Override
	public Date getEndTime() {
		return model.getEndTime();
	}

	/**
	 * Returns the primary key of this registration period.
	 *
	 * @return the primary key of this registration period
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the registration period ID of this registration period.
	 *
	 * @return the registration period ID of this registration period
	 */
	@Override
	public long getRegistrationPeriodId() {
		return model.getRegistrationPeriodId();
	}

	/**
	 * Returns the registration resource ID of this registration period.
	 *
	 * @return the registration resource ID of this registration period
	 */
	@Override
	public long getRegistrationResourceId() {
		return model.getRegistrationResourceId();
	}

	/**
	 * Returns the start time of this registration period.
	 *
	 * @return the start time of this registration period
	 */
	@Override
	public Date getStartTime() {
		return model.getStartTime();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the end time of this registration period.
	 *
	 * @param endTime the end time of this registration period
	 */
	@Override
	public void setEndTime(Date endTime) {
		model.setEndTime(endTime);
	}

	/**
	 * Sets the primary key of this registration period.
	 *
	 * @param primaryKey the primary key of this registration period
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the registration period ID of this registration period.
	 *
	 * @param registrationPeriodId the registration period ID of this registration period
	 */
	@Override
	public void setRegistrationPeriodId(long registrationPeriodId) {
		model.setRegistrationPeriodId(registrationPeriodId);
	}

	/**
	 * Sets the registration resource ID of this registration period.
	 *
	 * @param registrationResourceId the registration resource ID of this registration period
	 */
	@Override
	public void setRegistrationResourceId(long registrationResourceId) {
		model.setRegistrationResourceId(registrationResourceId);
	}

	/**
	 * Sets the start time of this registration period.
	 *
	 * @param startTime the start time of this registration period
	 */
	@Override
	public void setStartTime(Date startTime) {
		model.setStartTime(startTime);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected RegistrationPeriodWrapper wrap(
		RegistrationPeriod registrationPeriod) {

		return new RegistrationPeriodWrapper(registrationPeriod);
	}

}