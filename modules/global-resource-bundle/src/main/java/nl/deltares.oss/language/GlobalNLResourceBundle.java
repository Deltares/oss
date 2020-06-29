package nl.deltares.oss.language;

import com.liferay.portal.kernel.language.UTF8Control;
import org.osgi.service.component.annotations.Component;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * @author allan
 */
@Component(
        immediate = true,
        property = {
                "language.id=nl_NL",
        },
        service = ResourceBundle.class
)
public class GlobalNLResourceBundle extends ResourceBundle {

    @Override
    protected Object handleGetObject(String key) {
        return _resourceBundle.getObject(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return _resourceBundle.getKeys();
    }

    private final ResourceBundle _resourceBundle = ResourceBundle
            .getBundle("content.Language_nl_NL", UTF8Control.INSTANCE);

}