package nl.deltares.search.facet.date;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DDMStructureUtil;
import nl.deltares.search.constans.FacetPortletKeys;
import nl.deltares.search.facet.date.builder.DateRangeFacetBuilder;
import nl.deltares.search.facet.date.builder.DateRangeFacetFactory;
import nl.deltares.search.util.DateFacetUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + FacetPortletKeys.DATE_RANGE_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class DateRangeFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        Locale locale = portletSharedSearchSettings.getThemeDisplay().getLocale();
        Optional<String> startDateOptional = portletSharedSearchSettings.getParameter("startDate");
        Optional<String> endDateOptional = portletSharedSearchSettings.getParameter("endDate");

        Optional<DDMStructure> ddmStructureOptional = _ddmStructureUtil
                .getDDMStructureByName("SESSION", locale);

        LocalDate startDate;
        LocalDate endDate;

        if (startDateOptional.isPresent()) {
            startDate = DateFacetUtil.parseDate(startDateOptional.get());
        } else {
            startDate = getConfiguredDate("startDate", portletSharedSearchSettings);
        }

        if (endDateOptional.isPresent()) {
            endDate = DateFacetUtil.parseDate(endDateOptional.get());
        } else {
            endDate = getConfiguredDate("endDate", portletSharedSearchSettings);
        }

        if (ddmStructureOptional.isPresent() && (startDate != LocalDate.MIN || endDate != LocalDate.MAX)) {
            long ddmStructureId = ddmStructureOptional.get().getStructureId();
            String startDateField = _ddmIndexer.encodeName(ddmStructureId, "start", locale);
            portletSharedSearchSettings.addFacet(buildFacet(startDateField, startDate, endDate, portletSharedSearchSettings));
        }
    }

    private LocalDate getConfiguredDate(String key, PortletSharedSearchSettings portletSharedSearchSettings){

        try {
            DateRangeFacetConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(DateRangeFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
            if (key.equals("startDate")) {
                String dateText = configuration.startDate();
                if (dateText != null && !dateText.isEmpty()){
                    return DateFacetUtil.parseDate(dateText);
                }
                return DateFacetUtil.getDefaultStartDate();
            } else if (key.equals("endDate")){
                String dateText = configuration.endDate();
                if (dateText != null && !dateText.isEmpty()){
                    return DateFacetUtil.parseDate(dateText);
                }
                return DateFacetUtil.getDefaultEndDate();
            }

        } catch (ConfigurationException e) {
            LOG.debug("Could not get event configuration", e);
        }
        return null;
    }
    protected Facet buildFacet(String fieldName, LocalDate startDate, LocalDate endDate,
                               PortletSharedSearchSettings portletSharedSearchSettings) {

        _dateRangeFacetFactory.setField(fieldName);

        DateRangeFacetBuilder dateRangeFacetBuilder = new DateRangeFacetBuilder(_dateRangeFacetFactory);
        dateRangeFacetBuilder.setSearchContext(portletSharedSearchSettings.getSearchContext());
        if (startDate != null) dateRangeFacetBuilder.setStartDate(formatDate(startDate));
        if (endDate != null) dateRangeFacetBuilder.setEndDate(formatDate(endDate));

        return dateRangeFacetBuilder.build();
    }

    private String formatDate(LocalDate dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Reference
    private DDMIndexer _ddmIndexer;

    @Reference
    private DDMStructureUtil _ddmStructureUtil;

    @Reference
    private DateRangeFacetFactory _dateRangeFacetFactory;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(DateRangeFacetPortletSharedSearchContributor.class);
}
