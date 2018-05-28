package com.worth.deltares.portlet;


import com.worth.deltares.constants.UserPortraitsPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;


/**
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
@Component(
  immediate = true,
  property = {
    "com.liferay.portlet.display-category=category.deltares",
    "com.liferay.portlet.instanceable=true",
    "javax.portlet.display-name=User Portraits",
    "javax.portlet.init-param.template-path=/",
    "javax.portlet.init-param.view-template=/view.jsp",
    "javax.portlet.name=" + UserPortraitsPortletKeys.UserPortraits,
    "javax.portlet.resource-bundle=content.Language",
    "javax.portlet.security-role-ref=power-user,user"
  },
  service = Portlet.class
)
public class UserPortraitsPortlet extends MVCPortlet {
}
