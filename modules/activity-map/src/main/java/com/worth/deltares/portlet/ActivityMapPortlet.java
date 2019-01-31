package com.worth.deltares.portlet;


import com.worth.deltares.constants.ActivityMapPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;


/**set
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.deltares",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Activity Map",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ActivityMapPortletKeys.ActivityMap,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ActivityMapPortlet extends MVCPortlet {
}
