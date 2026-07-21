package nl.deltares.forms.util;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
