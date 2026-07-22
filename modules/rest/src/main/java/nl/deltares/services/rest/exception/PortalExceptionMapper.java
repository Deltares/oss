package nl.deltares.services.rest.exception;

import com.liferay.portal.kernel.exception.PortalException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class PortalExceptionMapper  implements ExceptionMapper<PortalException> {

    @Override
    public Response toResponse(PortalException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorMessage(exception))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
