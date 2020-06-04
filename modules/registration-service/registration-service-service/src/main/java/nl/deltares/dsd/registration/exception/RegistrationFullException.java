package nl.deltares.dsd.registration.exception;

public class RegistrationFullException extends ValidationException {
    public RegistrationFullException(String msg) {
        super(msg);
    }

    public RegistrationFullException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
