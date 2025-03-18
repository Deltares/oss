package nl.deltares.forms.internal;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateModel;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.model.RegistrationInfo;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DDMStructureUtil;
import nl.deltares.portal.utils.DsdParserUtils;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

public class CartOverviewDisplayContext {

    private final List<Registration> _registrations = new ArrayList<>();
    private final DsdParserUtils _dsdParserUtils;
    private final HttpServletRequest _httpServletRequest;
    private final ThemeDisplay _themeDisplay;
    private final DDMStructureUtil _ddmStructureUtil;
    private final String _ddmTemplateKey;
    private final Map<String, Integer> registrationQuantities = new HashMap<>();
    private final List<RegistrationInfo> _registrationInfos = new ArrayList<>();
    private final RegistrationInfo _defaultRegistrationInfo;
    private final String ids;
    private final String _displayURL;

    public CartOverviewDisplayContext(HttpServletRequest request, DsdParserUtils dsdParserUtils, DDMStructureUtil ddmStructureUtil) throws Exception {

        _dsdParserUtils = dsdParserUtils;
        _httpServletRequest = request;
        _ddmStructureUtil = ddmStructureUtil;

        CPRequestHelper cpRequestHelper = new CPRequestHelper(_httpServletRequest);

        _themeDisplay = cpRequestHelper.getThemeDisplay();
        _displayURL = _themeDisplay.getSiteGroup().getDisplayURL(_themeDisplay);

        ids = ParamUtil.getString(request, "ids");
        loadRegistrations(ids);

        List<RegistrationInfo> list = (List<RegistrationInfo>) request.getSession().getAttribute("registrationInfos");
        if (list != null) _registrationInfos.addAll(list);

        Optional<DDMTemplate> ddmTemplateOptional = _ddmStructureUtil
                .getDDMTemplateByName(_themeDisplay.getScopeGroupId(), "REGISTRATION", _themeDisplay.getLocale());

        _defaultRegistrationInfo = getDefaultRegistrationInfo(_themeDisplay.getUser());
        _ddmTemplateKey = ddmTemplateOptional.map(DDMTemplateModel::getTemplateKey).orElse(null);
    }

    private RegistrationInfo getDefaultRegistrationInfo(User user) {
        RegistrationInfo registrationInfo = new RegistrationInfo();
        registrationInfo.setEmail(user.getEmailAddress());
        registrationInfo.setFirstName(user.getFirstName());
        registrationInfo.setLastName(user.getLastName());
        return registrationInfo;
    }

    public String getIds() {return ids;}
    public List<Registration> getRegistrations() {
        return _registrations;
    }

    public List<RegistrationInfo> getRegistrationInfos(Registration registration){

        if (_registrationInfos.isEmpty()) {
            return Collections.singletonList(_defaultRegistrationInfo);
        }
        return  _registrationInfos.stream().filter(registrationInfo ->
                registrationInfo.getRegistrationId().equals(registration.getArticleId())).collect(Collectors.toList());
    }

    public String getViewURL(Registration registration){
        return _displayURL + "/-/" + registration.getJournalArticle().getUrlTitle();
    }

    public ThemeDisplay getThemeDisplay(){
        return _themeDisplay;
    }

    public Registration getRegistration(String articleId){
        Optional<Registration> first = _registrations.stream().filter(registration -> registration.getArticleId().equals(articleId)).findFirst();
        return first.orElse(null);
    }

    public JournalArticleDisplay getArticleDisplay(PortletRequest portletRequest, PortletResponse portletResponse,
        JournalArticle article) throws PortalException {

        return JournalArticleLocalServiceUtil.getArticleDisplay(
                _themeDisplay.getScopeGroupId(), article.getArticleId(), _ddmTemplateKey == null ? article.getDDMTemplateKey() : _ddmTemplateKey
                , "VIEW",
                _themeDisplay.getLanguageId(), 1, new PortletRequestModel(portletRequest, portletResponse),
                _themeDisplay);
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

}
