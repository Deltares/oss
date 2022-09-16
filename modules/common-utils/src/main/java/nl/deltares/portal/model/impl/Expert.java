package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import java.util.Locale;

public class Expert extends AbsDsdArticle {
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
            this.email = getFormFieldValue( "expertEmailAddress", false);
            this.name =  getFormFieldValue( "expertName", true);
            this.jobTitle =  getFormFieldValue( "expertJobTitle", true);
            this.company =  getFormFieldValue( "expertCompany", true);

            String jsonImage = getFormFieldValue( "expertImage", true);
            if (jsonImage != null) {
                imageUrl = JsonContentUtils.parseImageJson(jsonImage);
            }

        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Expert.name();
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
