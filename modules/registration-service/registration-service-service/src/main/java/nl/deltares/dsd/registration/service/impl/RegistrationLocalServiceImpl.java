/*
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

package nl.deltares.dsd.registration.service.impl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil;
import nl.deltares.dsd.registration.service.base.RegistrationLocalServiceBaseImpl;
import nl.deltares.dsd.registration.service.persistence.RegistrationUtil;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the registration local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.deltares.dsd.registration.service.RegistrationLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see RegistrationLocalServiceBaseImpl
 */
public class RegistrationLocalServiceImpl
	extends RegistrationLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>nl.deltares.dsd.registration.service.RegistrationLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil</code>.
	 */
	public void addUserRegistration(long companyId, long groupId, long articleId, long parentArticleId,
									long userId, Date startTime, Date endTime, String preferences) {

		//do not validate here. validation has already taken place

		Registration registration = RegistrationLocalServiceUtil.createRegistration(CounterLocalServiceUtil.increment(Registration.class.getName()));
		registration.setCompanyId(companyId);
		registration.setGroupId(groupId);
		registration.setArticleId(articleId);
		registration.setParentArticleId(parentArticleId);
		registration.setUserId(userId);
		registration.setStartTime(startTime);
		registration.setEndTime(endTime);
		registration.setUserPreferences(preferences);

		addRegistration(registration);

	}

	/**
	 * Delete all registrations related to 'articleId'. This inlcudes all registration with a parentArticleId
	 * that matches 'articleId'.
	 * @param groupId Site Identifier
	 * @param articleId Article Identifier being removed.
	 */
	public void deleteAllRegistrationsAndChildRegistrations(long groupId, long articleId){

		//Remove all registrations with a parentArticleId equal to articleId
		RegistrationUtil.removeByChildArticleRegistrations(groupId, articleId);

		//Remove all registrations for articleId
		RegistrationUtil.removeByArticleRegistrations(groupId, articleId);

	}

	/**
	 * Delete user registrations for 'articleId'. This inlcudes all registration with a parentArticleId
	 * that matches 'articleId'.
	 * @param groupId Site Identifier
	 * @param articleId Article Identifier being removed.
	 * @param userId User for which to remove registration
	 */
	public void deleteUserRegistrationAndChildRegistrations(long groupId, long articleId, long userId){

		//Remove all registrations with a parentArticleId equal to articleId
		RegistrationUtil.removeByUserChildArticleRegistrations(groupId, userId, articleId);

		//Remove all registrations for articleId
		RegistrationUtil.removeByUserArticleRegistrations(groupId, userId, articleId);

	}

	public long[] getRegistrationsWithOverlappingPeriod(long groupId, long userId, Date startTime, Date endTime){

		Criterion checkUserId = PropertyFactoryUtil.forName("userId").eq(userId);
		Criterion checkGroupId = PropertyFactoryUtil.forName("groupId").eq(groupId);
		Criterion checkStart = PropertyFactoryUtil.forName("startTime").between(startTime, endTime);
		Criterion checkEnd = PropertyFactoryUtil.forName("endTime").between(startTime, endTime);
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(Registration.class, getClass().getClassLoader()).add(checkGroupId).add(checkUserId).add(checkStart).add(checkEnd);
		List<Registration> overlappingRegistrations = RegistrationUtil.findWithDynamicQuery(query);
		if (overlappingRegistrations.size() == 0) return new long[0];

		long[] articleIds = new long[overlappingRegistrations.size()];
		int i = 0;
		for (Registration overlappingRegistration : overlappingRegistrations) {
			articleIds[i++] = overlappingRegistration.getArticleId();
		}
		return articleIds;
	}

	public int getRegistrationsCount(long groupId, long articleId){
		return RegistrationUtil.countByArticleRegistrations(groupId, articleId);
	}

	public int getRegistrationsCount(long groupId, long userId, long articleId)  {
		return  RegistrationUtil.countByUserArticleRegistrations(groupId, userId, articleId);
	}
}