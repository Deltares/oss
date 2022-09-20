package nl.deltares.search.results;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.search.constans.SearchModuleKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.util.Optional;

/**
 * @author rooij_e
 */
@Component(
        configurationPid = "nl.deltares.search.results.SearchResultsPortletConfiguration",
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=OSS-search",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=Search Results",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.config-template=/search/results/configuration.jsp",
                "javax.portlet.init-param.view-template=/search/results/view.jsp",
                "javax.portlet.name=" + SearchModuleKeys.SEARCH_RESULTS_PORTLET,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class SearchResultsPortlet extends MVCPortlet {

    @Reference
    protected DsdParserUtils dsdParserUtils;

    @Reference
    protected Portal portal;

    @Reference
    protected PortletSharedSearchRequest portletSharedSearchRequest;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        SearchResultsPortletConfiguration configuration;
        try {
            configuration = _configurationProvider.getPortletInstanceConfiguration(
                    SearchResultsPortletConfiguration.class, themeDisplay.getLayout(), themeDisplay.getPortletDisplay().getId());
        } catch (ConfigurationException e) {
            throw new PortletException(String.format("Could not get configuration for portlet '%s': %s", themeDisplay.getPortletDisplay().getId(), e.getMessage()), e);
        }

        renderRequest.setAttribute("displayTemplate", configuration.displayTemplate());
        final String type = configuration.displayType();
        renderRequest.setAttribute("displayType", type);

        PortletSharedSearchResponse portletSharedSearchResponse = portletSharedSearchRequest.search(renderRequest);
        SearchResultsPortletDisplayContext displayContext = _buildDisplayContext(portletSharedSearchResponse, renderRequest,
                themeDisplay, type);
        renderRequest.setAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT, displayContext);

        super.render(renderRequest, renderResponse);
    }

    private SearchResultsPortletDisplayContext _buildDisplayContext(PortletSharedSearchResponse portletSharedSearchResponse,
                                                                    RenderRequest renderRequest, ThemeDisplay themeDisplay, String type) {

        final SearchResultsPortletDisplayContext displayContext = new SearchResultsPortletDisplayContext(dsdParserUtils, themeDisplay);
        final SearchResponse searchResponse = portletSharedSearchResponse.getSearchResponse();
        SearchRequest searchRequest = searchResponse.getRequest();

        final int cur = ParamUtil.getInteger(renderRequest, "cur", 1);
        final int deltas = ParamUtil.getInteger(renderRequest, "delta", 20);

        Optional<String> keywordsOptional = Optional.ofNullable(searchRequest.getQueryString());
        displayContext.setKeywords(keywordsOptional.orElse(StringPool.BLANK));
        displayContext.setRenderNothing(isRenderNothing(renderRequest, searchRequest));

        displayContext.setDelta(deltas);
        displayContext.setPaginationStart((cur -1)  * deltas);
        displayContext.setResultsDocuments(portletSharedSearchResponse.getDocuments(), type);

        return displayContext;
    }

    protected boolean isRenderNothing(
            RenderRequest renderRequest, SearchRequest searchRequest) {

        long assetEntryId = ParamUtil.getLong(renderRequest, "assetEntryId");

        if (assetEntryId != 0) {
            return false;
        }

        return (searchRequest.getQueryString() == null) &&
                !searchRequest.isEmptySearchEnabled();
    }



}