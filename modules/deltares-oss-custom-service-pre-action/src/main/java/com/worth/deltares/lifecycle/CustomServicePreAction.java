package com.worth.deltares.lifecycle;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.worth.deltares.constants.ServicePreActionPortletKeys;

/**
 * @author Cesar Isaac Hernandez Lavarreda @ Worth Systems
 */

@Component(
	immediate = true, property = {"key=servlet.service.events.pre"},
	service = LifecycleAction.class
)
public class CustomServicePreAction implements LifecycleAction{
	
	private static Log _log = LogFactoryUtil.getLog(CustomServicePreAction.class);

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {
		HttpServletRequest request = lifecycleEvent.getRequest();
		
        _log.debug("CustomServicePreAction.processLifeCycleEvent");
        
        Long openDelft3dSiteId = PrefsPropsUtil.getLong(ServicePreActionPortletKeys.OPENDELFT3D_SITE_ID, 0);
        Long xBeachSiteId = PrefsPropsUtil.getLong(ServicePreActionPortletKeys.XBEACH_SITE_ID, 0);
        Long activeCommunityId = openDelft3dSiteId;
        
        _log.debug("openDelft3dSiteId: "+openDelft3dSiteId);
        _log.debug("xBeachSiteId: "+xBeachSiteId);
        
        if(Validator.isNotNull(request.getSession().getAttribute("activeCommunityId"))) {
  			activeCommunityId = (Long) request.getSession().getAttribute("activeCommunityId");
  		}
  		
  		ThemeDisplay themeDisplay= (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
  		long communityId = themeDisplay.getLayout().getGroupId();
  		if(communityId == openDelft3dSiteId || communityId == xBeachSiteId) {
  			activeCommunityId = communityId;
  		}
  		
  		Map<String,Object> map = new HashMap<String, Object>();
  		
  		request.getSession().setAttribute("activeCommunityId", activeCommunityId);
  		
  		map.put("activeCommunityId", activeCommunityId);
  		
  		request.setAttribute(WebKeys.FTL_VARIABLES, map);
	}
}
