package nl.deltares.forms.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import nl.deltares.forms.util.comparator.CheckoutStepServiceWrapperOrderComparator;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component(service = DeltaresCheckoutStepRegistry.class)
public class DeltaresCheckoutStepRegistryImpl implements DeltaresCheckoutStepRegistry {
    @Override
    public DeltaresCheckoutStep getPreviousCheckoutStep(String currentCheckoutStepName, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (Validator.isNull(currentCheckoutStepName)) {
            return null;
        }

        List<DeltaresCheckoutStep> commerceCheckoutSteps =
                getCheckoutSteps(
                        httpServletRequest, httpServletResponse, true);

        int commerceCheckoutStepIndex = commerceCheckoutSteps.indexOf(
                getCheckoutStep(currentCheckoutStepName));

        if (commerceCheckoutStepIndex > 0) {
            return commerceCheckoutSteps.get(commerceCheckoutStepIndex - 1);
        }

        return null;
    }

    @Override
    public DeltaresCheckoutStep getNextCheckoutStep(
            String checkoutStepName, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        if (Validator.isNull(checkoutStepName)) {
            return null;
        }

        List<DeltaresCheckoutStep> commerceCheckoutSteps =
                getCheckoutSteps(
                        httpServletRequest, httpServletResponse, false);

        DeltaresCheckoutStep currentCommerceCheckoutStep =
                getCheckoutStep(checkoutStepName);

        for (int commerceCheckoutStepIndex = commerceCheckoutSteps.indexOf(
                currentCommerceCheckoutStep);
             commerceCheckoutStepIndex < commerceCheckoutSteps.size();
             commerceCheckoutStepIndex++) {

            if ((commerceCheckoutStepIndex >= 0) &&
                    (commerceCheckoutStepIndex <
                            (commerceCheckoutSteps.size() - 1))) {

                DeltaresCheckoutStep commerceCheckoutStep =
                        commerceCheckoutSteps.get(commerceCheckoutStepIndex + 1);

                if (commerceCheckoutStep.isActive(
                        httpServletRequest, httpServletResponse)) {

                    return commerceCheckoutStep;
                }
            }
        }

        return null;
    }

    @Override
    public List<DeltaresCheckoutStep> getCheckoutSteps(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, boolean onlyActive) {
        List<DeltaresCheckoutStep> commerceCheckoutSteps = new ArrayList<>();

        ServiceTrackerMap<String, ServiceTrackerCustomizerFactory.ServiceWrapper<DeltaresCheckoutStep>>
                commerceCheckoutStepServiceTrackerMap =
                _getCommerceCheckoutStepServiceTrackerMap();

        List<ServiceTrackerCustomizerFactory.ServiceWrapper<DeltaresCheckoutStep>>
                commerceCheckoutStepServiceWrappers = ListUtil.fromCollection(
                commerceCheckoutStepServiceTrackerMap.values());

        commerceCheckoutStepServiceWrappers.sort(_commerceCheckoutStepServiceWrapperDisplayOrderComparator);

        for (ServiceTrackerCustomizerFactory.ServiceWrapper<DeltaresCheckoutStep>
                commerceCheckoutStepServiceWrapper :
                commerceCheckoutStepServiceWrappers) {

            DeltaresCheckoutStep commerceCheckoutStep =
                    commerceCheckoutStepServiceWrapper.getService();

            if (!onlyActive ||
                    commerceCheckoutStep.isActive(
                            httpServletRequest, httpServletResponse)) {

                commerceCheckoutSteps.add(commerceCheckoutStep);
            }
        }

        return Collections.unmodifiableList(commerceCheckoutSteps);
    }

    @Override
    public DeltaresCheckoutStep getCheckoutStep(String checkoutStepName) {
        if (Validator.isNull(checkoutStepName)) {
            return null;
        }

        ServiceTrackerMap<String, ServiceTrackerCustomizerFactory.ServiceWrapper<DeltaresCheckoutStep>>
                commerceCheckoutStepServiceTrackerMap =
                _getCommerceCheckoutStepServiceTrackerMap();

        ServiceTrackerCustomizerFactory.ServiceWrapper<DeltaresCheckoutStep>
                commerceCheckoutStepServiceWrapper =
                commerceCheckoutStepServiceTrackerMap.getService(
                        checkoutStepName);

        if (commerceCheckoutStepServiceWrapper == null) {
            if (_log.isDebugEnabled()) {
                _log.debug(
                        "No checkout step registered with name " +
                                checkoutStepName);
            }

            return null;
        }

        return commerceCheckoutStepServiceWrapper.getService();
    }

    @Activate
    protected void activate(BundleContext bundleContext) {
        _bundleContext = bundleContext;
    }

    @Deactivate
    protected void deactivate() {
        if (_serviceTrackerMap != null) {
            _serviceTrackerMap.close();
        }
    }

    private ServiceTrackerMap<String, ServiceTrackerCustomizerFactory.ServiceWrapper<DeltaresCheckoutStep>>
    _getCommerceCheckoutStepServiceTrackerMap() {

        if (_serviceTrackerMap == null) {
            _serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
                    _bundleContext, DeltaresCheckoutStep.class,
                    "checkout.step.name",
                    ServiceTrackerCustomizerFactory.
                            serviceWrapper(_bundleContext));
        }

        return _serviceTrackerMap;
    }

    private static final Log _log = LogFactoryUtil.getLog(
            DeltaresCheckoutStepRegistryImpl.class);

    private BundleContext _bundleContext;
    private final Comparator<ServiceTrackerCustomizerFactory.ServiceWrapper<DeltaresCheckoutStep>>
            _commerceCheckoutStepServiceWrapperDisplayOrderComparator =
            new CheckoutStepServiceWrapperOrderComparator();
    private ServiceTrackerMap<String, ServiceTrackerCustomizerFactory.ServiceWrapper<DeltaresCheckoutStep>>
            _serviceTrackerMap;
}
