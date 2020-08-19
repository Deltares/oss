package nl.deltares.portal.configuration;

import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;
import org.osgi.service.component.annotations.Component;

@Component(
        immediate = true,
        service = ConfigurationBeanDeclaration.class
)
public class LayoutsUrlsConfigurationBeanDeclaration implements ConfigurationBeanDeclaration {
    @Override
    public Class<?> getConfigurationBeanClass() {
        return LayoutsUrlsConfiguration.class;
    }
}
