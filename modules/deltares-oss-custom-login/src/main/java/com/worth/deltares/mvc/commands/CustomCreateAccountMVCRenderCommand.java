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

package com.worth.deltares.mvc.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author Cesar Isaac Hernandez Lavarreda @ Worth Systems
 */

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=com_liferay_login_web_portlet_FastLoginPortlet",
		"javax.portlet.name=com_liferay_login_web_portlet_LoginPortlet",
		"mvc.command.name=/login/create_account",
		"service.ranking:Integer=1000"
	},
	service = MVCRenderCommand.class
)
public class CustomCreateAccountMVCRenderCommand implements MVCRenderCommand {

	private static final Log _log = LogFactoryUtil.getLog(
			CustomCreateAccountMVCRenderCommand.class);
	
	// Inject a reference to the component CreateAccountMVCRenderCommand
	@Reference(target = 
		      "(component.name=com.liferay.login.web.internal.portlet.action.CreateAccountMVCRenderCommand)")
		  protected MVCRenderCommand mvcRenderCommand;
	
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		
		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();
		List<Integer> groupTypes = new ArrayList<>();
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
		long guestGroupId = 0;
		
		// Fetch Guest site groupId.
		try {
			long companyId = PortalUtil.getDefaultCompanyId();
			guestGroupId = GroupLocalServiceUtil.getGroup(companyId, GroupConstants.GUEST).getGroupId();

		} catch (PortalException e) {
			_log.error("There was an error fetching the Guest site groupId");
			e.printStackTrace();
		}
		
		// Set the group parameters
		groupTypes.add(GroupConstants.TYPE_SITE_OPEN);
		groupTypes.add(GroupConstants.TYPE_SITE_RESTRICTED);
		
		groupParams.put("types", groupTypes);
		groupParams.put("active", Boolean.TRUE);
		
		// Fetch the all the Active, Non private sites (Open/Restricted)
		List<Group> nonPrivateSites = GroupLocalServiceUtil.search(themeDisplay.getCompanyId(), 
				groupParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		_log.info("Found "+ nonPrivateSites.size() +" non private sites");
		
		// Send the nonPrivateSites to the create_account.jsp view through a request attribute.
		renderRequest.setAttribute("nonPrivateSites", nonPrivateSites);
		
		// Send the Guest groupId as parameter
		renderRequest.setAttribute("guestGroupId", guestGroupId);
		
		// Call the render method from the CreateAccountMVCRenderCommand reference.
		return mvcRenderCommand.render(renderRequest, renderResponse);
	}

}
