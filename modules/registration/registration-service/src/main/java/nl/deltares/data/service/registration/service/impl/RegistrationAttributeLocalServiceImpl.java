/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service.impl;

import com.liferay.portal.aop.AopService;

import nl.deltares.data.service.registration.model.RegistrationAttribute;
import nl.deltares.data.service.registration.service.base.RegistrationAttributeLocalServiceBaseImpl;

import nl.deltares.data.service.registration.service.persistence.RegistrationAttributeUtil;
import org.osgi.service.component.annotations.Component;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=nl.deltares.data.service.registration.model.RegistrationAttribute",
	service = AopService.class
)
public class RegistrationAttributeLocalServiceImpl
	extends RegistrationAttributeLocalServiceBaseImpl {

	public List<RegistrationAttribute> findByRegistration(long registrationId) {
		return RegistrationAttributeUtil.findByRegistration(registrationId);
	}

	public void removeByRegistration(long registrationId) {
		RegistrationAttributeUtil.removeByRegistration(registrationId);
	}
}