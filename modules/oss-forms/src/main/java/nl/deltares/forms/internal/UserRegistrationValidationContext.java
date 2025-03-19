package nl.deltares.forms.internal;

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
import java.util.List;
import java.util.Optional;

public class UserRegistrationValidationContext {

    private final DsdSessionUtils _sessionUtils;
    private final UserLocalService _userLocalService;
    private final ThemeDisplay _themeDisplay;
    private final DsdParserUtils _dsdParserUtils;
    private boolean _registrationsLoaded = false;
    private boolean _registrationInfosLoaded = false;
    private final List<Registration> _registrations = new ArrayList<>();
    private final List<RegistrationInfo> _registrationInformation = new ArrayList<>();

    public UserRegistrationValidationContext(HttpServletRequest httpServletRequest, DsdSessionUtils sessionUtils,
                                             DsdParserUtils parserUtils,
                                             UserLocalService userLocalService) throws Exception {
        _sessionUtils = sessionUtils;
        _dsdParserUtils = parserUtils;
        _userLocalService = userLocalService;

        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        _themeDisplay = cpRequestHelper.getThemeDisplay();
    }

    public void validateRequestData(HttpServletRequest request) throws Exception {

        if(!_registrationInfosLoaded){
            loadRegistrationInfos(request);
        }
        boolean addIfExceptions = false;
        List<RegistrationFormException> exceptions;
        if (SessionErrors.contains(request, RegistrationFormException.class)) {
            exceptions = (List<RegistrationFormException>) SessionErrors.get(request, RegistrationFormException.class);
        } else {
            addIfExceptions = true;
            exceptions = new ArrayList<>();
        }
        long companyId = _themeDisplay.getCompanyId();
        for (RegistrationInfo info : _registrationInformation) {
            boolean error = false;
            if (!Validator.isEmailAddress(info.getEmail())) {
                exceptions.add(new RegistrationFormException(String.format("Invalid email '%s' for registration '%s'", info.getEmail(), info.getRegistrationTitle())));
                error = true;
            }
            if (Validator.isBlank(info.getFirstName()) || Validator.isBlank(info.getLastName())) {
                exceptions.add(new RegistrationFormException(String.format("Missing First- or Last Name for registration '%s'", info.getRegistrationTitle())));
                error = true;
            }
            if (error) continue;

            String email = info.getEmail();
            User user = _userLocalService.fetchUserByEmailAddress(companyId, email);
            if (user == null) continue;
            _sessionUtils.isUserRegisteredFor(user, getRegistration(info.getRegistrationId()));
        }

        if (addIfExceptions && !exceptions.isEmpty()) {
            SessionErrors.add(request, RegistrationFormException.class, exceptions);
        }
    }

    public void loadRegistrationInfos(HttpServletRequest request) throws Exception {
        if (_registrationInfosLoaded){
            throw new IllegalStateException("Registration Infos already loaded!");
        }
        if (!_registrationsLoaded){
            String ids = ParamUtil.getString(request, "ids");
            loadRegistrations(ids);
        }
        for (Registration registration : _registrations) {

            String articleId = registration.getArticleId();
            int rowCount = ParamUtil.getNumber(request, "count_registration_" + articleId).intValue();
            String POST_FIX;
            for (int i = 0; i < rowCount; i++) {
                final RegistrationInfo registrationInfo = new RegistrationInfo();
                registrationInfo.setRegistrationName(registration.getTitle());
                registrationInfo.setArticleId(articleId);
                registrationInfo.setBillingInfoRequired(registration.getPrice() > 0);

                POST_FIX = i == 0 ? "" : "_" + i;
                registrationInfo.setSalutation(ParamUtil.getString(request, "salutation_" + articleId + POST_FIX));
                registrationInfo.setFirstName(ParamUtil.getString(request, "firstName_" + articleId + POST_FIX));
                registrationInfo.setLastName(ParamUtil.getString(request, "lastName_" + articleId + POST_FIX));
                registrationInfo.setRemarks(ParamUtil.getString(request, "remarks_" + articleId + POST_FIX));
                String email = ParamUtil.getString(request, "email_" + articleId + POST_FIX);
                registrationInfo.setEmail(email);
                _registrationInformation.add(registrationInfo);
            }
        }
        _registrationInfosLoaded = true;
    }

    public Registration getRegistration(String articleId){
        Optional<Registration> first = _registrations.stream().filter(registration -> registration.getArticleId().equals(articleId)).findFirst();
        return first.orElse(null);
    }

    public void loadRegistrations(String ids) throws Exception {
        if (_registrationsLoaded){
            throw new IllegalStateException("Registrations already loaded!");
        }
        String[] registrationIds = ids.split(",", -1);
        if (ids.isEmpty()) return;
        for (String registrationId : registrationIds) {
            _registrations.add(_dsdParserUtils.getRegistration(
                    _themeDisplay.getScopeGroupId(), registrationId));
        }
        _registrationsLoaded = true;
    }

    public List<RegistrationInfo> getRegistrationInformation() {
        if (!_registrationInfosLoaded)
            throw new IllegalStateException("Registration Infos not loaded!");
        return _registrationInformation;
    }
}
