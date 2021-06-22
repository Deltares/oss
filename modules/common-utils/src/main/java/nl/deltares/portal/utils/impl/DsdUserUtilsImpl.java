package nl.deltares.portal.utils.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.utils.DsdUserUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        service = DsdUserUtils.class
)
public class DsdUserUtilsImpl implements DsdUserUtils {

    @Reference
    KeycloakUtils keycloakUtils;

    private static final Log LOG = LogFactoryUtil.getLog(DsdUserUtils.class);

    @Override
    public String getUserAttribute(User user, ATTRIBUTES attribute) {

        final ExpandoBridge expandoBridge = user.getExpandoBridge();
        if (expandoBridge.hasAttribute(attribute.name())){
            return expandoBridge.getAttribute(attribute.name()).toString();
        }
        return null;
    }

    @Override
    public void setUserAttribute(User user, ATTRIBUTES attribute, String value) {

        if (value == null) value = "";

        final ExpandoBridge expandoBridge = user.getExpandoBridge();
        if(!expandoBridge.hasAttribute(attribute.name())){
            try {
                expandoBridge.addAttribute(attribute.name(), false);
            } catch (PortalException e) {
                LOG.warn("Could not add expando bridge attribute for user '" + user.getEmailAddress() + "': " + e.getMessage());
                return;
            }
        }
        expandoBridge.setAttribute(attribute.name(), value, false);
    }

    @Override
    public Map<String, String> getUserAttributes(User user) throws Exception {
        Map<String, String> userAttributes = new HashMap<>();

        //Load local attributes
        final ExpandoBridge expandoBridge = user.getExpandoBridge();
        for (DsdUserUtils.ATTRIBUTES attribute : DsdUserUtils.ATTRIBUTES.values()) {
            userAttributes.put(attribute.name(), (String) expandoBridge.getAttribute(attribute.name(), false));
        }
        //Load keycloak attributes
        final Map<String, String> keycloakAttributes = keycloakUtils.getUserAttributes(user.getEmailAddress());
        userAttributes.putAll(keycloakAttributes);
        return userAttributes;
    }

    @Override
    public int updateUserAttributes(User user, Map<String, String> attributes) throws Exception {

        for (ATTRIBUTES key : ATTRIBUTES.values()) {
            final String value = attributes.get(key.name());
            if (value == null) continue;
            setUserAttribute(user, key, value);
        }
        //Write global user attributes to keycloak
        return keycloakUtils.updateUserAttributes(user.getEmailAddress(), attributes);
    }

    @Override
    public void deleteUserAttribute(User user, DsdUserUtils.ATTRIBUTES attribute) throws PortalException {
        if (!user.getExpandoBridge().hasAttribute(attribute.name())) return;
        ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.getTable(user.getCompanyId(), User.class.getName(), "CUSTOM_FIELDS");
        ExpandoColumn expandoColumn =  ExpandoColumnLocalServiceUtil.getColumn(user.getCompanyId(), User.class.getName(), expandoTable.getName(), attribute.name());
        ExpandoValueLocalServiceUtil.deleteValue(user.getCompanyId(), User.class.getName(), expandoTable.getName(), expandoColumn.getName(), user.getUserId());
    }

    @Override
    public boolean isSubscribed(String emailAddress, List<String> mailingIdsList) throws Exception {
        return keycloakUtils.isSubscribed(emailAddress, mailingIdsList);
    }

    @Override
    public void subscribe(String emailAddress, String mailingId) throws Exception {
        keycloakUtils.subscribe(emailAddress, mailingId);
    }

    @Override
    public void unsubscribe(String emailAddress, String mailingId) throws Exception {
        keycloakUtils.unsubscribe(emailAddress, mailingId);
    }

}
