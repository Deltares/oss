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

package com.worth.deltares.subversion.service.impl;


import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.worth.deltares.subversion.service.RepositoryLogLocalServiceUtil;
import com.worth.deltares.subversion.service.base.RepositoryLogServiceBaseImpl;


/**
 * The implementation of the repository log remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.worth.deltares.subversion.service.RepositoryLogService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLogServiceBaseImpl
 * @see com.worth.deltares.subversion.service.RepositoryLogServiceUtil
 */
public class RepositoryLogServiceImpl extends RepositoryLogServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.worth.deltares.subversion.service.RepositoryLogServiceUtil} to access the repository log remote service.
	 */

	@JSONWebService
	@AccessControlled(guestAccessEnabled=true)
	public JSONArray getLastLogs(Integer number) {
  
	  return RepositoryLogLocalServiceUtil.getLastLogs(number);
	}
}
