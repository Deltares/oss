
package nl.deltares.search.util;

import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.utils.JsonContentUtils;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
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

    public static String[] getStructureKeys(DSDSiteConfiguration configuration) {
        if (configuration == null) return new String[]{"SESSION"};
        String structureList = configuration.dsdRegistrationStructures();
        if (structureList != null && !structureList.isEmpty()) {
            return StringUtil.split(structureList, ' ');
        }
        return new String[0];
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

    public static Map<String, String> getYesNoFieldOptions() {
        return yesNo;
    }

    public static Boolean parseYesNo(String value) {
        switch (value) {
            case "yes":
                return true;
            case "no":
                return false;
            default:
                return null;
        }
    }

    public static String serializeYesNo(boolean value) {
        return value ? "yes" : "no";
    }


    public static Map<String, String> getLanguageFieldValueMap(ActionRequest actionRequest, String fieldName) {

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        HashMap<String, String> titleMap = new HashMap<>();
        LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId()).forEach(locale -> {
            final String value = ParamUtil.getString(actionRequest, fieldName + '_' + locale.toString());
            if (value.isEmpty()) return;
            titleMap.put(locale.toString(), value);
        });
        return titleMap;
    }

    public static String retrieveLanguageFieldValue(String jsonMap, String languageKey) {
        try {
            final Map<String, String> jsonToMap = JsonContentUtils.parseJsonToMap(jsonMap);
            return jsonToMap.get(languageKey);
        } catch (JSONException e) {
            return jsonMap;
        }

    }

    public static JournalArticleDisplay getArticleDisplay(PortletRequest portletRequest, PortletResponse portletResponse,
                                                          String ddmTemplateKey, String articleId, ThemeDisplay themeDisplay) {
        JournalArticleDisplay articleDisplay = null;
        try {
            articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(
                    themeDisplay.getScopeGroupId(), articleId, ddmTemplateKey, "VIEW",
                    themeDisplay.getLanguageId(), 1, new PortletRequestModel(portletRequest, portletResponse),
                    themeDisplay);
        } catch (Exception e) {
            String message = String.format("Error getting article display object for article [%s] with template ID [%s]",
                    articleId, ddmTemplateKey);
            LOG.debug(message, e);
        }
        return articleDisplay;
    }

    /**
     * Find a request parameter in the request string of the search results iterator
     *
     * @param parameterName Parameter name (no namespace)
     * @param request       Portlet request
     * @return parameter if found else null
     */
    public static String getIteratorParameter(String parameterName, PortletRequest request) {

        String searchParam = ((LiferayPortletRequest) request).getOriginalHttpServletRequest().getParameter(parameterName);
        if (searchParam != null) return searchParam;

        //Does the parameter exist for current namespace
        searchParam = ParamUtil.getString(request, parameterName, null);
        return searchParam;
    }
}
