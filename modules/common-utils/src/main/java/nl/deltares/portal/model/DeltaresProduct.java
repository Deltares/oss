package nl.deltares.portal.model;

import com.liferay.commerce.frontend.model.PriceModel;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.model.*;
import com.liferay.commerce.product.service.*;
import nl.deltares.portal.constants.DeltaresProductKeys;
import nl.deltares.portal.utils.Period;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DeltaresProduct {
    private final CProduct product;

    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    private final CPCatalogEntry cpCatalogEntry;
    private final List<Period> periods = new ArrayList<>();
    private String commerceCurrencyCode;
    private float price;

    private TimeZone timeZone;


    public DeltaresProduct(CPCatalogEntry entry) throws Exception {
        this.cpCatalogEntry = entry;
        this.product = CProductLocalServiceUtil.getCProduct(entry.getCProductId());
        initPeriods();
    }

    public float getPrice(){
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCommerceCurrencyCode(String commerceCurrencyCode) {
        this.commerceCurrencyCode = commerceCurrencyCode;
    }

    public String getCurrency(){
        return commerceCurrencyCode;
    }
    public CPCatalogEntry getCpCatalogEntry() {
        return cpCatalogEntry;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public long getStartTime() {
        if (periods.isEmpty()) return 0;
        return periods.get(0).getStartTime();
    }

    public long getEndTime() {
        if (periods.isEmpty()) return 0;
        return periods.get(periods.size() - 1).getEndTime();
    }

    public int getPeriodsCount() {
        return periods.size();
    }

    public Period getPeriod(int index) {

        if (index >= periods.size()) {
            throw new IllegalArgumentException("Periods index out of bounds");
        }
        return periods.get(index);
    }

    private void initPeriods() throws Exception {

        final CPOptionCategory dateCategory = CPOptionCategoryLocalServiceUtil.getCPOptionCategory(product.getCompanyId(), DeltaresProductKeys.CATEGORY_COURSE_DATES);
        final List<CPDefinitionSpecificationOptionValue> dateValues = CPDefinitionSpecificationOptionValueLocalServiceUtil
                .getCPDefinitionSpecificationOptionValues(product.getPublishedCPDefinitionId(), dateCategory.getCPOptionCategoryId());

        final ArrayList<String> startTimeStrings = new ArrayList<>();
        final ArrayList<String> endTimeStrings = new ArrayList<>();

        String timeZoneString = "GMT";
        for (CPDefinitionSpecificationOptionValue dateValue : dateValues) {
            switch (dateValue.getCPSpecificationOption().getKey()) {
                case DeltaresProductKeys.START_TIME:
                    startTimeStrings.add(dateValue.getValueCurrentValue());
                    break;
                case DeltaresProductKeys.END_TIME:
                    endTimeStrings.add(dateValue.getValueCurrentValue());
                    break;
                case DeltaresProductKeys.TIMEZONE:
                    timeZoneString = dateValue.getValueCurrentValue();
            }
        }

        if (startTimeStrings.size() != endTimeStrings.size()) {
            final CPDefinition cpDefinition = CPDefinitionLocalServiceUtil.getCPDefinition(product.getPublishedCPDefinitionId());
            throw new IllegalStateException(String.format("Product item %s does not have equal number of start- and end-date values", cpDefinition.getName()));
        }

        final ArrayList<Long> startTimes = new ArrayList<>();
        final ArrayList<Long> endTimes = new ArrayList<>();

        timeZone = TimeZone.getTimeZone(timeZoneString);
        dateTimeFormat.setTimeZone(timeZone);
        timeFormat.setTimeZone(timeZone);

        startTimeStrings.forEach(s -> {
            try {
                startTimes.add(parseDate(s));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        endTimeStrings.forEach(s -> {
            try {
                endTimes.add(parseDate(s));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        startTimes.sort(Comparator.comparingLong(Long::longValue));
        endTimes.sort(Comparator.comparingLong(Long::longValue));

        for (int i = 0; i < startTimes.size(); i++) {
            periods.add(new Period(startTimes.get(i), endTimes.get(i)));
        }
    }

    public boolean isEndDateInThePast() {
        return getEndTime() < System.currentTimeMillis();
    }

    public boolean isStartDateInThePast() {

        return getStartTime() < System.currentTimeMillis();
    }

    public boolean isSameDay(long date1, long date2) {
        return new Date(date1).toInstant().atZone(timeZone.toZoneId()).toLocalDate().isEqual(
                new Date(date2).toInstant().atZone(timeZone.toZoneId()).toLocalDate()
        );
    }

    public String getTimeString(long timeMillis) {
        return timeFormat.format(new Date(timeMillis));
    }

    private Long parseDate(String stringTime) throws ParseException {

        if (stringTime.contains("-")) {
            stringTime = stringTime.replaceAll("-", "/");
        }
        return dateTimeFormat.parse(stringTime).getTime();
    }

}
