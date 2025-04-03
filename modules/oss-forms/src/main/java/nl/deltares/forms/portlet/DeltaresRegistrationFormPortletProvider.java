/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.forms.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.constants.OssConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Luca Pellizzon
 */
@Component(
        property = "model.class.name=" + OssConstants.REGISTRATIONFORM,
        service = PortletProvider.class
)
public class DeltaresRegistrationFormPortletProvider extends BasePortletProvider {

    @Override
    public String getPortletName() {
        return OssConstants.REGISTRATIONFORM;
    }

    @Override
    public PortletURL getPortletURL(
            HttpServletRequest httpServletRequest, Group group)
            throws PortalException {

        if (group == null) {
            ThemeDisplay themeDisplay =
                    (ThemeDisplay) httpServletRequest.getAttribute(
                            WebKeys.THEME_DISPLAY);

            group = themeDisplay.getScopeGroup();
        }

        long plid = _portal.getPlidFromPortletId(
                group.getGroupId(), getPortletName());

        return PortletURLFactoryUtil.create(
                httpServletRequest, getPortletName(), plid,
                PortletRequest.RENDER_PHASE);
    }

    @Reference
    private Portal _portal;


}