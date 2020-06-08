package nl.deltares.services.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import nl.deltares.portal.utils.DsdRegistrationUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.services.rest.exception.JsonProcessingExceptionMapper;
import nl.deltares.services.rest.exception.LiferayRestExceptionMapper;
import nl.deltares.services.rest.exception.PortalExceptionMapper;
import nl.deltares.services.rest.registration.UserRegistrationService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
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
        //todo: DO NOT FORGET TO REMOVE AUTH entries!!!!!
        property = {
			JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/dsd",
			JaxrsWhiteboardConstants.JAX_RS_NAME + "=DSD.Rest",
                "oauth2.scopechecker.type=none",
                "auth.verifier.guest.allowed=true",
                "auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*"
        },
        service = Application.class
)

public class DsdRestServices extends Application {

    @Reference
    DsdRegistrationUtils registrationUtil;

    @Reference
    KeycloakUtils keycloakUtils;

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
        // Services for registration
        singletons.add(new UserRegistrationService(registrationUtil, keycloakUtils));
        return singletons;
    }

    @GET
    @Path("/")
    public Response test(){
        return Response.ok().entity("DSD.Rest service is up and running").build();
    }

    private JacksonJsonProvider getJacksonJsonProvider() {

        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();

        ObjectMapper objectMapper = new ObjectMapper();

        // Prevent serialization of null and empty string values
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        jacksonJsonProvider.setMapper(objectMapper);
        jacksonJsonProvider.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return jacksonJsonProvider;
    }
}