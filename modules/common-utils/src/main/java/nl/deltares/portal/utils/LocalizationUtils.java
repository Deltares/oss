package nl.deltares.portal.utils;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class LocalizationUtils {

    public static String getLocalizedValue(String jsonValue, String language) {

        if (jsonValue != null && jsonValue.isEmpty()) return null;
        try {
            final Map<String, String> map = JsonContentUtils.parseJsonToMap(jsonValue);
            final String value = map.get(language);
            if (value != null && !value.isEmpty()) return value;
            final Iterator<String> iterator = map.values().iterator();
            if (iterator.hasNext()) return iterator.next();
        } catch (JSONException e) {
            //
        }
        return null;
    }

    public static List<String> getAvailableLanguageIds(HttpServletRequest httpServletRequest) {
        final List<String> ids = new ArrayList<>();
        final Enumeration<Locale> supportedLocales = httpServletRequest.getLocales();
        while (supportedLocales.hasMoreElements()){
            final String language = supportedLocales.nextElement().getLanguage();
            if (!ids.contains(language)) ids.add(language);
        }
        return ids;
    }

    public static Map<String, String> convertToLocalizedMap(ActionRequest actionRequest, String parameterId){

        HashMap<String, String> map = new HashMap<>();
        final Enumeration<Locale> locales = actionRequest.getLocales();
        while (locales.hasMoreElements()){
            final Locale locale = locales.nextElement();
            final String language = locale.getLanguage();
            final String localizedValue = ParamUtil.getString(actionRequest, parameterId + '-' + language);
            if (localizedValue != null && !map.containsKey(language)) map.put(language, localizedValue);
        }
        return map;

    }
}
