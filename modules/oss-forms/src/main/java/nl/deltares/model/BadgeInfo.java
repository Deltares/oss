package nl.deltares.model;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

public class BadgeInfo {

    public enum ATTRIBUTES {
        badge_name_setting,
        badge_title_setting,
        badge_title,
        badge_initials
    }

    String name_setting = "name";
    String title_setting = "no";
    String title = null;
    String initials = null;

    public void setAttribute(ATTRIBUTES key, String value){
        switch (key){
            case badge_name_setting:
                name_setting = value;
                break;
            case badge_title_setting:
                title_setting = value;
                break;
            case badge_title:
                title = value;
                break;
            case badge_initials:
                initials = value;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported badge attribute: " + key);
        }
    }

    public String getAttribute(ATTRIBUTES key){
        switch (key){
            case badge_name_setting:
                return name_setting;
            case badge_title_setting:
                return title_setting;
            case badge_title:
                return title;
            case badge_initials:
                return initials;
            default:
                throw new UnsupportedOperationException("Unsupported billing attribute: " + key);
        }
    }

    public boolean isShowTitle(){
        return "yes".equals(title_setting);
    }


    public boolean isShowInitials() {
        return !"name".equals(name_setting);
    }

    public Map<String, String> toMap(){
        final HashMap<String, String> map = new HashMap<>();
        for (ATTRIBUTES key : ATTRIBUTES.values()) {
            final String value = getAttribute(key);
            if (Validator.isNotNull(value)) map.put(key.name(), value);

        }
        return map;
    }

    public static BadgeInfo getInstance(Map<String, String> preferences) {
        final BadgeInfo badgeInfo = new BadgeInfo();
        for (String key : preferences.keySet()) {
            try {
                final ATTRIBUTES attribute = ATTRIBUTES.valueOf(key);
                badgeInfo.setAttribute(attribute, preferences.get(key));
            } catch (IllegalArgumentException e) {
                // continue;
            }
        }
        return badgeInfo;
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

    public void setTitle(String title){
        this.title = title;
    }

    public void setInitials(String initials){
        this.initials = initials;
    }

    public String formatBadgeName(String firstName, String lastName){

        final StringBundler stringBundler = new StringBundler();
        if (isShowTitle() && title != null){
            stringBundler.append(title);
            stringBundler.append(' ');
        }
        if (initials == null) name_setting = "name";
        switch (name_setting){
            case "initials":
                stringBundler.append(initials);
                stringBundler.append(' ');
                break;
            case "both":
                stringBundler.append(initials);
                stringBundler.append(" (");
                stringBundler.append(firstName);
                stringBundler.append(") ");
                break;
            case "name" :
                stringBundler.append(firstName);
                stringBundler.append(' ');
                break;
        }
        stringBundler.append(lastName);

        return stringBundler.toString();
    }


}
