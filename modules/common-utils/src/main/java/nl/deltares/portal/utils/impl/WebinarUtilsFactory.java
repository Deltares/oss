package nl.deltares.portal.utils.impl;

import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.WebinarUtils;

import java.util.HashMap;
import java.util.Map;

public class WebinarUtilsFactory {

    private static Map<String, WebinarUtils> cache = new HashMap<>();

    public static WebinarUtils newInstance(Registration registration){
        if (!isWebinarSupported(registration)){
            throw new UnsupportedOperationException("unsupported registration type " + registration.getClass().getSimpleName());
        }
        String webinarProvider = ((SessionRegistration) registration).getWebinarProvider();
        return newInstance(webinarProvider);
    }

    public static WebinarUtils newInstance(String provider){

        final String providerKey = provider.toLowerCase();

        WebinarUtils webinarUtils = cache.get(providerKey);
        if ( webinarUtils != null) return webinarUtils;

        switch (providerKey){
            case "goto":
                webinarUtils = new GotoUtils();
                break;
            case "anewspring" :
                webinarUtils = new ANewSpringUtils();
                break;
            case "msteams" :
                webinarUtils = new MSTeamsUtils();
                break;
            default:
                throw new UnsupportedOperationException("unsupported provider " + provider);
        }
        cache.put(providerKey, webinarUtils);
        return webinarUtils;
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
