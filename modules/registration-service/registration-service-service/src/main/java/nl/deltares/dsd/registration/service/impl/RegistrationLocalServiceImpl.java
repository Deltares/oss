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

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.dsd.registration.exception.OverlappingPeriodException;
import nl.deltares.dsd.registration.exception.RegistrationClosedException;
import nl.deltares.dsd.registration.exception.RegistrationFullException;
import nl.deltares.dsd.registration.exception.ValidationException;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.model.impl.RegistrationJournalArtical;
import nl.deltares.dsd.registration.service.base.RegistrationLocalServiceBaseImpl;
import nl.deltares.dsd.registration.service.persistence.RegistrationUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
 * @author Brian Wing Shun Chan
 * @see RegistrationLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=nl.deltares.dsd.registration.model.Registration",
	service = AopService.class
)
public class RegistrationLocalServiceImpl
	extends RegistrationLocalServiceBaseImpl {


	private static final Log LOG = LogFactoryUtil.getLog(
			RegistrationLocalServiceImpl.class);
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>nl.deltares.dsd.registration.service.RegistrationLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil</code>.
	 */

	public void validateRegistration(long groupId, long articleId, long userId) throws PortalException {
		JournalArticle article = journalArticleLocalService.getLatestArticle(groupId, String.valueOf(articleId));

		RegistrationJournalArtical registration = RegistrationJournalArtical.getInstance(article);

		//check if article is open for registration
		validateOpenForRegistration(registration);

		//check if room limit is exceeded
		validateRegistrationCapacity(registration);

		//check if period overlaps
		validateRegistrationPeriod(registration, userId);

	}

	private void validateRegistrationPeriod(RegistrationJournalArtical registration, long userId) throws OverlappingPeriodException {

		Criterion checkUserId = PropertyFactoryUtil.forName("userId").eq(userId);
		Criterion checkGroupId = PropertyFactoryUtil.forName("groupId").eq(registration.getGroupId());
		Criterion checkStart = PropertyFactoryUtil.forName("startTime").between(registration.getStartTime(), registration.getEndTime());
		Criterion checkEnd = PropertyFactoryUtil.forName("endTime").between(registration.getStartTime(), registration.getEndTime());
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(Registration.class, getClass().getClassLoader()).add(checkGroupId).add(checkUserId).add(checkStart).add(checkEnd);
		List<Registration> overlappingRegistrations = RegistrationUtil.findWithDynamicQuery(query);
		if (overlappingRegistrations.size() == 0) return;

		StringBuilder sb = new StringBuilder();
		for (Registration overlappingRegistration : overlappingRegistrations) {
			try {
				JournalArticle latestArticle = journalArticleLocalService.getLatestArticle(overlappingRegistration.getGroupId(), String.valueOf(overlappingRegistration.getArticleId()));
				sb.append(latestArticle.getUrlTitle());
				sb.append(',');
			} catch (PortalException e) {
				LOG.warn( String.format("Error getting latest article for '%s': %s", overlappingRegistration.getArticleId(), e.getMessage()) );
				sb.append(overlappingRegistration.getArticleId());
				sb.append(',');
			}
		}
		throw new OverlappingPeriodException(String.format("Period of registration '%s' overlaps with existing user registrations: [%s]", registration.getArticleId(), sb.toString().trim()));
	}

	private void validateRegistrationCapacity(RegistrationJournalArtical articleContent) throws ValidationException {

		int capacity = articleContent.getCapacity();
		int registrationCount = RegistrationUtil.countByArticleRegistrations(articleContent.getGroupId(), articleContent.getArticleId());
		if (registrationCount < capacity) return;

		throw new RegistrationFullException(String.format("Capacity '%d' of registration '%s' has been reached!", registrationCount, articleContent.getArticleId()));

	}

	private void validateOpenForRegistration(RegistrationJournalArtical articleContent) throws ValidationException {
		if (!articleContent.isOpen()){
			throw new RegistrationClosedException(String.format("Registration '%s' is closed !", articleContent.getArticleId()));
		}
	}

	private JournalArticleLocalService journalArticleLocalService;

	@Reference(unbind = "-")
	private void setJournalArticalLocalService(JournalArticleLocalService journalArticleLocalService) {

		this.journalArticleLocalService = journalArticleLocalService;
	}
}