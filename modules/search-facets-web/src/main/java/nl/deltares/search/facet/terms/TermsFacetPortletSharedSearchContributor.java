package nl.deltares.search.facet.terms;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {
                "jakarta.portlet.name=" + SearchModuleKeys.TERMS_FACET_PORTLET
        },
        service = PortletSharedSearchContributor.class
)
public class TermsFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {

    private static final Log LOG = LogFactoryUtil.getLog(TermsFacetPortletSharedSearchContributor.class);

    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {

        try {
            TermsFacetConfiguration termsPortletConfiguration = _configurationProvider.getPortletInstanceConfiguration(TermsFacetConfiguration.class, portletSharedSearchSettings.getThemeDisplay().getLayout(), portletSharedSearchSettings.getPortletId());
            String termFieldName = termsPortletConfiguration.termFieldName();
            String termFieldValue = termsPortletConfiguration.termValue();
            boolean multipleTermValues = Boolean.parseBoolean(termsPortletConfiguration.multipleTermValues());
            if (!termFieldValue.isEmpty()) {
                try {
                    if (multipleTermValues) {
                        _dsdJournalArticleUtils.addTermsFacet(portletSharedSearchSettings, termFieldName, termFieldValue.split(" "),false);
                    } else {
                        _dsdJournalArticleUtils.addTermFacet(portletSharedSearchSettings, termFieldName, termFieldValue,
                                false, Boolean.parseBoolean(termsPortletConfiguration.useWildcard()), Boolean.parseBoolean(termsPortletConfiguration.isDdmField()));
                    }
                } catch (PortalException e) {
                    LOG.warn(e);
                }
            }
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }

    }

    @Reference
    private ConfigurationProvider _configurationProvider;

    @Reference
    private DsdJournalArticleUtils _dsdJournalArticleUtils;
}