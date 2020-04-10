package nl.deltares.services.rest;

import com.liferay.journal.service.JournalArticleLocalService;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.services.rest.exception.JsonProcessingExceptionMapper;
import nl.deltares.services.rest.exception.LiferayRestExceptionMapper;
import nl.deltares.services.rest.exception.PortalExceptionMapper;
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
        property = {

			JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/public/dsd/",
			JaxrsWhiteboardConstants.JAX_RS_NAME + "=DSD.Rest.Public",
				"oauth2.scopechecker.type=none",
				"auth.verifier.guest.allowed=true",
				"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*"
        },
        service = Application.class
)

public class DsdRestPublicServices extends Application {

    @Reference
    JournalArticleLocalService journalArticleLocalService;

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
    public Set getSingletons() {
        Set singletons = new HashSet();
        singletons.add(this);
        return singletons;
    }

    @GET
    @Path("/")
    public Response test(){
        return Response.ok().entity("DSD.Rest.Public service is up and running").build();
    }

}