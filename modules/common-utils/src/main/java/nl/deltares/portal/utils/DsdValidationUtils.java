package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import nl.deltares.dsd.registration.service.RegistrationLocalServiceUtil;
import nl.deltares.portal.exception.*;
import nl.deltares.portal.model.impl.AbstractRegistration;
import org.w3c.dom.Document;

public class DsdValidationUtils {

    private static final Log LOG = LogFactoryUtil.getLog(DsdValidationUtils.class);

    /**
     * Validate if the capacity of this Session article matches the room capacity
     *
     * @param article       Article being validated
     * @param parsedContent Optional pre-parsed content (for performance).
     * @throws ValidationException Throws validation exception of session capacity exceeds room capacity.
     */
    public static void validateSessionCapacityMatchesRoomCapacity(JournalArticle article, Document parsedContent) throws PortalException {

        if (parsedContent == null) {
            parsedContent = XmlContentParserUtils.parseContent(article);
        }

        Integer sessionCapacity = (Integer) XmlContentParserUtils.getNodeValue(parsedContent, "capacity", false);
        String roomJson = (String) XmlContentParserUtils.getNodeValue(parsedContent, "room", false);

        JournalArticle roomArticle = JsonContentParserUtils.jsonReferenceToJournalArticle(roomJson);
        if (roomArticle == null) {
            throw new PortalException(String.format("Could not find room for session %s: %s", article.getArticleId(), roomJson));
        }

        Document roomDocument = XmlContentParserUtils.parseContent(roomArticle);
        Integer roomCapacity = (Integer) XmlContentParserUtils.getNodeValue(roomDocument, "capacity", false);

        if (roomCapacity == null || sessionCapacity == null || roomCapacity < sessionCapacity) {
            throw new ValidationException(String.format("The require capacity %d for session %s, exceeds the capacity %d of room '%s'",
                    sessionCapacity, article.getTitle(), roomCapacity, roomArticle.getTitle()));
        }
    }

    /**
     * Validate if a user can register of a particular registration.
     * @param groupId   Site Identifier
     * @param articleId Registration Identifier
     * @param userId    User Identifier
     * @throws PortalException Throws various ValidationExceptions whenever a validation check fails.
     */
    public void validateRegistration(long groupId, long articleId, long userId) throws PortalException {
        JournalArticle article = JournalArticleLocalServiceUtil.getLatestArticle(groupId, String.valueOf(articleId));

        AbstractRegistration registration = AbstractRegistration.getInstance(article);

        //check if article is open for registration
        validateOpenForRegistration(registration);

        //check if room limit is exceeded
        validateRegistrationCapacity(registration);

        //check if period overlaps
        validateRegistrationPeriod(registration, userId);

        //check if user is registered for required parent registrations
        validateParentChildRelation(registration, userId);

    }

    /**
     * Validate if current registration overlaps with any of users other registrations.
     * @param registration Registration being validated
     * @param userId User registering.
     * @throws RegistrationPeriodOverlapException Thrown if user is already registerd for registration with overlapping period.
     */
    private void validateRegistrationPeriod(AbstractRegistration registration, long userId) throws RegistrationPeriodOverlapException {

        long[] overlappingRegistrations = RegistrationLocalServiceUtil.getRegistrationsWithOverlappingPeriod(registration.getGroupId(), userId, registration.getStartTime(), registration.getEndTime());
        StringBuilder sb = new StringBuilder();
        for (long articleId : overlappingRegistrations) {
            sb.append(getUrlTitle(registration.getGroupId(), articleId));
            sb.append(',');
        }
        throw new RegistrationPeriodOverlapException(String.format("Period of registration '%s' overlaps with existing user registrations: [%s]", registration.getArticleId(), sb.toString().trim()));
    }

    /**
     * Validate if the number of registrations for current Registration exceeds the maximum capacity.
     * @param registration Registration for which to check capacity
     * @throws RegistrationFullException Thrown if capacity is exceeded.
     */
    private void validateRegistrationCapacity(AbstractRegistration registration) throws RegistrationFullException {

        int capacity = registration.getCapacity();
        int registrationCount = RegistrationLocalServiceUtil.getRegistrationsCount(registration.getGroupId(), registration.getArticleId());
        if (registrationCount < capacity) return;

        throw new RegistrationFullException(String.format("Capacity '%d' of registration '%s' has been reached!", registrationCount, registration.getArticleId()));

    }

    /**
     * Validate if the Registration is open for registering.
     * @param registration Registration being validated
     * @throws RegistrationClosedException Thrown if Registration is still closed.
     */
    private void validateOpenForRegistration(AbstractRegistration registration) throws RegistrationClosedException {
        if (!registration.isOpen()) {
            throw new RegistrationClosedException(String.format("Registration '%s' is closed !", registration.getArticleId()));
        }
    }

    /**
     * Validate if parent - child relation is being honored. User can only register for a Registration with a parentRegistrationId
     * assigned if user is already registered for the parent Registration.
     *
     * @param registration Registration for which to check parent registration
     * @param userId    User trying to register.
     * @throws RegistrationParentMissingException Thrown if user is not registered to parent registration.
     */
    private void validateParentChildRelation(AbstractRegistration registration, long userId) throws ValidationException {
        if (!registration.hasParentRegistration()) return;

        long parentRegistrationId = registration.getParentRegistrationId();
        int parentRegistrationsCount = RegistrationLocalServiceUtil.getParentRegistrationsCount(registration.getGroupId(), userId, parentRegistrationId);
        if (parentRegistrationsCount > 0) return; //user is already registered for parent

        String childName = getUrlTitle(registration.getGroupId(), registration.getArticleId());
        String parentName = getUrlTitle(registration.getGroupId(), parentRegistrationId);

        throw new RegistrationParentMissingException(String.format("Required parent registration '%s' is missing for '%s!", parentName, childName));

    }

    private String getUrlTitle(long groupId, long articleId) {
        try {
            JournalArticle article = JournalArticleLocalServiceUtil.getLatestArticle(groupId, String.valueOf(articleId));
            return article.getUrlTitle();
        } catch (PortalException e) {
            LOG.warn(String.format("Error getting latest article for '%s': %s", articleId, e.getMessage()));
        }
        return String.valueOf(articleId);
    }
}
