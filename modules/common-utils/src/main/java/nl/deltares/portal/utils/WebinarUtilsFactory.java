package nl.deltares.portal.utils;

import nl.deltares.portal.model.impl.Registration;

public interface WebinarUtilsFactory {

    WebinarUtils newInstance(Registration registration);

    boolean isWebinarSupported(Registration registration);
}
