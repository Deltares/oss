
package nl.deltares.search.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class FacetUtils {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final HashMap<String, String> yesNo = new HashMap<>();
    static {
        yesNo.put("yes", "facet.checkbox.yes");
        yesNo.put("no", "facet.checkbox.no");
    }
    public static LocalDate getStartDate(String date) throws DateTimeParseException {
        LocalDate startDate = parseDate(date);
        if (startDate == null) {
            startDate = getDefaultStartDate();
        }
        return startDate;
    }

    public static LocalDate getEndDate(String date) throws DateTimeParseException {
        LocalDate endDate = parseDate(date);
        if (endDate == null) {
            endDate = getDefaultEndDate();
        }
        return endDate;
    }

    public static LocalDate parseDate(String date) throws DateTimeParseException {
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(date, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            LOG.error("Error parsing date", e);
        }
        return localDate;
    }

    public static LocalDate getDefaultStartDate() {
        return LocalDate.MIN;
    }

    public static LocalDate getDefaultEndDate() {
        return LocalDate.MAX;
    }

    private static final Log LOG = LogFactoryUtil.getLog(FacetUtils.class);

    public static Map<String, String> getYesNoFieldOptions(){
        return yesNo;
    }

    public static Boolean parseYesNo(String value){
        switch (value){
            case "yes": return true;
            case "no" : return false;
            default: return null;
        }
    }
}
