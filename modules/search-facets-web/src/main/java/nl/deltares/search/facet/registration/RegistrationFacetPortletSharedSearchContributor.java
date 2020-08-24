package nl.deltares.search.facet.registration;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import nl.deltares.portal.utils.DDMStructureUtil;
import nl.deltares.search.constans.FacetPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Optional;

@Component(
        immediate = true,
        property = "javax.portlet.name=" + FacetPortletKeys.REGISTRATION_FACET_PORTLET,
        service = PortletSharedSearchContributor.class
)
public class RegistrationFacetPortletSharedSearchContributor implements PortletSharedSearchContributor {


    @Override
    public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
        SearchContext searchContext = portletSharedSearchSettings.getSearchContext();

        Optional<DDMStructure> ddmStructureOptional = _ddmStructureUtil
                .getDDMStructureByName("SESSION", searchContext.getLocale());

        if (ddmStructureOptional.isPresent()) {
            String structureKey = ddmStructureOptional.get().getStructureKey();
            BooleanQuery structureQuery = new BooleanQueryImpl();

            try {
                structureQuery.addTerm("ddmStructureKey", structureKey);
            } catch (ParseException e) {
                LOG.debug("Could not parse term for [field: ddmStructureKey, value: " + structureKey + "]");
            }

            BooleanClause<Query> structureBooleanClause = BooleanClauseFactoryUtil
                    .create(structureQuery, BooleanClauseOccur.MUST.getName());

            searchContext.setBooleanClauses(new BooleanClause[]{structureBooleanClause});
        }

        BooleanQuery groupQuery = new BooleanQueryImpl();
        long siteGroupId = portletSharedSearchSettings.getThemeDisplay().getSiteGroupId();
        try {
            groupQuery.addTerm("groupId", siteGroupId);
        } catch (ParseException e) {
            LOG.debug("Could not parse term for [field: groupId, value: " + siteGroupId + "]");
        }
        BooleanClause<Query> groupBooleanClause = BooleanClauseFactoryUtil
                .create(groupQuery, BooleanClauseOccur.MUST.getName());

        //searchContext.setBooleanClauses(new BooleanClause[]{groupBooleanClause});
    }

    @Reference
    private DDMStructureUtil _ddmStructureUtil;

    private static final Log LOG = LogFactoryUtil.getLog(RegistrationFacetPortletSharedSearchContributor.class);
}
