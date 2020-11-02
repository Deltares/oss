package nl.deltares.dsd.registration.upgrade;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import org.osgi.service.component.annotations.Component;

@Component(service = UpgradeStepRegistrator.class)
public class RegistrationsServiceUpgrade implements UpgradeStepRegistrator {
    @Override
    public void register(Registry registry) {
        registry.register(
                "1.0.0", "1.1.0",
                new nl.deltares.dsd.registration.upgrade.v1_1_0.UpgradeRatingsTable());
    }
}
