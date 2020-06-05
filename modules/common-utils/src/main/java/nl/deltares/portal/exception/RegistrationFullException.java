package nl.deltares.portal.exception;

public class RegistrationFullException extends ValidationException {
    public RegistrationFullException(String msg) {
        super(msg);
    }

    public RegistrationFullException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
