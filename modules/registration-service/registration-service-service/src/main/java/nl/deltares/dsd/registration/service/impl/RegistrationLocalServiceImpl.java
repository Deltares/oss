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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.service.base.RegistrationLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

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

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>nl.deltares.dsd.registration.service.RegistrationLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil</code>.
	 */
}