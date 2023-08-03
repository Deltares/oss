package nl.deltares.services.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import nl.deltares.portal.utils.DownloadUtils;
import nl.deltares.portal.utils.GeoIpUtils;
import nl.deltares.services.rest.download.DownloadRestService;
import nl.deltares.services.rest.exception.JsonProcessingExceptionMapper;
import nl.deltares.services.rest.exception.LiferayRestExceptionMapper;
import nl.deltares.services.rest.exception.PortalExceptionMapper;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rooij_e
 */
@Component(
        property = {

                JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/download/",
                JaxrsWhiteboardConstants.JAX_RS_NAME + "=Deltares.Rest.Download",
                "auth.verifier.guest.allowed=true",
                "liferay.access.control.disable=true"
        },
        service = Application.class
)

public class DownloadPortalServices extends Application {

    private DownloadUtils downloadUtils;

    @Reference(
            unbind = "-",
            cardinality = ReferenceCardinality.AT_LEAST_ONE
    )
    protected void setDownloadUtils(DownloadUtils downloadUtils){
        if (downloadUtils.isActive()) {
            this.downloadUtils = downloadUtils;
        }
    }

    private GeoIpUtils geoIpUtils;
    @Reference(
            unbind = "-",
            cardinality = ReferenceCardinality.AT_LEAST_ONE
    )
    protected void setGeoIpUtils(GeoIpUtils geoIpUtils) {
        if (geoIpUtils.isActive()){
            this.geoIpUtils = geoIpUtils;
        }
    }
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        /* Classes to wrap any exception into a JSON response */
        classes.add(PortalExceptionMapper.class);
        classes.add(JsonProcessingExceptionMapper.class);
        classes.add(LiferayRestExceptionMapper.class);
        /* add your additional JAX-RS classes here */
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        singletons.add(this);
        singletons.add(getJacksonJsonProvider());
        //Services for FullCalendar
        singletons.add(new DownloadRestService(downloadUtils, geoIpUtils));
        return singletons;
    }

    @GET
    @Path("/")
    public Response test() {
        return Response.ok().entity("Deltares.Rest.Download service is up and running").build();
    }

    private static JacksonJsonProvider getJacksonJsonProvider() {

        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();

        ObjectMapper objectMapper = new ObjectMapper();

        // Prevent serialization of null and empty string values
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        jacksonJsonProvider.setMapper(objectMapper);
        jacksonJsonProvider.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return jacksonJsonProvider;
    }
}