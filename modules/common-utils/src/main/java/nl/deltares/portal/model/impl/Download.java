package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.display.context.DownloadDisplayContext;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Download extends AbsDsdArticle {

    private int fileId = Integer.MIN_VALUE;
    private String fileSize = "";
    private String filePath;
    private String fileName;
    private String fileType;
    private String fileTopic;
    private String fileTypeName;

    enum ACTION {direct, terms, userInfo, billingInfo, subscriptions}

    private final List<ACTION> requiredActions = new ArrayList<>();

    public Download(JournalArticle journalArticle, DsdParserUtils articleParserUtils, DsdJournalArticleUtils dsdJournalArticleUtils, Locale locale) throws PortalException {
        super(journalArticle, articleParserUtils, locale);
        init(dsdJournalArticleUtils);
    }

    private void init(DsdJournalArticleUtils articleUtils) throws PortalException {
        try {
            Document document = getDocument();
            String fileId = XmlContentUtils.getDynamicContentByName(document, "FileId", true);
            if (fileId != null) this.fileId = Integer.parseInt(fileId);
            filePath = XmlContentUtils.getDynamicContentByName(document, "FilePath", false);
            fileName = XmlContentUtils.getDynamicContentByName(document, "FileName", false);

            String options = XmlContentUtils.getDynamicContentByName(document, "RequiredActions", false);
            parseRequiredActions(options);

            fileType = XmlContentUtils.getDynamicContentByName(document, "FileType", false);

            final Map<String, String> fileTypesMap = articleUtils.getStructureFieldOptions(getGroupId(), getStructureKey(), "FileType", getLocale());
            fileTypeName = fileTypesMap.get(fileType);

            String fileSize = XmlContentUtils.getDynamicContentByName(document, "FileSize", true);
            if (fileSize != null) this.fileSize = fileSize;

            fileTopic = XmlContentUtils.getDynamicContentByName(document, "Topic", false);

        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    private void parseRequiredActions(String options) {
        options = options.trim();
        options = options.replace('\n', ' ');
        final String[] actionList = options.split(" ");
        for (String action : actionList) {
            try {
                requiredActions.add(ACTION.valueOf(action.trim()));
            } catch (IllegalArgumentException e) {
                //skip
            }
        }
    }

    @Override
    public String getStructureKey() {
        return "download";
    }

    public boolean isDirectDownload() {
        return requiredActions.contains(ACTION.direct) && fileId > 0;
    }

    public boolean isSendLink() {
        return requiredActions.contains(ACTION.direct);
    }

    public int getFileId() {
        return fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getFileTopic() {
        return fileTopic;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public DownloadDisplayContext toDisplayContext(ThemeDisplay themeDisplay) {
        return new DownloadDisplayContext(this, dsdParserUtils, themeDisplay);
    }
}
