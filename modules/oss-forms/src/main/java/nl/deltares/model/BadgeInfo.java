package nl.deltares.model;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

public class BadgeInfo {

    public enum ATTRIBUTES {
        badge_name_setting,
        badge_title_setting
    }

    String name_setting = null;
    String title_setting = null;

    public void setAttribute(ATTRIBUTES key, String value){
        switch (key){
            case badge_name_setting:
                name_setting = value;
                break;
            case badge_title_setting:
                title_setting = value;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported billing attribute: " + key);
        }
    }

    public String getAttribute(ATTRIBUTES key){
        switch (key){
            case badge_name_setting:
                return name_setting;
            case badge_title_setting:
                return title_setting;
            default:
                throw new UnsupportedOperationException("Unsupported billing attribute: " + key);
        }
    }

    public Map<String, String> toMap(){
        final HashMap<String, String> map = new HashMap<>();
        for (ATTRIBUTES key : ATTRIBUTES.values()) {
            final String value = getAttribute(key);
            if (Validator.isNotNull(value)) map.put(key.name(), value);

        }
        return map;
    }

    public String getNameSetting() {
        return name_setting;
    }

    public String getTitleSetting() {
        return title_setting;
    }

    public void setNameSetting(String name_setting) {
        this.name_setting = name_setting;
    }

    public void setTitleSetting(String title_setting) {
        this.title_setting = title_setting;
    }
}
