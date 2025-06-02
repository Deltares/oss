/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service.impl;

import com.liferay.portal.aop.AopService;

import nl.deltares.data.service.registration.model.Registration;
import nl.deltares.data.service.registration.service.base.RegistrationLocalServiceBaseImpl;

import nl.deltares.data.service.registration.service.persistence.RegistrationUtil;
import org.osgi.service.component.annotations.Component;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=nl.deltares.data.service.registration.model.Registration",
	service = AopService.class
)
public class RegistrationLocalServiceImpl
	extends RegistrationLocalServiceBaseImpl {

	public  int countByUserAndResource(long userId, long resourceId) {
		return RegistrationUtil.countByUserAndResource(userId, resourceId);
	}

	public int countByResource(long resourceId) {
		return RegistrationUtil.countByResource(resourceId);
	}

	public List<Registration> findByResource(long resourceId) {
		return RegistrationUtil.findByResource(resourceId);
	}

	public List<Registration> findByUserAndGroup(long userId, long groupId){
		return RegistrationUtil.findByUserAndGroup(userId, groupId);
	}

	public List<Registration> findByAuthorAndGroup(long authorId, long groupId){
		return RegistrationUtil.findByAuthorAndGroup(authorId, groupId);
	}

	public List<Registration> findByAuthorAndResource(long authorId, long resourceId){
		return RegistrationUtil.findByAuthorAndResource(authorId, resourceId);
	}

	public List<Registration> findByUserAndResource(long userId, long resourceId){
		return RegistrationUtil.findByUserAndResource(userId, resourceId);
	}

	public void removeByResource(long resourceId) {
		RegistrationUtil.removeByResource(resourceId);
	}

	public void removeByUserAndResource(long userId, long resourceId) {
		RegistrationUtil.removeByUserAndResource(userId, resourceId);
	}

}