package nl.deltares.forms.internal;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.model.RegistrationInfo;
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
    private final ThemeDisplay _themeDisplay;
    private final DsdParserUtils _dsdParserUtils;
    private final HttpServletRequest _httpServletRequest;
    private final String _validEmailDomains;
    private final List<Registration> _registrations = new ArrayList<>();
    private final List<RegistrationInfo> _registrationInformation = new ArrayList<>();

    public UserInputValidationContext(HttpServletRequest httpServletRequest, DsdSessionUtils sessionUtils,
                                      DsdParserUtils parserUtils, UserLocalService userLocalService,
                                      AccountEntryLocalService accountEntryLocalService) throws Exception {
        _sessionUtils = sessionUtils;
        _dsdParserUtils = parserUtils;
        _userLocalService = userLocalService;

        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        _themeDisplay = cpRequestHelper.getThemeDisplay();
        _httpServletRequest = httpServletRequest;

        Object selectedAccountEntryId = httpServletRequest.getSession().getAttribute("selectedAccountEntryId");
        if (selectedAccountEntryId != null) {
            AccountEntry _selectedAccountEntry = accountEntryLocalService.getAccountEntry((Long) selectedAccountEntryId);
            _validEmailDomains = _selectedAccountEntry.getDomains();
        } else {
            _validEmailDomains = "";
        }
    }

    public void validateRequestData() throws Exception {

        loadRegistrationInfos(_httpServletRequest);
        boolean addIfExceptions = false;
        List<RegistrationFormException> exceptions;
        if (SessionErrors.contains(_httpServletRequest, RegistrationFormException.class)) {
            exceptions = (List<RegistrationFormException>) SessionErrors.get(_httpServletRequest, RegistrationFormException.class);
        } else {
            addIfExceptions = true;
            exceptions = new ArrayList<>();
        }
        HashMap<User, List<String>> overlappingRegistrations = new HashMap<>();
        long companyId = _themeDisplay.getCompanyId();
        for (RegistrationInfo info : _registrationInformation) {
            String email = info.getEmail();
            if (!Validator.isEmailAddress(email)){
                exceptions.add(new RegistrationFormException(String.format("Invalid email address '%s'!", email)));
                continue;
            }
            String domain = email.split("@")[1];
            if (!_validEmailDomains.isEmpty() && !_validEmailDomains.contains(domain)){
                exceptions.add(new RegistrationFormException(String.format("Invalid email domain for user '%s'. Required one of the following email domains '%s'", email, _validEmailDomains)));
                continue;
            }

            User user = _userLocalService.fetchUserByEmailAddress(companyId, email);
            if (user != null) {
                //Check if user is already registered for this registration
                if (_sessionUtils.isUserRegisteredFor(_themeDisplay.getSiteGroupId(), user.getUserId(), info.getResourceId())){
                    exceptions.add(new RegistrationFormException(String.format("User '%s' is already registered for '%s'", user.getEmailAddress(), info.getTitle())));
                    continue;
                }
                //Check for registrations with overlapping periods
                List<String> overlapping = overlappingRegistrations.putIfAbsent(user, new ArrayList<>());
                if (overlapping == null)
                    overlapping = overlappingRegistrations.get(user);
                if (!overlapping.contains(info.getTitle())) {
                    //Check for overlapping registrations in the current submission.
                    List<String> collect = _registrationInformation.stream()
                            .filter(registrationInfo ->
                                    registrationInfo.getEmail().equals(email) &&
                                            registrationInfo.getResourceId() != info.getResourceId()
                                            && registrationInfo.isAnyTimeCommon(info.getPeriods()))
                            .map(RegistrationInfo::getTitle)
                            .collect(Collectors.toList());
                    overlapping.addAll(collect);

                    //Check for overlapping registrations in the database.
                    List<String> overlappingRegistrationTitles = _sessionUtils.getOverlappingRegistrationTitles(
                            _themeDisplay.getSiteGroupId(), user.getUserId(), info.getResourceId(), info.getPeriods());
                    overlapping.addAll(overlappingRegistrationTitles);
                    if (!overlapping.isEmpty()){
                        overlapping.add(info.getTitle());
                        exceptions.add(new RegistrationFormException(
                                String.format("User '%s' has registered for overlapping sessions: '%s'.", email, String.join(", ", overlapping))));
                    }
                }

            }
            //Check if required parent registration is selected for this child registration
            if (info.isChildRelation()){
                if (_registrationInformation.stream()
                        .anyMatch(registrationInfo ->
                                registrationInfo.getResourceId() == info.getParentResourceId() &&
                                registrationInfo.getEmail().equals(email))) continue;

                if (user == null || !_sessionUtils.isUserRegisteredFor(_themeDisplay.getSiteGroupId(), user.getUserId(), info.getParentResourceId())){
                    exceptions.add(new RegistrationFormException(
                            String.format("User '%s' wishes to register for '%s' but has not selected required parent registration '%s'",
                                    email, info.getTitle(), info.getParentTitle())));
                }
            }
        }

        if (addIfExceptions && !exceptions.isEmpty()) {
            SessionErrors.add(_httpServletRequest, RegistrationFormException.class, exceptions);
        }
    }

    public void loadRegistrationInfos(HttpServletRequest request) throws Exception {
        String ids = ParamUtil.getString(request, "ids");
        loadRegistrations(ids);

        for (Registration registration : _registrations) {

            String articleId = registration.getArticleId();
            long parentResourceId = 0;
            String parentTitle = null;
            if (registration.hasParent()){
                Registration parentRegistration = registration.getParentRegistration();
                parentResourceId = parentRegistration.getResourceId();
                parentTitle = parentRegistration.getTitle();
            }

            int rowCount = ParamUtil.getNumber(request, "count_registration_" + articleId).intValue();
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

                _registrationInformation.add(registrationInfo);
            }
        }
    }

    public void loadRegistrations(String ids) throws Exception {
        String[] registrationIds = ids.split(",", -1);
        for (String registrationId : registrationIds) {
            if (registrationId.isEmpty()) continue;
            _registrations.add(_dsdParserUtils.getRegistration(
                    _themeDisplay.getScopeGroupId(), registrationId));
        }
    }

    public List<RegistrationInfo> getRegistrationInformation() {
        return _registrationInformation;
    }

}
