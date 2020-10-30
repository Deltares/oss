package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import nl.deltares.portal.utils.impl.KeycloakUtilsImpl;
import org.w3c.dom.Document;

import java.util.Collections;
import java.util.Map;

public class Expert extends AbsDsdArticle {
    private static final Log LOG = LogFactoryUtil.getLog(Expert.class);
    private boolean storeInParentSite;
    private boolean autoFillMissingValues;
    private String name;
    private String imageUrl;
    private String jobTitle;
    private String company;
    private String email;
    private final KeycloakUtilsImpl keycloakUtils = new KeycloakUtilsImpl();


    public Expert(JournalArticle dsdArticle, DsdParserUtils dsdParserUtils) throws PortalException {
        super(dsdArticle, dsdParserUtils);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();

            this.email = XmlContentUtils.getDynamicContentByName(document, "expertEmailAddress", false);
            this.name =  XmlContentUtils.getDynamicContentByName(document, "expertName", true);
            this.jobTitle =  XmlContentUtils.getDynamicContentByName(document, "expertJobTitle", true);
            this.company =  XmlContentUtils.getDynamicContentByName(document, "expertCompany", true);

            String jsonImage = XmlContentUtils.getDynamicContentByName(document, "expertImage", true);
            if (jsonImage != null) {
                imageUrl = JsonContentUtils.parseImageJson(jsonImage);
            }
            String storeInParentSite = XmlContentUtils.getDynamicContentByName(document, "storeInParentSite", true);
            this.storeInParentSite = Boolean.parseBoolean(storeInParentSite);

            String autoFill = XmlContentUtils.getDynamicContentByName(document, "autoFill", true);
            this.autoFillMissingValues = Boolean.parseBoolean(autoFill);

            if (autoFillMissingValues){
                fillMissingValues();
            }

        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private void fillMissingValues() {
        try {
        if (name == null) {
            User user = UserLocalServiceUtil.getUserByEmailAddress(getCompanyId(), email);
            name = user.getFullName();
        }
        } catch (PortalException e) {
            //
        }

        Map<String, String> userAttributes = getUserAttributes();
        if (jobTitle == null){
            jobTitle = userAttributes.getOrDefault(KeycloakUtils.ATTRIBUTES.jobTitle.name(), "");
        }
        if (company == null) {
            company = userAttributes.getOrDefault(KeycloakUtils.ATTRIBUTES.org_name.name(), "" );
        }
    }

    private User loadImageFromKeycloak(User user){
        if (user == null) return null;
        try {
            byte[] bytes = keycloakUtils.getUserAvatar(email);
            if (bytes != null && bytes.length > 0) {
                LOG.info("Updating avatar for user " + user.getFullName());
                return UserLocalServiceUtil.updatePortrait(user.getUserId(), bytes);
            }
        } catch (Exception e) {
           LOG.warn(String.format("Error updating user avatar for user %s : %s", email, e.getMessage()));
        }
        return user;
    }

    private Map<String, String> getUserAttributes(){
        try {
            return keycloakUtils.getUserAttributes(email);
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Expert.name();
    }

    @Override
    public boolean storeInParentSite() {
        return storeInParentSite;
    }

    @Override
    public String getSmallImageURL(ThemeDisplay themeDisplay) {

        if (imageUrl == null && autoFillMissingValues){
            try {
                User user = UserLocalServiceUtil.getUserByEmailAddress(getCompanyId(), email);
                imageUrl = user != null ? user.getPortraitURL(themeDisplay) : "";
                if (imageUrl == null) {
                    user = loadImageFromKeycloak(user);
                    imageUrl = user != null ? user.getPortraitURL(themeDisplay) : "";
                }
            } catch (PortalException e) {
                //
            }
        }
        if (imageUrl == null) imageUrl = "";
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }
}
