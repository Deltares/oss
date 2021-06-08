package nl.deltares.portal.utils;

import nl.deltares.portal.model.impl.Registration;

public interface WebinarUtilsFactory {

    WebinarUtils newInstance(Registration registration) throws Exception;

    boolean isWebinarSupported(Registration registration);
}
