package nl.deltares.portal.utils.impl;

import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.service.*;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdSessionUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        service = DsdSessionUtils.class
)
public class RegistrationUtilsImpl implements DsdSessionUtils {

    @Override
    public void deleteRegistrationRecord(long registrationId) throws PortalException {
        _objectEntryLocalService.deleteObjectEntry(registrationId);
    }

    @Override
    public int getRegistrationCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Map<String, Object>> getRegistrations(int start, int end) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUserJoinLink(User user, Registration registration, boolean isRegistered) throws Exception {

        _objectFieldLocalService.getTable()
        ObjectDefinition objectDefinition = _objectDefinitionLocalService.fetchObjectDefinitionByExternalReferenceCode("Registration_Table", user.getCompanyId());
        objectDefinition.getDBTableName();
        _objectFieldLocalService.getTable(objectDefinition.getObjectDefinitionId(), objectDefinition.getDBTableName());

        .getTable(objectDefinition.getT)
        return "";
    }

    @Override
    public String getUserJoinLink(User user, Registration registration) throws Exception {
        return "";
    }

    @Override
    public void registerUser(User user, Map<String, String> userAttributes, Registration registration, Map<String, String> registrationProperties, User registeredBy) throws PortalException {

    }

    @Override
    public void registerUser(User user, Registration registration, Map<String, String> registrationProperties, User registrationUser) throws PortalException {

    }

    @Override
    public void unRegisterUser(User user, Registration registration) throws PortalException {

    }

    @Override
    public int getRegistrationCount(Registration registration) {
        return 0;
    }

    @Override
    public List<Registration> getChildRegistrations(Registration registration) throws PortalException {
        return List.of();
    }

    @Override
    public Map<String, String> getUserPreferences(User user, Registration registration) throws PortalException {
        return Map.of();
    }

    @Override
    public boolean isUserRegisteredFor(User user, Registration registration) {
        return false;
    }

    @Override
    public void validateRegistrations(User user, List<Registration> registrations, List<Registration> childRegistrations) throws PortalException {

    }

    @Override
    public void deleteRegistrationsFor(Registration registration) {

    }

    @Override
    public void deleteRegistrationsFor(long groupId, long resourceId) throws PortalException {

    }

    @Override
    public List<Map<String, Object>> getUserRegistrations(User user, long groupId) {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getUserRegistrationsMadeForOthers(User user, long groupId) {
        return List.of();
    }

    @Override
    public boolean hasUserRegistrationsMadeForOthers(User user, long groupId, long eventArticleId) {
        return false;
    }

    @Override
    public List<Map<String, Object>> getRegistrations(Event event) {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getRegistrations(long groupId, long resourceId) {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getRegistrations(long groupId, Date startDate, Date endDate) {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getEventRegistrations(long groupId, long eventResourceId) {
        return List.of();
    }

    @Override
    public void deleteEventRegistrations(long groupId, long resourceId) {

    }

    @Reference
    private ObjectEntryLocalService _objectEntryLocalService;
    @Reference
    private ObjectDefinitionLocalService _objectDefinitionLocalService;
    @Reference
    private ObjectFieldLocalService _objectFieldLocalService;

}
