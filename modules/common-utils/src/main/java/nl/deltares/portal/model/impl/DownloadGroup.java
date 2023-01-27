package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.LayoutUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import nl.deltares.portal.utils.impl.DsdParserUtilsImpl;
import org.w3c.dom.Document;

import java.util.Locale;


public class DownloadGroup extends AbsDsdArticle {

    private String name = "";
    private String imageUrl = "";
    private Layout groupPage;
    private String description = "";

    public DownloadGroup(JournalArticle journalArticle, DsdParserUtilsImpl dsdParserUtils, LayoutUtils layoutUtils, Locale locale) throws PortalException {
        super(journalArticle, dsdParserUtils, locale);
        init(layoutUtils);
    }

    private void init(LayoutUtils layoutUtils) throws PortalException {
        try {
            Document document = getDocument();
            name = XmlContentUtils.getDynamicContentByName(document, "Name", false);
            String linkToPage = XmlContentUtils.getDynamicContentByName(document, "GroupPage", false);
            groupPage = layoutUtils.getLinkToPageLayout(linkToPage);
            String jsonImage = XmlContentUtils.getDynamicContentByName(document, "Icon", false);
            if (jsonImage != null) {
                imageUrl = JsonContentUtils.parseImageJson(jsonImage);
            }
            String desc = XmlContentUtils.getDynamicContentByName(document, "Description", true);
            if (desc != null) {
                description = desc;
            }

        } catch (PortalException e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    public String getName() {
        return name;
    }

    public String getGroupPage(ThemeDisplay themeDisplay) throws PortalException {
        try {
            final String layoutFriendlyURL = PortalUtil.getLayoutFriendlyURL(groupPage, themeDisplay);
            return layoutFriendlyURL == null ? groupPage.getFriendlyURL() : layoutFriendlyURL;
        } catch (PortalException e) {
            throw new PortalException(String.format("Error getting FriendlyUrl for group page %s: %s!", groupPage.getTitle(), e.getMessage()), e);
        }

    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getSmallImageURL(ThemeDisplay themeDisplay) {
        String smallImageURL = super.getSmallImageURL(themeDisplay);
        if (smallImageURL != null && !smallImageURL.trim().isEmpty()) {
            return smallImageURL;
        }
        return imageUrl;
    }
}
