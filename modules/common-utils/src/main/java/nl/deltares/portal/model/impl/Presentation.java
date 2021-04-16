package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

public class Presentation  extends AbsDsdArticle{

    private String presenterName = "";
    private String presenterOrg = "";
    private String presentationLink;
    private String documentLink = null;
    private String thumbnailLink = null;

    public Presentation(JournalArticle article, DsdParserUtils dsdParserUtils) throws PortalException {
        super(article, dsdParserUtils);
        init();
    }

    private void init() throws PortalException {
        try {
            Document document = getDocument();
            String presenterName = XmlContentUtils.getDynamicContentByName(document, "presenterName", true);
            if (presenterName != null) this.presenterName = presenterName;
            String presenterOrg = XmlContentUtils.getDynamicContentByName(document, "presenterOrganization", true);
            if (presenterOrg != null) this.presenterOrg = presenterOrg;
            presentationLink = XmlContentUtils.getDynamicContentByName(document, "presentationLink", true);

            String docJson = XmlContentUtils.getDynamicContentByName(document, "document", true);
            documentLink = JsonContentUtils.parseDocumentJson(docJson);

            String imgJson = XmlContentUtils.getDynamicContentByName(document, "thumbnailImage", true);
            thumbnailLink = JsonContentUtils.parseImageJson(imgJson);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    public boolean isVideoLink(){
        return presentationLink != null;
    }

    public boolean isDownloadLink(){
        return documentLink != null;
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
