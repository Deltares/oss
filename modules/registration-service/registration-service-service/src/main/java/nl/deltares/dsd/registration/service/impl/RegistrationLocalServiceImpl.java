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
import com.liferay.portal.kernel.dao.orm.*;
import nl.deltares.dsd.registration.exception.NoSuchRegistrationException;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil;
import nl.deltares.dsd.registration.service.base.RegistrationLocalServiceBaseImpl;
import nl.deltares.dsd.registration.service.persistence.RegistrationUtil;

import java.util.ArrayList;
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
	public void addUserRegistration(long companyId, long groupId, long resouceId, long parentResourceId,
									long userId, Date startTime, Date endTime, String preferences) {

		//do not validate here. validation has already taken place

		Registration registration = RegistrationLocalServiceUtil.createRegistration(CounterLocalServiceUtil.increment(Registration.class.getName()));
		registration.setCompanyId(companyId);
		registration.setGroupId(groupId);
		registration.setResourcePrimaryKey(resouceId);
		registration.setParentResourcePrimaryKey(parentResourceId);
		registration.setUserId(userId);
		registration.setStartTime(startTime);
		registration.setEndTime(endTime);
		registration.setUserPreferences(preferences);

		addRegistration(registration);

	}

	/**
	 * Delete all registrations related to 'resourceId'. This inlcudes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 * @param groupId Site Identifier
	 * @param resourceId Article Identifier being removed.
	 */
	public void deleteAllRegistrationsAndChildRegistrations(long groupId, long resourceId){

		//Remove all registrations with a parentArticleId equal to resourceId
		RegistrationUtil.removeByChildArticleRegistrations(groupId, resourceId);

		//Remove all registrations for resourceId
		RegistrationUtil.removeByArticleRegistrations(groupId, resourceId);

	}

	/**
	 * Delete user registrations for 'resourceId'. This inlcudes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 * @param groupId Site Identifier
	 * @param resourceId Article Identifier being removed.
	 * @param userId User for which to remove registration
	 */
	public void deleteUserRegistrationAndChildRegistrations(long groupId, long resourceId, long userId){

		//Remove all registrations with a parentArticleId equal to resourceId
		RegistrationUtil.removeByUserChildArticleRegistrations(groupId, userId, resourceId);

		//Remove all registrations for resourceId
		RegistrationUtil.removeByUserArticleRegistrations(groupId, userId, resourceId);

	}

	/**
	 * Delete user registrations for 'resourceId' and a start date equal to 'stratDate'
	 * that matches 'resourceId'.
	 * @param groupId Site Identifier
	 * @param resourceId Article Identifier being removed.
	 * @param userId User for which to remove registration
	 * @param startDate Start date for which to remove registration
	 */
	public void deleteUserRegistration(long groupId, long resourceId, long userId, Date startDate) throws NoSuchRegistrationException {
		DynamicQuery query = getDynamicQuery(groupId, resourceId, userId, startDate);
		List<Registration> withDynamicQuery = RegistrationUtil.findWithDynamicQuery(query);
		for (Registration registration : withDynamicQuery) {
			//Remove all registrations for resourceId
			RegistrationUtil.remove(registration.getRegistrationId());
		}

	}

	private DynamicQuery getDynamicQuery(long groupId, long resourceId, long userId, Date startDate) {
		Criterion checkUserId = PropertyFactoryUtil.forName("userId").eq(userId);
		Criterion checkGroupId = PropertyFactoryUtil.forName("groupId").eq(groupId);
		Criterion checkResourceId = PropertyFactoryUtil.forName("resourcePrimaryKey").eq(resourceId);
		Criterion checkStart = PropertyFactoryUtil.forName("startTime").eq(startDate);
		return DynamicQueryFactoryUtil.forClass(Registration.class, getClass().getClassLoader()).add(checkGroupId).add(checkUserId).add(checkResourceId).add(checkStart);
	}

	public long[] getRegistrationsWithOverlappingPeriod(long groupId, long userId, Date startTime, Date endTime){


		Criterion checkUserId = PropertyFactoryUtil.forName("userId").eq(userId);
		Criterion checkGroupId = PropertyFactoryUtil.forName("groupId").eq(groupId);
		Criterion checkStart = RestrictionsFactoryUtil.and(PropertyFactoryUtil.forName("startTime").le(startTime), PropertyFactoryUtil.forName("endTime").ge(startTime));
		Criterion checkEnd = RestrictionsFactoryUtil.and(PropertyFactoryUtil.forName("startTime").le(endTime), PropertyFactoryUtil.forName("endTime").ge(endTime));
		Criterion checkPeriod = RestrictionsFactoryUtil.or(checkStart, checkEnd);
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(Registration.class, getClass().getClassLoader()).add(checkGroupId).add(checkUserId).add(checkPeriod);
		List<Registration> overlappingRegistrations = RegistrationUtil.findWithDynamicQuery(query);
		if (overlappingRegistrations.size() == 0) return new long[0];

		long[] resourceIds = new long[overlappingRegistrations.size()];
		int i = 0;
		for (Registration overlappingRegistration : overlappingRegistrations) {
			resourceIds[i++] = overlappingRegistration.getResourcePrimaryKey();
		}
		return resourceIds;
	}

	public int getRegistrationsCount(long groupId, long resourceId){
		return RegistrationUtil.countByArticleRegistrations(groupId, resourceId);
	}

	public int getRegistrationsCount(long groupId, long userId, long resourceId)  {
		return  RegistrationUtil.countByUserArticleRegistrations(groupId, userId, resourceId);
	}

	public List<Registration> getRegistrations(long groupId, long userId, long resourceId)  {
		return  RegistrationUtil.findByUserArticleRegistrations(groupId, userId, resourceId);
	}

	public int getRegistrationsCount(long groupId, long resourceId, Date startDate)  {

		Criterion checkGroupId = PropertyFactoryUtil.forName("groupId").eq(groupId);
		Criterion checkResourceId = PropertyFactoryUtil.forName("resourcePrimaryKey").eq(resourceId);
		Criterion checkStart = PropertyFactoryUtil.forName("startTime").eq(startDate);
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(Registration.class, getClass().getClassLoader()).add(checkGroupId).add(checkResourceId).add(checkStart);
		return (int) RegistrationUtil.countWithDynamicQuery(query);
	}

	public int getRegistrationsCount(long groupId, long userId, long resourceId, Date startDate)  {

		DynamicQuery query = getDynamicQuery(groupId, groupId, userId, startDate);
		return (int) RegistrationUtil.countWithDynamicQuery(query);
	}

	public List<Date> getRegistrationDates(long groupId, long userId, long resourceId){
		List<Date> registrationDates = new ArrayList<>();
		List<Registration> byUserArticleRegistrations = RegistrationUtil.findByUserArticleRegistrations(groupId, userId, resourceId);
		byUserArticleRegistrations.forEach(registration -> registrationDates.add(registration.getStartTime()));
		return registrationDates;
	}

	public List<Registration> getUserRegistrations(long groupId, long userId, Date start, Date end){
		Criterion checkGroupId = PropertyFactoryUtil.forName("groupId").eq(groupId);
		Criterion checkUserId = PropertyFactoryUtil.forName("userId").eq(userId);
		Criterion checkStart = PropertyFactoryUtil.forName("startTime").ge(start);
		Criterion checkEnd = PropertyFactoryUtil.forName("endTime").le(end);
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(Registration.class,
				getClass().getClassLoader()).add(checkGroupId).add(checkGroupId).add(checkUserId).add(checkStart).add(checkEnd);
		return RegistrationUtil.findWithDynamicQuery(query);

	}

	public List<Registration> getRegistrations(long groupId, Date start, Date end){
		Criterion checkGroupId = PropertyFactoryUtil.forName("groupId").eq(groupId);
		Criterion checkStart = PropertyFactoryUtil.forName("startTime").ge(start);
		Criterion checkEnd = PropertyFactoryUtil.forName("endTime").le(end);
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(Registration.class,
				getClass().getClassLoader()).add(checkGroupId).add(checkGroupId).add(checkStart).add(checkEnd);
		return RegistrationUtil.findWithDynamicQuery(query);

	}
}