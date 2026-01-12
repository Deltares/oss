package nl.deltares.forms.portlet.action;

import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.forms.internal.UnregisterDisplayContext;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdSessionUtils;
import nl.deltares.portal.utils.URLUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + OssConstants.REGISTRATIONFORM,
                "javax.portlet.name=" + OssConstants.DSD_REGISTRATIONFORM,
                "mvc.command.name=/submit/unregister/form"
        },
        service = MVCActionCommand.class
)
public class SubmitUnregisterActionCommand extends BaseMVCActionCommand {

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        String articleId = ParamUtil.getString(actionRequest, "articleId");
        Long userId = ParamUtil.getLong(actionRequest, "userId");
        String redirect = ParamUtil.getString(actionRequest, "redirect");

        HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(actionRequest);

        UnregisterDisplayContext unregisterDisplayContext = new UnregisterDisplayContext(httpServletRequest,
                _configurationProvider, _dsdParserUtils, _dsdSessionUtils);

        try {
            unregisterDisplayContext.unRegisterUser(articleId, userId);
        } catch (Exception e) {
            httpServletRequest.getSession().setAttribute("registration-errors", Collections.singletonList(
                    new RegistrationFormException(e.getMessage())));
        }

        if (SessionErrors.isEmpty(httpServletRequest)) {
            redirect = _urlUtils.getRegistrationFormSuccessUrl(actionRequest, "unregister-success", redirect);
        } else {
            redirect = _urlUtils.getRegistrationFormFailUrl(actionRequest, "unregister-error", redirect);
        }
        sendRedirect(actionRequest, actionResponse, redirect);

    }

    @Reference
    private Portal _portal;

    @Reference
    private DsdParserUtils _dsdParserUtils;

    @Reference
    private DsdSessionUtils _dsdSessionUtils;

    @Reference
    private ConfigurationProvider _configurationProvider;

    @Reference
    private URLUtils _urlUtils;
}
