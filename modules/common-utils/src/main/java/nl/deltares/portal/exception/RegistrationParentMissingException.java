package nl.deltares.portal.exception;

public class RegistrationParentMissingException extends ValidationException {

    public RegistrationParentMissingException(String msg) {
        super(msg);
    }

    public RegistrationParentMissingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
