package nl.deltares.fullcalendar.portlet;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.utils.DsdJournalArticleUtils;

import java.util.Map;
import java.util.TreeMap;

public class FullCalendarUtils {

    public static String[] getStructureKeys(DSDSiteConfiguration configuration) {
        if (configuration == null) return new String[0];
        String structureList = configuration.dsdRegistrationStructures();
        if (structureList != null && !structureList.isEmpty()){
            return StringUtil.split(structureList, ' ');
        }
        return new String[0];
    }


    public static Map<String, String> getTypeMap(ThemeDisplay themeDisplay, DsdJournalArticleUtils dsdJournalArticleUtils,
                                                 ConfigurationProvider configurationProvider) throws PortalException {

        DSDSiteConfiguration siteConfiguration;
        try {
            siteConfiguration = configurationProvider
                    .getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getSiteGroupId());

        } catch (ConfigurationException e) {
            throw new PortalException(String.format("Error getting DSD siteConfiguration: %s", e.getMessage()));
        }
        String[] structureKeys = getStructureKeys(siteConfiguration);
        String registrationTypeField = siteConfiguration.dsdRegistrationTypeField();
        Map<String, String> typeMap = new TreeMap<>();
        for (String structureKey : structureKeys) {
            typeMap.putAll(dsdJournalArticleUtils.getStructureFieldOptions(themeDisplay.getSiteGroupId(),
                    structureKey.toUpperCase(), registrationTypeField, themeDisplay.getLocale()));
        }
        return typeMap;
    }


}
