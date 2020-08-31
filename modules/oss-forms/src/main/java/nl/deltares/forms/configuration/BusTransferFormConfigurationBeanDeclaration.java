package nl.deltares.forms.configuration;

import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;
import org.osgi.service.component.annotations.Component;

@Component(
        immediate = true,
        service = ConfigurationBeanDeclaration.class
)
public class BusTransferFormConfigurationBeanDeclaration implements ConfigurationBeanDeclaration {
    @Override
    public Class<?> getConfigurationBeanClass() {
        return BusTransferFormConfiguration.class;
    }
}
