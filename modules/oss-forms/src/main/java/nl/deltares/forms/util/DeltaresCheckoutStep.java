package nl.deltares.forms.util;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public interface DeltaresCheckoutStep {
    String getName();

    boolean isVisible(HttpServletRequest request, HttpServletResponse response);

    void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception;

    void processAction(
            ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception;

    boolean isOrder();

    boolean isSennaDisabled();

    String getLabel(Locale locale);

    boolean isActive(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    boolean showControls(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse);
}
