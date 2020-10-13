package nl.deltares.portal.configuration;

import com.liferay.configuration.admin.category.ConfigurationCategory;
import org.osgi.service.component.annotations.Component;

@Component(
        service = ConfigurationCategory.class
)
public class DSDConfigurationCategory implements ConfigurationCategory {
    @Override
    public String getCategoryKey() {
        return _CATEGORY_KEY;
    }

    @Override
    public String getCategorySection() {
        return _CATEGORY_SECTION;
    }

    private static final String _CATEGORY_KEY = "dsd-general";
    private static final String _CATEGORY_SECTION = "dsd";
}
