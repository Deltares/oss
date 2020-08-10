package nl.deltares.mock;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.Props;

import java.util.Properties;

public class MockProps implements Props {

    private Properties properties = new Properties();
    @Override
    public boolean contains(String key) {
        return properties.containsKey(key);
    }

    @Override
    public String get(String key) {
        return properties.getProperty(key);
    }

    @Override
    public String get(String key, Filter filter) {
        return get(key);
    }

    @Override
    public String[] getArray(String key) {
        return new String[]{properties.getProperty(key)};
    }

    @Override
    public String[] getArray(String key, Filter filter) {
        return getArray(key);
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public Properties getProperties(String prefix, boolean removePrefix) {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
