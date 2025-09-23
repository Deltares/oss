package nl.deltares.forms.internal;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.model.RegistrationInfo;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRegistrationDisplayContext {

    private final List<Registration> _registrations = new ArrayList<>();
    private final DsdParserUtils _dsdParserUtils;
    private final ThemeDisplay _themeDisplay;
    private final List<RegistrationInfo> _registrationInfos = new ArrayList<>();
    private final String ids;
    private final String _displayURL;

    public UserRegistrationDisplayContext(HttpServletRequest request, DsdParserUtils dsdParserUtils) throws Exception {

        _dsdParserUtils = dsdParserUtils;

        CPRequestHelper cpRequestHelper = new CPRequestHelper(request);
        _themeDisplay = cpRequestHelper.getThemeDisplay();
        _displayURL = _themeDisplay.getSiteGroup().getDisplayURL(_themeDisplay);
        ids = ParamUtil.getString(request, "ids");
        loadRegistrations(ids);
        loadRegistrationInfos(request, _themeDisplay.getUser());
    }

    private void loadRegistrationInfos(HttpServletRequest request, User user) {

        List<RegistrationInfo> list = (List<RegistrationInfo>) request.getSession().getAttribute("registrationInfos");

        for (Registration registration : _registrations) {
            if (list != null) {
                List<RegistrationInfo> infos = getRegistrationInfos(list, registration);
                if (!infos.isEmpty()) {
                    _registrationInfos.addAll(infos);
                    continue;
                }
            }
            RegistrationInfo registrationInfo = new RegistrationInfo();
            registrationInfo.setRegistrationName(registration.getTitle());
            registrationInfo.setArticleId(registration.getArticleId());
            registrationInfo.setPrice((float) registration.getPrice());
            registrationInfo.setFirstName(user.getFirstName());
            registrationInfo.setLastName(user.getLastName());
            registrationInfo.setEmail(user.getEmailAddress());
            registrationInfo.setSalutation(user.getJobTitle());
            _registrationInfos.add(registrationInfo);

        }
    }

    public String getViewURL(Registration registration){
        return _displayURL + "/-/" + registration.getJournalArticle().getUrlTitle();
    }

    public String getIds() {
        return ids;
    }

    public List<Registration> getRegistrations() {
        return _registrations;
    }

    public ThemeDisplay getThemeDisplay() {
        return _themeDisplay;
    }

    private void loadRegistrations(String ids) throws Exception {
        String[] registrationIds = ids.split(",", -1);
        if (ids.isEmpty()) return;
        for (String registrationId : registrationIds) {
            if (registrationId == null || registrationId.isEmpty()) continue;
            _registrations.add(_dsdParserUtils.getRegistration(
                    _themeDisplay.getScopeGroupId(), registrationId));
        }
    }

    public List<RegistrationInfo> getRegistrationInfos(Registration registration) {
        return getRegistrationInfos(_registrationInfos, registration);
    }
    public List<RegistrationInfo> getRegistrationInfos(List<RegistrationInfo> infos, Registration registration) {
        return infos.stream().filter(
                info -> info.getArticleId().equals(registration.getArticleId())).collect(Collectors.toList());
    }
}
