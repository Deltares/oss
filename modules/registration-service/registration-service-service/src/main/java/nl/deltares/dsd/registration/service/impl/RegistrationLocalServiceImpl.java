/**
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

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.dsd.registration.exception.*;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.model.impl.AbstractRegistration;
import nl.deltares.dsd.registration.service.base.RegistrationLocalServiceBaseImpl;
import nl.deltares.dsd.registration.service.persistence.RegistrationUtil;

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

	private static final Log LOG = LogFactoryUtil.getLog(RegistrationLocalServiceImpl.class);

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>nl.deltares.dsd.registration.service.RegistrationLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil</code>.
	 */

	public void validateRegistration(long groupId, long articleId, long userId) throws PortalException {
		JournalArticle article = JournalArticleLocalServiceUtil.getLatestArticle(groupId, String.valueOf(articleId));

		AbstractRegistration registration = AbstractRegistration.getInstance(article);

		//check if article is open for registration
		validateOpenForRegistration(registration);

		//check if room limit is exceeded
		validateRegistrationCapacity(registration);

		//check if period overlaps
		validateRegistrationPeriod(registration, userId);

		//check if user is registered for required parent registrations
		validateParentChildRelation(registration, userId);

	}

	private void validateRegistrationPeriod(AbstractRegistration registration, long userId) throws RegistrationPeriodOverlapException {

		Criterion checkUserId = PropertyFactoryUtil.forName("userId").eq(userId);
		Criterion checkGroupId = PropertyFactoryUtil.forName("groupId").eq(registration.getGroupId());
		Criterion checkStart = PropertyFactoryUtil.forName("startTime").between(registration.getStartTime(), registration.getEndTime());
		Criterion checkEnd = PropertyFactoryUtil.forName("endTime").between(registration.getStartTime(), registration.getEndTime());
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(Registration.class, getClass().getClassLoader()).add(checkGroupId).add(checkUserId).add(checkStart).add(checkEnd);
		List<Registration> overlappingRegistrations = RegistrationUtil.findWithDynamicQuery(query);
		if (overlappingRegistrations.size() == 0) return;

		StringBuilder sb = new StringBuilder();
		for (Registration overlappingRegistration : overlappingRegistrations) {
			sb.append(getUrlTitle(overlappingRegistration.getGroupId(), overlappingRegistration.getArticleId()));
			sb.append(',');
		}
		throw new RegistrationPeriodOverlapException(String.format("Period of registration '%s' overlaps with existing user registrations: [%s]", registration.getArticleId(), sb.toString().trim()));
	}

	private void validateRegistrationCapacity(AbstractRegistration registration) throws PortalException {

		int capacity = registration.getCapacity();
		int registrationCount = RegistrationUtil.countByArticleRegistrations(registration.getGroupId(), registration.getArticleId());
		if (registrationCount < capacity) return;

		throw new RegistrationFullException(String.format("Capacity '%d' of registration '%s' has been reached!", registrationCount, registration.getArticleId()));

	}

	private void validateOpenForRegistration(AbstractRegistration registration) throws ValidationException {
		if (!registration.isOpen()){
			throw new RegistrationClosedException(String.format("Registration '%s' is closed !", registration.getArticleId()));
		}
	}

	private void validateParentChildRelation(AbstractRegistration registration, long userId) throws ValidationException {
		if (!registration.hasParentRegistration()) return;

		long parentRegistrationId = registration.getParentRegistrationId();
		List<Registration> parentRegistrations = RegistrationUtil.findByUserArticleRegistrations(registration.getGroupId(), userId, parentRegistrationId);
		if (parentRegistrations.size() > 0) return;

		String childName = getUrlTitle(registration.getGroupId(), registration.getArticleId());
		String parentName = getUrlTitle(registration.getGroupId(), parentRegistrationId);

		throw new RegistrationParentMissingException(String.format("Required parent registration '%s' is missing for '%s!", parentName, childName));

	}

	private String getUrlTitle(long groupId, long articleId) {
		try {
			JournalArticle article = JournalArticleLocalServiceUtil.getLatestArticle(groupId, String.valueOf(articleId));
			return article.getUrlTitle();
		} catch (PortalException e) {
			LOG.warn(String.format("Error getting latest article for '%s': %s", articleId, e.getMessage()));
		}
		return String.valueOf(articleId);
	}
}