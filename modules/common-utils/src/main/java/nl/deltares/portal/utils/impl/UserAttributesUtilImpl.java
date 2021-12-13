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
import nl.deltares.portal.utils.UserAttributesUtil;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component(
        immediate = true,
        service = UserAttributesUtil.class
)
public class UserAttributesUtilImpl implements UserAttributesUtil {

    @Reference
    KeycloakUtils keycloakUtils;

    private static final Log LOG = LogFactoryUtil.getLog(UserAttributesUtil.class);

    @Override
    public String getUserAttribute(User user, String attribute) {

        final ExpandoBridge expandoBridge = user.getExpandoBridge();
        if (expandoBridge.hasAttribute(attribute)){
            return expandoBridge.getAttribute(attribute).toString();
        }
        return null;
    }

    @Override
    public Map<String, Serializable> getUserAttributes(User user, String[] attributes)  {
        Map<String, Serializable> userAttributes = new HashMap<>();

        //Load local attributes
        final ExpandoBridge expandoBridge = user.getExpandoBridge();
        if ( attributes == null) return expandoBridge.getAttributes();

        for (String attribute : attributes) {
            if (!expandoBridge.hasAttribute(attribute)) continue;
            userAttributes.put(attribute, expandoBridge.getAttribute(attribute, false));
        }
        return userAttributes;
    }

    @Override
    public Map<String, Serializable> setUserAttributes(User user, Map<String, String> attributes) {

        Map<String, Serializable> oldAttributes = new HashMap<>();
        for (Map.Entry<String, String> stringStringEntry : attributes.entrySet()) {
            final String key = stringStringEntry.getKey();
            final String value = stringStringEntry.getValue();
            final String oldValue = setUserAttribute(user, key, value);
            if (oldValue != null) oldAttributes.put(key, oldValue);
        }
        return oldAttributes;
    }


    @Override
    public String setUserAttribute(User user, String attribute, String value) {

        if (value == null) {
            throw new NullPointerException("value");
        }
        String oldAttribute = null;
        final ExpandoBridge expandoBridge = user.getExpandoBridge();
        if(expandoBridge.hasAttribute(attribute)) {
            oldAttribute = expandoBridge.getAttribute(attribute).toString();
        } else {
            try {
                expandoBridge.addAttribute(attribute, false);
            } catch (PortalException e) {
                LOG.warn("Could not add expando bridge attribute for user '" + user.getEmailAddress() + "': " + e.getMessage());
                return null;
            }
        }
        expandoBridge.setAttribute(attribute, value, false);
        return oldAttribute;
    }

    @Override
    public void deleteUserAttribute(User user, String attribute) throws Exception {
        if (!user.getExpandoBridge().hasAttribute(attribute)) return;
        ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.getTable(user.getCompanyId(), User.class.getName(), "CUSTOM_FIELDS");
        ExpandoColumn expandoColumn =  ExpandoColumnLocalServiceUtil.getColumn(user.getCompanyId(), User.class.getName(), expandoTable.getName(), attribute);
        ExpandoValueLocalServiceUtil.deleteValue(user.getCompanyId(), User.class.getName(), expandoTable.getName(), expandoColumn.getName(), user.getUserId());
    }

    @Override
    public void deleteUserAttributes(User user, String[] attributes) throws Exception {
        ExpandoTable expandoTable = null;
        for (String attribute : attributes) {
            if (!user.getExpandoBridge().hasAttribute(attribute)) continue;
            if (expandoTable == null) expandoTable = ExpandoTableLocalServiceUtil.getTable(user.getCompanyId(), User.class.getName(), "CUSTOM_FIELDS");
            ExpandoColumn expandoColumn =  ExpandoColumnLocalServiceUtil.getColumn(user.getCompanyId(), User.class.getName(), expandoTable.getName(), attribute);
            ExpandoValueLocalServiceUtil.deleteValue(user.getCompanyId(), User.class.getName(), expandoTable.getName(), expandoColumn.getName(), user.getUserId());
        }
    }
}
