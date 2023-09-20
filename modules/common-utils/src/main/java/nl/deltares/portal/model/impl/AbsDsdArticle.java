package nl.deltares.portal.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DuplicateCheck;
import nl.deltares.portal.utils.JsonContentUtils;
import org.w3c.dom.Document;

import java.text.SimpleDateFormat;
import java.util.*;

public abstract class AbsDsdArticle implements DsdArticle {

    private final JournalArticle article;
    public final long instantiationTime;
    protected final DsdParserUtils dsdParserUtils;
    private final Locale locale;
    private List<DDMFormFieldValue> ddmFormFieldValues;
    final private SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    @Override
    public void validate() throws PortalException {
        //
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Generic.name();
    }

    @Override
    public long getResourceId() {
        if (article == null) return 0;
        return article.getResourcePrimKey();
    }

    @Override
    public String getArticleId() {
        if (article == null) return "0";
        return article.getArticleId();
    }

    @Override
    public String getTitle() {
        if (article == null) return "";
        return article.getTitle();
    }

    @Override
    public long getGroupId(){
        if (article == null) return 0;
        return article.getGroupId();
    }

    @Override
    public long getCompanyId(){
        if (article == null) return 0;
        return article.getCompanyId();
    }

    @Override
    public Document getDocument(){
        return null;
    }

    AbsDsdArticle(){
        this.article = null;
        this.instantiationTime = System.currentTimeMillis();
        this.dsdParserUtils = null;
        this.locale = null;
    }

    AbsDsdArticle(){
        this.article = null;
        this.instantiationTime = System.currentTimeMillis();
        this.dsdParserUtils = null;
        this.locale = null;
    }

    AbsDsdArticle(JournalArticle article, DsdParserUtils dsdParserUtils, Locale locale) throws PortalException {
        this.article = article;
        this.instantiationTime = System.currentTimeMillis();
        this.dsdParserUtils = dsdParserUtils;
        this.locale = locale;
        init();
    }

    private void init() {
        final DDMStructure ddmStructure = article.getDDMStructure();
        final DDMForm ddmForm = ddmStructure.getDDMForm();
        final DDMFormValues ddmFormValues = DDMFieldLocalServiceUtil.getDDMFormValues(ddmForm, article.getId());
        this.ddmFormFieldValues = ddmFormValues.getDDMFormFieldValues();
    }

    public String getSmallImageURL(ThemeDisplay themeDisplay) {
        if (article == null) return "";
        String url = article.getSmallImageURL();
        if (Validator.isNull(url)) {
            url = article.getArticleImageURL(themeDisplay);
        }
        if (url == null) return "";
        return url;
    }

    List<Room> parseRooms(List<String> roomReferences) throws PortalException {

        DuplicateCheck check = new DuplicateCheck();
        ArrayList<Room> rooms = new ArrayList<>();
        for (String json : roomReferences) {
            JournalArticle article = JsonContentUtils.jsonReferenceToJournalArticle(json);
            AbsDsdArticle room = dsdParserUtils.toDsdArticle(article, this.locale);
            if (!(room instanceof Room)) throw new PortalException(String.format("Article %s not instance of Room", article.getTitle()));
            if (check.checkDuplicates(room)) rooms.add((Room) room);
        }
        return rooms;
    }

    public JournalArticle getJournalArticle(){
        return article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbsDsdArticle that = (AbsDsdArticle) o;
        return article != null && article.getPrimaryKey() == that.article.getPrimaryKey();
    }

    @Override
    public int hashCode() {
        if (article == null) return 0;
        return Objects.hash(article.getPrimaryKey());
    }

    public Locale getLocale() {
        return locale;
    }

    public List<String> getFormFieldValues(List<DDMFormFieldValue> searchList, String fieldName, boolean optional) throws PortalException {
        return extractStringValues(getDdmFormFieldValues(searchList, fieldName, optional));
    }

    public List<String> getFormFieldArrayValue(List<DDMFormFieldValue> searchList, String fieldName, boolean optional) throws PortalException {
        return extractStringArray(getDdmFormFieldValue(searchList, fieldName, optional));
    }

    public List<String> getFormFieldArrayValue(String fieldName, boolean optional) throws PortalException {
        return extractStringArray(getDdmFormFieldValue(ddmFormFieldValues, fieldName, optional));
    }
    public String getFormFieldValue(List<DDMFormFieldValue> searchList, String fieldName, boolean optional) throws PortalException {
        return extractStringValue(getDdmFormFieldValue(searchList, fieldName, optional));
    }

    public List<String> getFormFieldValues(String fieldName, boolean optional) throws PortalException {
        return getFormFieldValues(ddmFormFieldValues, fieldName, optional);
    }

    public String getFormFieldValue(String fieldName, boolean optional) throws PortalException {
        return getFormFieldValue(ddmFormFieldValues, fieldName, optional);
    }

    public float getFormFieldFloatValue(String fieldName, boolean optional) throws PortalException {
        String formFieldValue = getFormFieldValue(fieldName, optional);
        if (formFieldValue.indexOf(',') > 0) {
            formFieldValue = formFieldValue.replace(',', '.');
        }
        return Float.parseFloat(formFieldValue);
    }
    public DDMFormFieldValue getDdmFormFieldValue(List<DDMFormFieldValue> searchList, String fieldName, boolean optional) throws PortalException {

        final ArrayList<DDMFormFieldValue> foundFormFieldValues = new ArrayList<>();
        loadFormFieldValues(fieldName, searchList, foundFormFieldValues, false);

        if (foundFormFieldValues.size() == 0){
            if (optional) return null;
            throw new PortalException(String.format("Could not find required field %s in DSD article %s!", fieldName, getTitle()));
        } else {
            return foundFormFieldValues.get(0);
        }
    }
    public List<DDMFormFieldValue> getDdmFormFieldValues(String fieldName, boolean optional) throws PortalException {
        return getDdmFormFieldValues(ddmFormFieldValues, fieldName, optional);
    }

    public List<DDMFormFieldValue> getDdmFormFieldValues(List<DDMFormFieldValue> searchList, String fieldName, boolean optional) throws PortalException {

        final ArrayList<DDMFormFieldValue> foundFormFieldValues = new ArrayList<>();
        loadFormFieldValues(fieldName, searchList, foundFormFieldValues, false);

        if (foundFormFieldValues.size() == 0 && !optional){
            throw new PortalException(String.format("Could not find required field %s in DSD article %s!", fieldName, getTitle()));
        }

        return foundFormFieldValues;
    }

    private List<String> extractStringArray(DDMFormFieldValue formFieldValue){
        final String localStringValue = formFieldValue.getValue().getString(locale);
        if (!localStringValue.isEmpty() && !localStringValue.equals("{}")) {
            return parseToArray(localStringValue);
        }
        return Collections.singletonList(localStringValue);
    }

    private String extractStringValue(DDMFormFieldValue formFieldValue){
        if (formFieldValue == null) return null;
        final String localStringValue = formFieldValue.getValue().getString(locale);
        if (localStringValue.isEmpty() || localStringValue.equals("{}")) return null;
        return removeBrackets(localStringValue);
    }

    private List<String> extractStringValues(List<DDMFormFieldValue> formFieldValues){
        final List<String> stringValues = new ArrayList<>(formFieldValues.size());
        for (DDMFormFieldValue fieldValue : formFieldValues) {
            final String e = extractStringValue(fieldValue);
            if (e != null) stringValues.add(e);
        }
        return stringValues;
    }

    /**
     * As of 7.4 checkbox values contain brackets
     * @param localStringValue row input string
     * @return trimmed string
     */
    private String removeBrackets(String localStringValue) {
        try {
            return JsonContentUtils.parseJsonArrayToValue(localStringValue);
        } catch (JSONException e) {
            return localStringValue;
        }
    }

    /**
     * As of 7.4 checkbox values contain brackets
     * @param localStringValue row input string
     * @return trimmed string
     */
    private List<String> parseToArray(String localStringValue) {
        try {
            final JSONArray jsonArray = JsonContentUtils.parseContentArray(localStringValue);
            final ArrayList<String> values = new ArrayList<>();
            jsonArray.forEach(o -> values.add(String.valueOf(o)));
            return values;
        } catch (JSONException e) {
            return Collections.singletonList(localStringValue);
        }
    }
    public Date parseDateTimeFields(String dateValue, String timeValue, TimeZone timeZone) throws PortalException {
        if (timeValue == null){
            timeValue = "00:00";
        }
        String dateTimeValue = dateValue + 'T' + timeValue;

        dateTimeFormatter.setTimeZone(timeZone);
        try {
            return dateTimeFormatter.parse(dateTimeValue);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing dateTime %s: %s", dateTimeValue, e.getMessage()));
        }
    }

    private void loadFormFieldValues(String fieldName, List<DDMFormFieldValue> searchList, List<DDMFormFieldValue> foundList, boolean singleValue) {

        for (DDMFormFieldValue ddmFormFieldValue : searchList) {
            if (ddmFormFieldValue.getFieldReference().equals(fieldName)){
                foundList.add(ddmFormFieldValue);
            } else if(ddmFormFieldValue.getNestedDDMFormFieldValues().size() > 0){
                loadFormFieldValues(fieldName, ddmFormFieldValue.getNestedDDMFormFieldValues(), foundList, singleValue);
            }
            if (foundList.size() > 0 && singleValue) return;
        }

    }

}
