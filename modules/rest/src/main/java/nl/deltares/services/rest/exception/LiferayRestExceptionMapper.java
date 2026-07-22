package nl.deltares.services.rest.exception;


import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LiferayRestExceptionMapper  implements ExceptionMapper<LiferayRestException> {

    @Override
    public Response toResponse(LiferayRestException exception) {

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorMessage(exception))
                .type(MediaType.APPLICATION_JSON)
                .build();

    }

}
