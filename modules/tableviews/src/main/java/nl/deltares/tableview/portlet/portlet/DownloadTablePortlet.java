package nl.deltares.tableview.portlet.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.service.DownloadLocalServiceUtil;
import nl.deltares.oss.geolocation.model.GeoLocation;
import nl.deltares.oss.geolocation.service.GeoLocationLocalServiceUtil;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.tableview.comparator.DownloadComparator;
import nl.deltares.tableview.model.DisplayDownload;
import nl.deltares.tableview.portlet.constants.TablePortletKeys;
import nl.deltares.tableview.tasks.impl.DeletedSelectedDownloadsRequest;
import nl.deltares.tableview.tasks.impl.ExportDownloadsTableRequest;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.version=3.0",
                "com.liferay.portlet.display-category=OSS-table",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-javascript=/lib/downloadtableview.js",
                "com.liferay.portlet.header-portlet-javascript=/lib/common.js",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=DownloadTable",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/downloadsTable.jsp",
                "javax.portlet.name=" + TablePortletKeys.DOWNLOADTABLE,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DownloadTablePortlet extends MVCPortlet {

    private static final Log logger = LogFactoryUtil.getLog(DownloadTablePortlet.class);

    @Reference
    KeycloakUtils keycloakUtils;
    final static String datePattern = "yyy-MM-dd";
    final static SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        final int curPage = ParamUtil.getInteger(renderRequest, "cur", 1);
        final int deltas = ParamUtil.getInteger(renderRequest, "delta", 25);
        final String filterValue = ParamUtil.getString(renderRequest, "filterValue", null);
        final String filterSelection = ParamUtil.getString(renderRequest, "filterSelection", null);

        doFilterValues(filterValue, filterSelection, curPage, deltas, renderRequest);

        super.render(renderRequest, renderResponse);
    }

    private void doFilterValues(String filterValue, String filterSelection, int curPage, int deltas, RenderRequest renderRequest) {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        final long siteGroupId = themeDisplay.getSiteGroupId();
        List<Download> downloads = null;
        int downloadsCount = 0;
        final int start = (curPage - 1) * deltas;
        final int end = curPage * deltas;
        try {
            if (filterValue != null && !filterValue.trim().isEmpty()) {
                switch (filterSelection) {
                    case "email":
                        User user = UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), filterValue);
                        downloads = DownloadLocalServiceUtil.findDownloadsByUserId(siteGroupId, user.getUserId(), start, end);
                        downloadsCount = DownloadLocalServiceUtil.countDownloadsByUserId(siteGroupId, user.getUserId());
                        break;
                    case "fileName":
                        downloads = DownloadLocalServiceUtil.findDownloadsByFileName(siteGroupId, filterValue, start, end);
                        downloadsCount = DownloadLocalServiceUtil.countDownloadsByFileName(siteGroupId, filterValue);
                        break;
                }
            }
            if (downloads == null) {
                downloads = DownloadLocalServiceUtil.findDownloads(siteGroupId, start, end);
                downloadsCount = DownloadLocalServiceUtil.countDownloads(siteGroupId);
            }
            String orderByCol = ParamUtil.getString(renderRequest, "orderByCol");
            String orderByType = ParamUtil.getString(renderRequest, "orderByType");
            final List<DisplayDownload> displays = convertToDisplayDownloads(downloads);
            sortDownloads(displays, orderByCol, orderByType);
            renderRequest.setAttribute("records", displays);
            renderRequest.setAttribute("total", downloadsCount);
            renderRequest.setAttribute("filterValue", filterValue);
            renderRequest.setAttribute("filterSelection", filterSelection);
        } catch (Exception e) {
            SessionErrors.add(renderRequest, "filter-failed", e.getMessage());
            renderRequest.setAttribute("records", Collections.emptyList());
            renderRequest.setAttribute("total", 0);
        }
    }
    private List<DisplayDownload> convertToDisplayDownloads(List<Download> downloads) {
        final ArrayList<DisplayDownload> displays = new ArrayList<>(downloads.size());

        HashMap<Long, Map<String, String>> userAttributeCache = new HashMap<>();
        downloads.forEach(download -> {
            final DisplayDownload displayDownload = new DisplayDownload(download);
            displays.add(displayDownload);

            if (download.getGeoLocationId() == 0){
                final long userId = download.getUserId();
                Map<String, String> attributes = userAttributeCache.get(userId);
                if (attributes == null){
                    final User user = UserLocalServiceUtil.fetchUser(userId);
                    if (user != null) {
                        try {
                            attributes = keycloakUtils.getUserAttributes(user.getEmailAddress());
                            userAttributeCache.put(userId, attributes);
                        } catch (Exception e) {
                            logger.warn(String.format("Error getting user attributes for %s: %s", user.getEmailAddress(), e.getMessage()));
                            attributes = Collections.emptyMap();
                            userAttributeCache.put(userId, attributes);
                        }
                    } else {
                        attributes = Collections.emptyMap();
                    }
                }
                displayDownload.setCity(attributes.get(KeycloakUtils.ATTRIBUTES.org_city.name()));
                final Country country = CountryLocalServiceUtil.fetchCountryByName(download.getCompanyId(), attributes.get(KeycloakUtils.ATTRIBUTES.org_country.name()));
                if (country != null) displayDownload.setCountryCode(country.getA2());
            } else {
                final long geoLocationId = download.getGeoLocationId();
                final GeoLocation geoLocation = GeoLocationLocalServiceUtil.fetchGeoLocation(geoLocationId);
                if (geoLocation != null){
                    displayDownload.setCity(geoLocation.getCityName());
                    Country country = CountryServiceUtil.fetchCountry(geoLocation.getCountryId());
                    displayDownload.setCountryCode(country.getA2());
                }
            }

        });
        return displays;
    }

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void filter(ActionRequest actionRequest, ActionResponse actionResponse) {

        final String filter = ParamUtil.getString(actionRequest, "filterValue", "none");
        actionResponse.getRenderParameters().setValue("filterValue", filter);
        final String filterSelection = ParamUtil.getString(actionRequest, "filterSelection", "none");
        actionResponse.getRenderParameters().setValue("filterSelection", filterSelection);
    }

    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response) throws IOException {

        ThemeDisplay themeDisplay = (ThemeDisplay) request
                .getAttribute(WebKeys.THEME_DISPLAY);
        if (!themeDisplay.isSignedIn() || !request.isUserInRole("administrator")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Unauthorized request!");
            return;
        }
        String action = ParamUtil.getString(request, "action");
        String id = ParamUtil.getString(request, "id", null);
        String filterValue = ParamUtil.getString(request, "filterValue", null);
        String filterSelection = ParamUtil.getString(request, "filterSelection", null);

        if ("export".equals(action)) {
            if (id == null) {
                id = DownloadTablePortlet.class.getName() + themeDisplay.getUserId();
            }
            exportTable(id, filterValue, filterSelection, response, themeDisplay);
        } else if ("delete-selected".equals(action)) {
            if (id == null) {
                id = DownloadTablePortlet.class.getName() + themeDisplay.getUserId();
            }
            deletedSelected(id, request, response, themeDisplay);

        } else if ("updateStatus".equals(action)) {
            DataRequestManager.getInstance().updateStatus(id, response);
        } else if ("downloadLog".equals(action)) {
            DataRequestManager.getInstance().downloadDataFile(id, response);
        } else {
            DataRequestManager.getInstance().writeError("Unsupported Action error: " + action, response);
        }

    }

    private void deletedSelected(String dataRequestId, ResourceRequest request, ResourceResponse response, ThemeDisplay themeDisplay) throws IOException {

        final HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
        final String[] selectedIds = httpReq.getParameterValues("selection");

        if (selectedIds.length == 0) {
            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setContentType("text/csv");
            DataRequestManager instance = DataRequestManager.getInstance();
            DataRequest dataRequest = instance.getDataRequest(dataRequestId);
            if (dataRequest == null) {
                dataRequest = new DeletedSelectedDownloadsRequest(dataRequestId, themeDisplay.getUserId(), Arrays.asList(selectedIds));
                instance.addToQueue(dataRequest);
            } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated || dataRequest.getStatus() == DataRequest.STATUS.nodata) {
                instance.removeDataRequest(dataRequest);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            String statusMessage = dataRequest.getStatusMessage();
            response.setContentLength(statusMessage.length());
            PrintWriter writer = response.getWriter();
            writer.println(statusMessage);

        }
    }


    private void exportTable(String dataRequestId, String filterValue, String filterSelection, ResourceResponse response, ThemeDisplay themeDisplay) throws IOException {
        response.setContentType("text/csv");
        DataRequestManager instance = DataRequestManager.getInstance();
        DataRequest dataRequest = instance.getDataRequest(dataRequestId);
        if (dataRequest == null) {
            dataRequest = new ExportDownloadsTableRequest(dataRequestId, filterValue, filterSelection, themeDisplay.getUserId(), themeDisplay.getSiteGroup(), keycloakUtils);
            instance.addToQueue(dataRequest);
        } else if (dataRequest.getStatus() == DataRequest.STATUS.terminated || dataRequest.getStatus() == DataRequest.STATUS.nodata) {
            instance.removeDataRequest(dataRequest);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        String statusMessage = dataRequest.getStatusMessage();
        response.setContentLength(statusMessage.length());
        PrintWriter writer = response.getWriter();
        writer.println(statusMessage);

    }

    private void sortDownloads(List<DisplayDownload> displays, String orderByCol, String orderByType) {

        final DownloadComparator comparator = new DownloadComparator(orderByCol, orderByType.equals("asc"));
        displays.sort(comparator);

    }

}