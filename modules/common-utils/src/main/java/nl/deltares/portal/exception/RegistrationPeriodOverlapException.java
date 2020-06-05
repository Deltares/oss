package nl.deltares.portal.exception;

public class RegistrationPeriodOverlapException extends ValidationException {

    public RegistrationPeriodOverlapException(String msg) {
        super(msg);
    }

    public RegistrationPeriodOverlapException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
