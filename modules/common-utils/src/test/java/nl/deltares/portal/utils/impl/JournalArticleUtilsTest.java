package nl.deltares.portal.utils.impl;


import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class JournalArticleUtilsTest {


    @Test
    public void testParseContent() throws ParserConfigurationException, SAXException, IOException {
        URL expertXml = this.getClass().getResource("/data/ExpertUser.xml");

        Document document = JournalArticleUtils.parseContent(new FileInputStream(expertXml.getFile()));
        Assert.assertEquals(1, document.getElementsByTagName("root").getLength());

    }

    @Test
    public void testGetNodeValue() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        URL expertXml = this.getClass().getResource("/data/ExpertUser.xml");

        Document document = JournalArticleUtils.parseContent(new FileInputStream(expertXml.getFile()));

        Assert.assertEquals("Erik INT", JournalArticleUtils.getNodeValue(document, "ExpertName"));

        Assert.assertEquals("Deltares", JournalArticleUtils.getNodeValue(document, "ExpertCompany"));

        Assert.assertEquals("", JournalArticleUtils.getNodeValue(document, "AutoFill"));
    }
}
