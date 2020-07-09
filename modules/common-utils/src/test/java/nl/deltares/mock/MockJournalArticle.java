package nl.deltares.mock;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleWrapper;
import sun.misc.IOUtils;

import java.io.*;

public class MockJournalArticle extends JournalArticleWrapper {
    private long resourcePk;
    private String title;
    private String content;
    private String structureKey;
    private long groupId = 1000;

    public MockJournalArticle() {
        super(null);
    }

    public static JournalArticle getInstance(String title, String structureKey, long resourcePk, InputStream resourceAsStream) throws IOException {
        byte[] content = IOUtils.readFully(resourceAsStream, -1, true);
        MockJournalArticle article = new MockJournalArticle();
        article.setContent(new String(content));
        article.setTitle(title);
        article.setStructureKey(structureKey);
        article.setResourcePrimKey(resourcePk);

        return article;
    }

    public static JournalArticle getInstance(File resource) throws IOException {

        String nameWithExtenstion = resource.getName();
        String[] tokens = nameWithExtenstion.substring(0, nameWithExtenstion.indexOf(".xml")).split("#");
        assert tokens.length == 3;

        return getInstance(tokens[0], tokens[1], Long.parseLong(tokens[2]), new FileInputStream(resource));
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getDDMStructureKey(){
        return this.structureKey;
    }

    public void setStructureKey(String structureKey) {
        this.structureKey = structureKey;
    }

    @Override
    public long getResourcePrimKey() {
        return this.resourcePk;
    }

    public void setResourcePrimKey(long resourcePk) {
        this.resourcePk = resourcePk;
    }

    @Override
    public long getGroupId() {
        return groupId;
    }

    @Override
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
