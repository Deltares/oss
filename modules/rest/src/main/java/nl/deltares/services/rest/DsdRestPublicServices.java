package nl.deltares.services.rest;

import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.services.rest.exception.JsonProcessingExceptionMapper;
import nl.deltares.services.rest.exception.LiferayRestExceptionMapper;
import nl.deltares.services.rest.exception.PortalExceptionMapper;
import nl.deltares.services.rest.fullcalendar.DsdFullcalendarService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.jakarta.rs.json.JacksonJsonProvider;

import java.util.HashSet;
import java.util.Set;

/**
 * @author rooij_e
 */
@Component(
        property = {

			JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/public/dsd/",
			JaxrsWhiteboardConstants.JAX_RS_NAME + "=DSD.Rest.Public",
				"auth.verifier.guest.allowed=true",
				"liferay.access.control.disable=true"
        },
        service = Application.class
)

public class DsdRestPublicServices extends Application {

    @Reference
    JournalArticleLocalService journalArticleLocalService;

    @Reference
    KeycloakUtils keycloakUtils;

    @Reference
    DsdParserUtils parserUtils;

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
        singletons.add(new DsdFullcalendarService(_configurationProvider, parserUtils));
        return singletons;
    }

    @GET
    @Path("/")
    public Response test(){
        return Response.ok().entity("DSD.Rest.Public service is up and running").build();
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static JacksonJsonProvider getJacksonJsonProvider() {

        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();

        JsonMapper build = JsonMapper.builder().build();
        jacksonJsonProvider.setMapper(build);
        jacksonJsonProvider.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return jacksonJsonProvider;
    }
}