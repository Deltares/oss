package nl.deltares.search.facet.date;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import nl.deltares.search.util.FacetUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + SearchModuleKeys.DATE_RANGE_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class DateRangeFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        final Group scopeGroup = portletSharedSearchSettings.getThemeDisplay().getScopeGroup();
        long groupId = scopeGroup.getGroupId();
        final Locale siteDefaultLocale = LocaleUtil.fromLanguageId(scopeGroup.getDefaultLanguageId());

        Date startDate = getDate(portletSharedSearchSettings, "startDate");
        Date endDate = getDate(portletSharedSearchSettings, "endDate");
        boolean setStartNow = getBoolean(portletSharedSearchSettings, "setStartNow");
        if (setStartNow && startDate == null){
            startDate = new Date();
        }

        String[] structureKeys = getStructureKeys(portletSharedSearchSettings);
        String dateFieldName = getDsdConfiguredValue("dsdRegistrationDateField", portletSharedSearchSettings);
        _dsDsdJournalArticleUtils.queryDateRange(
                groupId, startDate, endDate, structureKeys, dateFieldName, portletSharedSearchSettings.getSearchContext(), siteDefaultLocale);

    }

    private String[] getStructureKeys(PortletSharedSearchSettings portletSharedSearchSettings) {

        String structureList = getDsdConfiguredValue("dsdRegistrationStructures", portletSharedSearchSettings);
        if (structureList != null && !structureList.isEmpty()){
            return StringUtil.split(structureList, ' ');
        }
        return new String[0];
    }

    @SuppressWarnings("SameParameterValue")
    private boolean getBoolean(PortletSharedSearchSettings portletSharedSearchSettings, String booleanField){
        Optional<String> showPastOptional = portletSharedSearchSettings.getParameterOptional(booleanField);
        if (showPastOptional.isPresent()){
            return Boolean.parseBoolean(showPastOptional.get());
        }
        String configuredValue = getConfiguredValue(booleanField, portletSharedSearchSettings);
        return Boolean.parseBoolean(configuredValue);
    }

    private Date getDate(PortletSharedSearchSettings portletSharedSearchSettings, String dateField) {

        Optional<String> optional = portletSharedSearchSettings.getParameterOptional(dateField);
        Locale locale = portletSharedSearchSettings.getThemeDisplay().getLocale();
        //check for parameter is in namespace of searchResultsPortlet
        String dateValue = optional.orElseGet(() -> FacetUtils.getIteratorParameter(dateField, portletSharedSearchSettings.getRenderRequest()));

        if (dateValue != null) {
            try {
                return DateUtil.parseDate("dd-MM-yyyy", dateValue, locale);
            } catch (ParseException e) {
                LOG.warn(String.format("Could not parse configured date %s: %s", dateValue, e.getMessage()), e);
            }
        }
        String dateText = getConfiguredValue(dateField, portletSharedSearchSettings);
        if (dateText != null && !dateText.isEmpty()){
            try {
                return DateUtil.parseDate("dd-MM-yyyy", dateText, portletSharedSearchSettings.getThemeDisplay().getLocale());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getConfiguredValue(String key, PortletSharedSearchSettings portletSharedSearchSettings){

        try {
            DateRangeFacetConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(DateRangeFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
            switch (key) {
                case "setStartNow":
                    return configuration.setStartNow();
                case "startDate":
                    return configuration.startDate();
                case "endDate":
                    return configuration.endDate();
            }

        } catch (ConfigurationException e) {
            LOG.warn("Could not find configuration for key " + key, e);
        }
        return null;
    }

    private String getDsdConfiguredValue(String key, PortletSharedSearchSettings portletSharedSearchSettings){

        try {
            DSDSiteConfiguration configuration = _configurationProvider.getPortletInstanceConfiguration(DSDSiteConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
            switch (key) {
                case "dsdRegistrationStructures":
                    return configuration.dsdRegistrationStructures();
                case "dsdRegistrationDateField":
                    return configuration.dsdRegistrationDateField();
            }

        } catch (ConfigurationException e) {
            LOG.warn("Could not find configuration for key " + key, e);
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
