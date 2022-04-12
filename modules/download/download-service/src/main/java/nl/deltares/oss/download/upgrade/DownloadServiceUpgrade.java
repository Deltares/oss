package nl.deltares.oss.download.upgrade;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import org.osgi.service.component.annotations.Component;

@Component(service = UpgradeStepRegistrator.class)
public class DownloadServiceUpgrade implements UpgradeStepRegistrator {
    @Override
    public void register(Registry registry) {
//nothing to update yet
    }
}
