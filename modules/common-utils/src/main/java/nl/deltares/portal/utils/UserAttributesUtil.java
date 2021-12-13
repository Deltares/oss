package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;

import java.io.Serializable;
import java.util.Map;

/*
 * Utility class for managing User attributes in ExpandoBridge.
 */
public interface UserAttributesUtil {

    /**
     * Get map of attribute values from expando bridge. If attribute is not found, it will be skipped. If attributes
     * is <code>null</code> all attributes will be returned.
     * @param user To to search
     * @param attributes list of requested attributes
     * @return map containing existing attributes
     *
     */
    Map<String, Serializable> getUserAttributes(User user, String[] attributes) ;

    /**
     * Get attribute value from expando bridge. If attribute is not found <code>null</code> will be returned.
     * @param user To to search
     * @param attribute requested attribute
     * @return value
     *
     */
    String getUserAttribute(User user, String attribute);

    /**
     * Delete attribute value from expando bridge. If attribute is not found, nothing will happen.
     * @param user To to search
     * @param attribute delete attribute
     *
     */
    void deleteUserAttribute(User user, String attribute) throws Exception;


    /**
     * Delete attribute values from expando bridge. If attribute is not found, nothing will happen.
     * @param user To to search
     * @param attributes delete attributes
     *
     */
    void deleteUserAttributes(User user, String[] attributes) throws Exception;

    /**
     * Update attribute values from expando bridge. If attribute is not found, attribute will be skipped.
     * @param user To to search
     * @param attributes update attributes
     *
     */
    Map<String, Serializable> setUserAttributes(User user, Map<String, String> attributes);

    /**
     * Update attribute values from expando bridge. If attribute is not found, attribute will be skipped.
     * @param user To to search
     * @param attribute update attribute
     *
     */
    String setUserAttribute(User user, String attribute, String value)  ;

}
