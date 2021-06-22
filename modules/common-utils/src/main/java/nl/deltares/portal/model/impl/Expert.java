package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

import java.util.Locale;

public class Expert extends AbsDsdArticle {
    private boolean storeInParentSite;
    private String name;
    private String imageUrl;
    private String jobTitle;
    private String company;
    private String email;

    public Expert(JournalArticle dsdArticle, DsdParserUtils dsdParserUtils, Locale locale) throws PortalException {
        super(dsdArticle, dsdParserUtils, locale);
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

        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
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

        if (imageUrl == null){
            try {
                User user = UserLocalServiceUtil.getUserByEmailAddress(getCompanyId(), email);
                imageUrl = user != null ? user.getPortraitURL(themeDisplay) : "";
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
