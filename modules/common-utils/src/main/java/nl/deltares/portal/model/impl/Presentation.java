package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

import java.util.Locale;

public class Presentation  extends AbsDsdArticle{

    private String presenterName = "";
    private String presenterOrg = "";
    private String presentationLink = "";
    private String documentLink = "";
    private String thumbnailLink = "";
    private String presentationType = "video";

    public Presentation(JournalArticle article, DsdParserUtils dsdParserUtils, Locale locale) throws PortalException {
        super(article, dsdParserUtils, locale);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            String presenterName = XmlContentUtils.getDynamicContentByName(document, "presenterName", true);
            if (presenterName != null) this.presenterName = presenterName;
            String presenterOrg = XmlContentUtils.getDynamicContentByName(document, "presenterOrganization", true);
            if (presenterOrg != null) this.presenterOrg = presenterOrg;
            final String presentationLink = XmlContentUtils.getDynamicContentByName(document, "presentationLink", true);
            if (presentationLink != null) this.presentationLink = presentationLink;

            String typeJson = XmlContentUtils.getDynamicContentByName(document, "presentationType", true);
            if (typeJson != null) presentationType = JsonContentUtils.parseDocumentJson(typeJson);

            String docJson = XmlContentUtils.getDynamicContentByName(document, "document", true);
            if (docJson != null) documentLink = JsonContentUtils.parseDocumentJson(docJson);

            String imgJson = XmlContentUtils.getDynamicContentByName(document, "thumbnailImage", true);
            if (imgJson != null) thumbnailLink = JsonContentUtils.parseImageJson(imgJson);

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
