package nl.deltares.portal.exception;

import com.liferay.portal.kernel.exception.PortalException;

public class ValidationException extends PortalException {

    public ValidationException(String msg) {
        super(msg);
    }

    public ValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
