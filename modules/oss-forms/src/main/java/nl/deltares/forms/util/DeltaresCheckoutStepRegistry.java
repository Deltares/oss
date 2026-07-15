package nl.deltares.forms.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface DeltaresCheckoutStepRegistry {
    DeltaresCheckoutStep getPreviousCheckoutStep(String currentCheckoutStepName, HttpServletRequest httpServletRequest,
                                                 HttpServletResponse httpServletResponse);

    DeltaresCheckoutStep getNextCheckoutStep(
            String checkoutStepName,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse)
            throws Exception;

    List<DeltaresCheckoutStep> getCheckoutSteps(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, boolean b);

    DeltaresCheckoutStep getCheckoutStep(String checkoutStepName);
}
