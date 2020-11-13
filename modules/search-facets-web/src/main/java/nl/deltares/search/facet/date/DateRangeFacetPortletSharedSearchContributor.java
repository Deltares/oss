package nl.deltares.search.facet.date;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.FacetPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.text.ParseException;
import java.util.Date;
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
        long groupId = portletSharedSearchSettings.getThemeDisplay().getScopeGroupId();

        Date startDate = getDate(portletSharedSearchSettings, "startDate");
        Date endDate = getDate(portletSharedSearchSettings, "endDate");
        boolean showPast = getBoolean(portletSharedSearchSettings, "showPast");
        if (!showPast && startDate == null){
            startDate = new Date();
        }

        _dsDsdJournalArticleUtils.contributeDsdDateRangeRegistrations(
                groupId, startDate, endDate, portletSharedSearchSettings.getSearchContext(), locale);

    }
    private boolean getBoolean(PortletSharedSearchSettings portletSharedSearchSettings, String fieldName){
        Optional<String> showPastOptional = portletSharedSearchSettings.getParameter("showPast");
        if (showPastOptional.isPresent()){
            return Boolean.parseBoolean(showPastOptional.get());
        }
        Object configuredValue = getConfiguredValue(fieldName, portletSharedSearchSettings);
        return configuredValue != null && (Boolean) configuredValue;
    }
    private Date getDate(PortletSharedSearchSettings portletSharedSearchSettings, String dateField) {

        Optional<String> optional = portletSharedSearchSettings.getParameter(dateField);
        Locale locale = portletSharedSearchSettings.getThemeDisplay().getLocale();
        if (optional.isPresent()) {
            try {
                return DateUtil.parseDate( "dd-MM-yyyy", optional.get(), locale);
            } catch (ParseException e) {
                LOG.warn(String.format("Could not parse configured date %s: %s", optional.get(), e.getMessage()), e);
            }
        }
        return getConfiguredDate(dateField, portletSharedSearchSettings);
    }

    private Object getConfiguredValue(String key, PortletSharedSearchSettings portletSharedSearchSettings){

        try {
            DateRangeFacetConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(DateRangeFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
            if (key.equals("showPast")) {
                String showPast = configuration.showPast();
                if (showPast != null && !showPast.isEmpty()){
                    return Boolean.parseBoolean(showPast);
                }
                return true;
            }

        } catch (ConfigurationException e) {
            LOG.warn("Could not get configuration", e);
        }
        return null;
    }

    private Date getConfiguredDate(String key, PortletSharedSearchSettings portletSharedSearchSettings){

        try {
            DateRangeFacetConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(DateRangeFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
            Locale locale = portletSharedSearchSettings.getThemeDisplay().getLocale();
            if (key.equals("startDate")) {
                String dateText = configuration.startDate();
                if (dateText != null && !dateText.isEmpty()){
                    return DateUtil.parseDate("dd-MM-yyyy", dateText, locale);
                }
                return null;
            } else if (key.equals("endDate")){
                String dateText = configuration.endDate();
                if (dateText != null && !dateText.isEmpty()){
                    return DateUtil.parseDate("dd-MM-yyyy", dateText, locale);
                }
                return null;
            }

        } catch (ConfigurationException | ParseException e) {
            LOG.warn("Could not get date configuration", e);
        }
        return null;
    }


    @Reference
    private DsdJournalArticleUtils _dsDsdJournalArticleUtils;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(DateRangeFacetPortletSharedSearchContributor.class);
}
