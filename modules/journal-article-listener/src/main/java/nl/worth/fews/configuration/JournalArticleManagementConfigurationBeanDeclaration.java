package nl.worth.fews.configuration;

import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;
import org.osgi.service.component.annotations.Component;

@Component(
  immediate = true,
  service = ConfigurationBeanDeclaration.class
)
public class JournalArticleManagementConfigurationBeanDeclaration implements ConfigurationBeanDeclaration {
  @Override
  public Class<?> getConfigurationBeanClass() {
    return JournalArticleManagementConfiguration.class;
  }
}
