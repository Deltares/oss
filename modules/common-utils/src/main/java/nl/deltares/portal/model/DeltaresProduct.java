package nl.deltares.portal.model;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.*;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.constants.DeltaresCommerceConstants;
import nl.deltares.portal.utils.Period;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DeltaresProduct {

    private final CProduct product;

    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    private final CPCatalogEntry cpCatalogEntry;
    private final List<Period> periods = new ArrayList<>();
    private TimeZone timeZone;
    private boolean isCourse;
    private final List<DeltaresProduct> relatedChildren = new ArrayList<>();
    private boolean selected;

    private CommerceOrderItem orderItem;
    private float unitPrice;

    private String commerceCurrencyCode;

    public DeltaresProduct(CPCatalogEntry entry) throws PortalException {
        this.cpCatalogEntry = entry;
        this.product = CProductLocalServiceUtil.getCProduct(entry.getCProductId());
        initPeriods();
        initCustomFields();
    }
    public float getUnitPrice() {
        return unitPrice;
    }

    public String getCurrency() {
        return commerceCurrencyCode;
    }

    public void setCommerceCurrencyCode(String currency) {
        this.commerceCurrencyCode = currency;
    }

    public void setUnitPrice(float price) {
        this.unitPrice = price;
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

    private void initCustomFields() {

        if (product.getExpandoBridge().hasAttribute(DeltaresCommerceConstants.CUSTOM_FIELD_PRODUCT_IS_COURSE)) {
            final Serializable isCourse = product.getExpandoBridge().getAttribute(DeltaresCommerceConstants.CUSTOM_FIELD_PRODUCT_IS_COURSE, false);
            this.isCourse = (boolean) isCourse;
        }
    }

    private void initPeriods() throws PortalException {

        final CPOptionCategory dateCategory = CPOptionCategoryLocalServiceUtil.getCPOptionCategory(product.getCompanyId(), DeltaresCommerceConstants.CATEGORY_COURSE_DATES);
        final List<CPDefinitionSpecificationOptionValue> dateValues = CPDefinitionSpecificationOptionValueLocalServiceUtil
                .getCPDefinitionSpecificationOptionValues(product.getPublishedCPDefinitionId(), dateCategory.getCPOptionCategoryId());

        final ArrayList<String> startTimeStrings = new ArrayList<>();
        final ArrayList<String> endTimeStrings = new ArrayList<>();

        String timeZoneString = "GMT";
        for (CPDefinitionSpecificationOptionValue dateValue : dateValues) {
            switch (dateValue.getCPSpecificationOption().getKey()) {
                case DeltaresCommerceConstants.START_TIME:
                    startTimeStrings.add(dateValue.getValueCurrentValue());
                    break;
                case DeltaresCommerceConstants.END_TIME:
                    endTimeStrings.add(dateValue.getValueCurrentValue());
                    break;
                case DeltaresCommerceConstants.TIMEZONE:
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

    public boolean isCourse() {
        return isCourse;
    }

    public long getCProductId() {
        return product.getCProductId();
    }

    public void setOrderItem(CommerceOrderItem orderItem) {
        this.orderItem = orderItem;
        this.unitPrice = orderItem.getUnitPrice().floatValue();
        final String priceString;
        try {
            priceString = orderItem.getUnitPriceMoney().format(Locale.getDefault());
        } catch (PortalException e) {
            return;
        }
        final String[] split = priceString.split(" ");
        commerceCurrencyCode = split[0];
    }

   public float getDiscount(){
        return orderItem == null ? 0 : orderItem.getDiscountAmount().floatValue();
   }

    public float getDiscountPercentage() {
        return orderItem == null ? 0 : orderItem.getDiscountPercentageLevel1().floatValue();
    }
    public CommerceOrderItem getOrderItem(){
        return orderItem;
    }
    public int getQuantity() {
        return orderItem == null? 0 :orderItem.getQuantity().intValue();
    }

    public void addRelatedChildren(List<DeltaresProduct> relatedChildren) {
        this.relatedChildren.addAll(relatedChildren);
    }

    public List<DeltaresProduct> getRelatedChildren() {
        return relatedChildren;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public long getOrderItemId() {
        return orderItem == null ? 0 : orderItem.getCommerceOrderItemId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeltaresProduct)) return false;
        DeltaresProduct that = (DeltaresProduct) o;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }

    public long getCPDefinitionId() {
        return cpCatalogEntry.getCPDefinitionId();
    }
}
