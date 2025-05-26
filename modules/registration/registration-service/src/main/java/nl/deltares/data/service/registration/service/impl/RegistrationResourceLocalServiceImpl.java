/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service.impl;

import com.liferay.portal.aop.AopService;

import nl.deltares.data.service.registration.model.RegistrationResource;
import nl.deltares.data.service.registration.service.base.RegistrationResourceLocalServiceBaseImpl;

import nl.deltares.data.service.registration.service.persistence.RegistrationResourceUtil;
import org.osgi.service.component.annotations.Component;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=nl.deltares.data.service.registration.model.RegistrationResource",
	service = AopService.class
)
public class RegistrationResourceLocalServiceImpl
	extends RegistrationResourceLocalServiceBaseImpl {

	public List<RegistrationResource> findByGroupAndResource(long groupId, long resourceId){
		return RegistrationResourceUtil.findByGroupAndResource(groupId, resourceId);
	}

	public List<RegistrationResource> findByGroupAndEventResource(long groupId, long eventResourceId){
		return RegistrationResourceUtil.findByGroupAndEventResource(groupId, eventResourceId);
	}

	public List<RegistrationResource> findByGroupAndEventArticle(long groupId, long eventResourceId){
		return RegistrationResourceUtil.findByGroupAndEventArticle(groupId, eventResourceId);
	}

	public List<RegistrationResource> findByGroupAndParentResource(long groupId, long parentResourceId){
		return RegistrationResourceUtil.findByGroupAndParentResource(groupId, parentResourceId);
	}
}