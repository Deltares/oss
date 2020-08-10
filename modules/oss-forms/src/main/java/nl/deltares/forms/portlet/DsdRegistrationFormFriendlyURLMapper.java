package nl.deltares.forms.portlet;

import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import nl.deltares.forms.constants.OssFormPortletKeys;
import org.osgi.service.component.annotations.Component;

@Component(
        property = {
                "com.liferay.portlet.friendly-url-routes=META-INF/friendly-url-routes/routes.xml",
                "javax.portlet.name=" + OssFormPortletKeys.DSD_REGISTRATIONFORM
        },
        service = FriendlyURLMapper.class
)
public class DsdRegistrationFormFriendlyURLMapper extends DefaultFriendlyURLMapper {

    @Override
    public String getMapping() {
        return _MAPPING;
    }

    private static final String _MAPPING = "registration";

}
