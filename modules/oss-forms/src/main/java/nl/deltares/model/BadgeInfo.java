package nl.deltares.model;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BadgeInfo implements Serializable {

    public final static String badge_name_setting = "badge_name_setting";
    public final static String badge_title_setting = "badge_title_setting";
    public final static String badge_title = "badge_title";
    public final static String badge_initials = "badge_initials";
    public final static String[] ATTRIBUTES = new String[]{badge_name_setting, badge_title_setting,badge_title, badge_initials};

    String name_setting = "name";
    String title_setting = "no";
    String title = null;
    String initials = null;
    String firstName = null;
    String lastName = null;


    public void setAttribute(String key, String value){
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

    public String getAttribute(String  key){
        return switch (key) {
            case badge_name_setting -> name_setting;
            case badge_title_setting -> title_setting;
            case badge_title -> title;
            case badge_initials -> initials;
            default -> throw new UnsupportedOperationException("Unsupported billing attribute: " + key);
        };
    }

    public boolean isShowTitle(){
        return "yes".equals(title_setting);
    }


    public boolean isShowInitials() {
        return !"name".equals(name_setting);
    }

    public Map<String, String> toMap(){
        final HashMap<String, String> map = new HashMap<>();
        for (String key : ATTRIBUTES) {
            final String value = getAttribute(key);
            if (Validator.isNotNull(value)) map.put(key, value);

        }
        return map;
    }

    public static BadgeInfo getInstance(Map<String, String> preferences) {
        final BadgeInfo badgeInfo = new BadgeInfo();
        for (String key : preferences.keySet()) {
            try {
                badgeInfo.setAttribute(key, preferences.get(key));
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

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public String getInitials() {
        return initials;
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
