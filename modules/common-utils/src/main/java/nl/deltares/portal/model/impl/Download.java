package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import nl.deltares.portal.utils.*;

import java.util.*;

public class Download extends AbsDsdArticle {

    private static final Log LOG = LogFactoryUtil.getLog(Download.class);

    private int fileId = Integer.MIN_VALUE;
    private String fileSize = "";
    private String filePath;
    private String fileName;
    private String fileType;
    private String fileTopic;
    private String fileTypeName;
    private String fileTopicName;
    private Layout groupPage;
    private List<Subscription> subscriptions = null;
    private boolean automaticLinkCreation = false;
    private Terms terms = null;
    private LicenseFile licenseFile = null;
    private boolean shareLink;

    enum ACTION {direct, userinfo, billinginfo, locks, licenses}

    private final List<ACTION> requiredActions = new ArrayList<>();

    public Download(JournalArticle journalArticle, DsdParserUtils articleParserUtils, DsdJournalArticleUtils dsdJournalArticleUtils, LayoutUtils layoutUtils, Locale locale) throws PortalException {
        super(journalArticle, articleParserUtils, locale);
        init(dsdJournalArticleUtils, layoutUtils);
    }

    private void init(DsdJournalArticleUtils articleUtils, LayoutUtils layoutUtils) throws PortalException {
        try {
            String fileId = getFormFieldValue( "FileId", true);
            if (fileId != null) this.fileId = Integer.parseInt(fileId);
            filePath = getFormFieldValue( "FilePath", false);
            fileName = getFormFieldValue( "FileName", false);
            if (filePath != null){
                shareLink = filePath.toLowerCase().startsWith("https://");
            }
            final List<String> actions = getFormFieldArrayValue("RequiredActions", true);
            actions.forEach(a -> {
                try {
                    if (a == null || a.trim().isEmpty()) return;
                    requiredActions.add(ACTION.valueOf(a));
                } catch (IllegalArgumentException e) {
                    LOG.warn(String.format("Invalid 'RequiredActions' %s found for download %s", a, getTitle()));
                }
            });

            fileType = getFormFieldValue( "FileType", false);

            final Map<String, String> fileTypesMap = articleUtils.getStructureFieldOptions(getGroupId(), getStructureKey(), "FileType", getLocale());
            fileTypeName = fileTypesMap.get(fileType);

            String fileSize = getFormFieldValue( "FileSize", true);
            if (fileSize != null) this.fileSize = fileSize;

            fileTopic = getFormFieldValue( "Topic", false);
            final Map<String, String> fileTopicMap = articleUtils.getStructureFieldOptions(getGroupId(), getStructureKey(), "Topic", getLocale());
            fileTopicName = fileTopicMap.get(fileTopic);

            String linkToPage = getFormFieldValue( "GroupPage", false);
            groupPage = layoutUtils.getLinkToPageLayout(linkToPage);

            final String automaticLinkCreation = getFormFieldValue( "AutomaticLinkCreation", true);
            if (automaticLinkCreation != null) {
                this.automaticLinkCreation = Boolean.parseBoolean(automaticLinkCreation);
            }
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing content for article %s: %s!", getTitle(), e.getMessage()), e);
        }
    }

    public Terms getTerms(){
        loadTerms();
        return terms;
    }

    private void loadTerms() {
        if(terms != null) return;
        try {
            parseTerms();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing terms for Download %s: %s", getTitle(), e.getMessage()));
        }
    }

    private void parseTerms() throws PortalException {
        String content = getFormFieldValue( "Terms", true);
        if (content != null){
            JournalArticle article = JsonContentUtils.jsonReferenceToJournalArticle(content);
            AbsDsdArticle dsdArticle = dsdParserUtils.toDsdArticle(article, super.getLocale());
            if (!(dsdArticle instanceof Terms)) throw new PortalException(String.format("Article %s not instance of Terms", article.getTitle()));
            terms = (Terms) dsdArticle;
        }
    }

    public List<Subscription> getSubscriptions() {
        loadSubscriptions();
        return Collections.unmodifiableList(subscriptions);
    }

    private void loadSubscriptions(){
        if (subscriptions != null) return;
        try {
            parseSubscriptions();
        } catch (PortalException e) {
            LOG.error(String.format("Error parsing subscriptions for Download %s: %s", getTitle(), e.getMessage()));
        }
    }

    private void parseSubscriptions() throws PortalException {
        subscriptions = new ArrayList<>();
        List<String> subscriptionsJson = getFormFieldValues("Subscription", true);
        DuplicateCheck check = new DuplicateCheck();
        if (!subscriptionsJson.isEmpty()){
            for (String content : subscriptionsJson) {
                JournalArticle article = JsonContentUtils.jsonReferenceToJournalArticle(content);
                AbsDsdArticle subscription = dsdParserUtils.toDsdArticle(article, super.getLocale());
                if (!(subscription instanceof Subscription)) throw new PortalException(String.format("Article %s not instance of Subscription", article.getTitle()));
                if (check.checkDuplicates(subscription)) subscriptions.add((Subscription) subscription);
            }
        }
    }

    public LicenseFile getLicenseFile(){
        loadLicenseFile();
        return licenseFile;
    }

    private void loadLicenseFile() {
        if (licenseFile != null) return;
        try {
            parseLicenseFile();
        } catch (PortalException e){
            LOG.error(String.format("Error parsing licenseFile for Download %s: %s", getTitle(), e.getMessage()));
        }
    }

    private void parseLicenseFile() throws PortalException {
        String content = getFormFieldValue( "LicenseFile", true);
        if (!JsonContentUtils.isEmpty(content)){
            JournalArticle article = JsonContentUtils.jsonReferenceToJournalArticle(content);
            AbsDsdArticle dsdArticle = dsdParserUtils.toDsdArticle(article, super.getLocale());
            if (!(dsdArticle instanceof LicenseFile)) throw new PortalException(String.format("Article %s not instance of LicenseFile", article.getTitle()));
            licenseFile = (LicenseFile) dsdArticle;
        }
    }
    @Override
    public String getStructureKey() {
        return "download";
    }

    public boolean isDirectDownload() {
        return requiredActions.contains(ACTION.direct);
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

    public String getFileTopicName() {
        return fileTopicName;
    }

    public boolean isShareLink() {
        return shareLink;
    }
    public String getGroupPage(ThemeDisplay themeDisplay) throws PortalException {
        try {
            final String layoutFriendlyURL = PortalUtil.getLayoutFriendlyURL(groupPage, themeDisplay);
            return layoutFriendlyURL == null ? groupPage.getFriendlyURL() : layoutFriendlyURL;
        } catch (PortalException e) {
            throw new PortalException(String.format("Error getting FriendlyUrl for group page %s: %s!", groupPage.getTitle(), e.getMessage()), e);
        }
    }

    public boolean isBillingRequired() {
        return requiredActions.contains(ACTION.billinginfo);
    }

    public boolean isAutomaticLinkCreation(){
        return automaticLinkCreation;
    }

    public boolean isShowSubscription() {
        return !getSubscriptions().isEmpty();
    }

    public boolean isUserInfoRequired() {
        return requiredActions.contains(ACTION.userinfo);
    }

    public boolean isTermsOfUseRequired() {
        return getTerms() != null;
    }

    public boolean isLockTypeRequired() {
        return requiredActions.contains(ACTION.locks);
    }

    public boolean isLicenseTypeRequired() {
        return requiredActions.contains(ACTION.licenses);
    }

}
