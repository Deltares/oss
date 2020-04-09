package nl.deltares.services.rest.exception;

import com.liferay.portal.kernel.exception.PortalException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

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
