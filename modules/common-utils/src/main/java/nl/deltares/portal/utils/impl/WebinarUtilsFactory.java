package nl.deltares.portal.utils.impl;

import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.WebinarUtils;

public class WebinarUtilsFactory {

    public static WebinarUtils newInstance(Registration registration){
        if (!isWebinarSupported(registration)){
            throw new UnsupportedOperationException("unsupported registration type " + registration.getClass().getSimpleName());
        }
        String webinarProvider = ((SessionRegistration) registration).getWebinarProvider();
        return newInstance(webinarProvider);
    }

    public static WebinarUtils newInstance(String provider){

        final String providerKey = provider.toLowerCase();
        switch (providerKey){
            case "goto": return new GotoUtils();
            case "anewspring" : return new ANewSpringUtils();
            case "msteams" : return new MSTeamsUtils();
            default:
                throw new UnsupportedOperationException("unsupported provider " + provider);
        }
    }

    public static boolean isWebinarSupported(Registration registration){

        if (registration instanceof SessionRegistration){
            String webinarProvider = ((SessionRegistration) registration).getWebinarProvider();
            switch (webinarProvider){
                case "goto":
                case "anewspring":
                case "msteams":
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }
}
