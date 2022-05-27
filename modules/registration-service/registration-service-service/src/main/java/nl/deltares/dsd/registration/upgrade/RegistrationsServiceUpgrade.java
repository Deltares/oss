package nl.deltares.dsd.registration.upgrade;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import nl.deltares.dsd.registration.upgrade.v1_0_0.CreateRegistrationsTable;
import nl.deltares.dsd.registration.upgrade.v1_1_0.UpgradeRegistrationsTable;
import nl.deltares.dsd.registration.upgrade.v1_2_0.AddRegisteredByUserIdTable;
import org.osgi.service.component.annotations.Component;

@Component(service = UpgradeStepRegistrator.class)
public class RegistrationsServiceUpgrade implements UpgradeStepRegistrator {
    @Override
    public void register(Registry registry) {
        registry.register(
                "0.0.1", "1.0.0",
                new CreateRegistrationsTable());

        registry.register(
                "1.0.0", "1.1.0",
                new UpgradeRegistrationsTable());

        registry.register(
                "1.1.0", "1.1.0",
                new AddRegisteredByUserIdTable());
    }
}
