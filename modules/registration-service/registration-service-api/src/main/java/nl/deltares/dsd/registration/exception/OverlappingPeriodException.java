package nl.deltares.dsd.registration.exception;

public class OverlappingPeriodException extends ValidationException {

    public OverlappingPeriodException(String msg) {
        super(msg);
    }

    public OverlappingPeriodException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
