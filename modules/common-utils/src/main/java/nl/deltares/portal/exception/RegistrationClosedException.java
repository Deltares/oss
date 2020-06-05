package nl.deltares.portal.exception;

public class RegistrationClosedException extends ValidationException {
    public RegistrationClosedException(String msg) {
        super(msg);
    }

    public RegistrationClosedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
