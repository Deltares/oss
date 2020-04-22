package nl.deltares.dsd.emails;

import com.liferay.petra.content.ContentUtil;

public class EmailUtils {

    public static String readTemplate(String templatePath) {
        return ContentUtil.get(EmailUtils.class.getClassLoader(), templatePath);
    }
}
