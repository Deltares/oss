package nl.deltares.dsd.registration.exception;

public class RegistrationOverlappingPeriodException extends ValidationException{
    public RegistrationOverlappingPeriodException(String msg) {
        super(msg);
    }

    public RegistrationOverlappingPeriodException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
