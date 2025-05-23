/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service.impl;

import com.liferay.portal.aop.AopService;

import nl.deltares.data.service.registration.service.base.RegistrationAttributeLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=nl.deltares.data.service.registration.model.RegistrationAttribute",
	service = AopService.class
)
public class RegistrationAttributeLocalServiceImpl
	extends RegistrationAttributeLocalServiceBaseImpl {
}