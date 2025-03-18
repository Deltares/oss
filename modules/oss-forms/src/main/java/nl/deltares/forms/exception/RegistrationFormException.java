package nl.deltares.forms.exception;

import com.liferay.portal.kernel.exception.ModelListenerException;

public class RegistrationFormException extends ModelListenerException {

    public RegistrationFormException(String msg) {
        super(msg);
    }

    public RegistrationFormException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
