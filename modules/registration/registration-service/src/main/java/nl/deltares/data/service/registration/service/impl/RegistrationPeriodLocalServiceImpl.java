/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service.impl;

import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.dao.orm.*;
import nl.deltares.data.service.registration.model.Registration;
import nl.deltares.data.service.registration.model.RegistrationPeriod;
import nl.deltares.data.service.registration.service.base.RegistrationPeriodLocalServiceBaseImpl;

import nl.deltares.data.service.registration.service.persistence.RegistrationPeriodUtil;
import org.osgi.service.component.annotations.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=nl.deltares.data.service.registration.model.RegistrationPeriod",
	service = AopService.class
)
public class RegistrationPeriodLocalServiceImpl
	extends RegistrationPeriodLocalServiceBaseImpl {

	public List<RegistrationPeriod> getOverlappingPeriods(Date startTime, Date endTime){

		Criterion checkPeriod = RestrictionsFactoryUtil.and(PropertyFactoryUtil.forName("startTime").lt(endTime), PropertyFactoryUtil.forName("endTime").gt(startTime));
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(Registration.class, getClass().getClassLoader()).add(checkPeriod);
		return RegistrationPeriodUtil.findWithDynamicQuery(query);
	}

	public List<RegistrationPeriod> getWithinPeriod(Date startTime, Date endTime){

		Criterion checkStart = PropertyFactoryUtil.forName("startTime").ge(startTime);
		Criterion checkEnd = PropertyFactoryUtil.forName("endTime").le(endTime);
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(Registration.class,
				getClass().getClassLoader()).add(checkStart).add(checkEnd);
		return RegistrationPeriodUtil.findWithDynamicQuery(query);
	}
}