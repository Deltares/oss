package nl.deltares.forms.portlet.action;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
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
import nl.deltares.portal.utils.impl.RegistrationUtilsImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + OssConstants.REGISTRATIONFORM,
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
            SessionErrors.add(httpServletRequest, RegistrationFormException.class, Collections.singletonList(
                    new RegistrationFormException(e.getMessage()))
            );
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

    private DsdSessionUtils _dsdSessionUtils;

    @Reference(
            unbind = "-",
            cardinality = ReferenceCardinality.MULTIPLE
    )
    protected void setDsdSessionUtils(DsdSessionUtils dsdSessionUtils) {

        if (dsdSessionUtils instanceof RegistrationUtilsImpl){
            _dsdSessionUtils = dsdSessionUtils;
        }
    }

    @Reference
    private ConfigurationProvider _configurationProvider;

    @Reference
    private URLUtils _urlUtils;
}
