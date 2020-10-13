package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.impl.Registration;

import java.util.Map;

public interface GotoUtils {

    boolean isActive();

    Map<String, String> registerUser(User user, String webinarKey, String callerId) throws Exception;

    Map<String, String> getRegistration(User user, String webinarKey) throws Exception;

    int unregisterUser(String registrantsKey, String webinarKey) throws Exception;

    boolean isGotoMeeting(Registration registration);
}
