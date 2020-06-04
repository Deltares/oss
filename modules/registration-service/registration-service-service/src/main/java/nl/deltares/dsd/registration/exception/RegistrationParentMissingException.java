package nl.deltares.dsd.registration.exception;

public class RegistrationParentMissingException extends ValidationException {

    public RegistrationParentMissingException(String msg) {
        super(msg);
    }

    public RegistrationParentMissingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
