package nl.deltares.forms.internal;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.model.AccountInfo;
import nl.deltares.model.RegistrationFormContext;
import nl.deltares.model.RegistrationInfo;
import nl.deltares.model.RegistrationsInfo;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdSessionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UserInputValidationContext {

    private final DsdSessionUtils _sessionUtils;
    private final UserLocalService _userLocalService;
    private final String _validEmailDomains;
    private final RegistrationsInfo _registrationsInfo;

    public UserInputValidationContext(HttpServletRequest request, DsdSessionUtils sessionUtils,
                                      DsdParserUtils dsdParserUtils, UserLocalService userLocalService) {
        _sessionUtils = sessionUtils;
        _userLocalService = userLocalService;

        RegistrationFormContext context = (RegistrationFormContext) request.getSession().getAttribute("registration-context");
        if (context == null) {
            context = new RegistrationFormContext();
            request.getSession().setAttribute("registration-context", context);
        }
        RegistrationsInfo registrationsInfo = context.getRegistrationsInfo();
        if (registrationsInfo == null) {
            _registrationsInfo = new RegistrationsInfo();
            context.setRegistrationsInfo(_registrationsInfo);
        } else {
            _registrationsInfo = registrationsInfo;
        }

        CPRequestHelper cpRequestHelper = new CPRequestHelper(request);
        ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();
        RegistrationsInfo.loadRegistrations(request, _registrationsInfo, dsdParserUtils, themeDisplay);

        AccountInfo accountInfo = context.getAccountInfo();
        if (accountInfo != null) {
            AccountEntry selectedAccountEntry = accountInfo.getSelectedAccountEntry();
            _validEmailDomains = selectedAccountEntry == null ? "" : selectedAccountEntry.getDomains();
        } else {
            _validEmailDomains = "";
        }

    }

    public void storeUserRegistrationInfos(HttpServletRequest request) {

        for (Registration registration : _registrationsInfo.getRegistrations()) {

            String articleId = registration.getArticleId();

            long parentResourceId = 0;
            String parentTitle = null;
            if (registration.hasParent()) {
                Registration parentRegistration = registration.getParentRegistration();
                parentResourceId = parentRegistration.getResourceId();
                parentTitle = parentRegistration.getTitle();
            }

            int rowCount = ParamUtil.getNumber(request, "count_registration_" + articleId).intValue();
            ArrayList<RegistrationInfo> userRegistrations = new ArrayList<>();
            String POST_FIX;
            for (int i = 0; i < rowCount; i++) {
                final RegistrationInfo registrationInfo = new RegistrationInfo();
                registrationInfo.setTitle(registration.getTitle());
                registrationInfo.setArticleId(articleId);
                registrationInfo.setResourceId(registration.getResourceId());
                registrationInfo.setPrice((float) registration.getPrice());
                registrationInfo.setParentResourceId(parentResourceId);
                registrationInfo.setParentTitle(parentTitle);

                POST_FIX = i == 0 ? "" : "_" + i;
                registrationInfo.setSalutation(ParamUtil.getString(request, "salutation_" + articleId + POST_FIX));
                registrationInfo.setFirstName(ParamUtil.getString(request, "firstName_" + articleId + POST_FIX));
                registrationInfo.setLastName(ParamUtil.getString(request, "lastName_" + articleId + POST_FIX));
                registrationInfo.setRemarks(ParamUtil.getString(request, "remarks_" + articleId + POST_FIX));
                String email = ParamUtil.getString(request, "email_" + articleId + POST_FIX);
                registrationInfo.setEmail(email);

                registration.getStartAndEndTimesPerDay().forEach(registrationInfo::addPeriod);

                userRegistrations.add(registrationInfo);
            }
            _registrationsInfo.setUserRegistrations(articleId, userRegistrations);
        }
    }

    public void validateRequestData(HttpServletRequest request)  {

        boolean addIfExceptions = false;
        List<RegistrationFormException> exceptions;
        if (SessionErrors.contains(request, RegistrationFormException.class)) {
            exceptions = (List<RegistrationFormException>) SessionErrors.get(request, RegistrationFormException.class);
        } else {
            addIfExceptions = true;
            exceptions = new ArrayList<>();
        }
        HashMap<User, List<String>> overlappingRegistrations = new HashMap<>();
        List<RegistrationInfo> allUserRegistrations = _registrationsInfo.getAllUserRegistrations();
        long companyId = _registrationsInfo.getRegistrationsCompanyId();
        long groupId = _registrationsInfo.getRegistrationsGroupId();
        for (RegistrationInfo info : allUserRegistrations) {
            String email = info.getEmail();
            if (!Validator.isEmailAddress(email)) {
                exceptions.add(new RegistrationFormException(String.format("Invalid email address '%s'!", email)));
                continue;
            }
            String domain = email.split("@")[1];
            if (!_validEmailDomains.isEmpty() && !_validEmailDomains.contains(domain)) {
                exceptions.add(new RegistrationFormException(String.format("Invalid email domain for user '%s'. Required one of the following email domains '%s'", email, _validEmailDomains)));
                continue;
            }

            User user = _userLocalService.fetchUserByEmailAddress(companyId, email);
            if (user != null) {
                //Check if user is already registered for this registration
                if (_sessionUtils.isUserRegisteredFor(groupId, user.getUserId(), info.getResourceId())) {
                    exceptions.add(new RegistrationFormException(String.format("User '%s' is already registered for '%s'", user.getEmailAddress(), info.getTitle())));
                    continue;
                }
                //Check for registrations with overlapping periods
                List<String> overlapping = overlappingRegistrations.putIfAbsent(user, new ArrayList<>());
                if (overlapping == null)
                    overlapping = overlappingRegistrations.get(user);
                if (!overlapping.contains(info.getTitle())) {
                    //Check for overlapping registrations in the current submission.
                    List<String> collect = allUserRegistrations.stream()
                            .filter(registrationInfo ->
                                    registrationInfo.getEmail().equals(email) &&
                                            registrationInfo.getResourceId() != info.getResourceId()
                                            && registrationInfo.isAnyTimeCommon(info.getPeriods()))
                            .map(RegistrationInfo::getTitle)
                            .collect(Collectors.toList());
                    overlapping.addAll(collect);

                    //Check for overlapping registrations in the database.
                    List<String> overlappingRegistrationTitles = _sessionUtils.getOverlappingRegistrationTitles(
                            groupId, user.getUserId(), info.getResourceId(), info.getPeriods());
                    overlapping.addAll(overlappingRegistrationTitles);
                    if (!overlapping.isEmpty()) {
                        overlapping.add(info.getTitle());
                        exceptions.add(new RegistrationFormException(
                                String.format("User '%s' has registered for overlapping sessions: '%s'.", email, String.join(", ", overlapping))));
                    }
                }

            }
            //Check if required parent registration is selected for this child registration
            if (info.isChildRelation()) {
                if (allUserRegistrations.stream()
                        .anyMatch(registrationInfo ->
                                registrationInfo.getResourceId() == info.getParentResourceId() &&
                                        registrationInfo.getEmail().equals(email))) continue;

                if (user == null || !_sessionUtils.isUserRegisteredFor(groupId, user.getUserId(), info.getParentResourceId())) {
                    exceptions.add(new RegistrationFormException(
                            String.format("User '%s' wishes to register for '%s' but has not selected required parent registration '%s'",
                                    email, info.getTitle(), info.getParentTitle())));
                }
            }
        }

        if (addIfExceptions && !exceptions.isEmpty()) {
            SessionErrors.add(request, RegistrationFormException.class, exceptions);
        }
    }

}
