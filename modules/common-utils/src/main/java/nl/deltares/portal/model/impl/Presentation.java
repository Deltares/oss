package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;

import java.util.Locale;

public class Presentation  extends AbsDsdArticle{

    private String presenterName = "";
    private String presenterOrg = "";
    private String presentationLink = "";
    private String documentLink = "";
    private String thumbnailLink = "";
    private String presentationType = "";

    public Presentation(JournalArticle article, DsdParserUtils dsdParserUtils, Locale locale) throws PortalException {
        super(article, dsdParserUtils, locale);
        init();
    }

    private void init() throws PortalException {
        try {
            String presenterName = getFormFieldValue("presenterName", true);
            if (presenterName != null) this.presenterName = presenterName;
            String presenterOrg = getFormFieldValue( "presenterOrganization", true);
            if (presenterOrg != null) this.presenterOrg = presenterOrg;
            final String presentationLink = getFormFieldValue( "presentationLink", true);
            if (presentationLink != null) this.presentationLink = presentationLink;

            String typeJson = getFormFieldValue( "presentationType", true);
            if (typeJson != null) presentationType = typeJson;

            String docJson = getFormFieldValue( "document", true);
            if (docJson != null) documentLink = JsonContentUtils.parseDocumentJson(docJson);

            String imgUrl = getFormFieldValue( "thumbnailURL", true);
            if (imgUrl != null) thumbnailLink = imgUrl;
            if (thumbnailLink.isEmpty()) {
                String imgJson = getFormFieldValue( "thumbnailImage", true);
                if (imgJson != null) thumbnailLink = JsonContentUtils.parseImageJson(imgJson);
            }

        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    public boolean isSlideLink() {
        return presentationType != null && presentationType.equals("slides"); }

    public boolean isVideoLink(){
        return presentationType != null && presentationType.equals("video");
    }

    public boolean isDownloadLink(){
        return documentLink != null && !documentLink.isEmpty();
    }

    public String getPresenter() {
        return presenterName;
    }

    public String getOrganization() {
        return presenterOrg;
    }

    public String getPresentationLink() {
        return presentationLink != null ? presentationLink : documentLink;
    }

    public String getThumbnailLink() {return thumbnailLink;}

}
