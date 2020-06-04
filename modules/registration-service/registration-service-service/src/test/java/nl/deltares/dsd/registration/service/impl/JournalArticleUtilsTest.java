package nl.deltares.dsd.registration.service.impl;


import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.XmlContentParserUtils;
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

        Document document = XmlContentParserUtils.parseContent(new FileInputStream(expertXml.getFile()));
        Assert.assertEquals(1, document.getElementsByTagName("root").getLength());

    }

    @Test
    public void testGetNodeValue() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException, PortalException {
        URL expertXml = this.getClass().getResource("/data/ExpertUser.xml");

        Document document = XmlContentParserUtils.parseContent(new FileInputStream(expertXml.getFile()));

        Assert.assertEquals(
                "{\"classPK\":\"52262\",\"groupId\":\"52076\",\"name\":\"sam.jpg\",\"alt\":\"Erik INT\",\"title\":\"sam.jpg\",\"type\":\"journal\",\"uuid\":\"75bccfc6-2d24-26eb-84e1-46ca05fa50fa\",\"fileEntryId\":\"52270\",\"resourcePrimKey\":\"52268\"}",
                XmlContentParserUtils.getNodeValue(document, "ExpertImage"));
        Assert.assertEquals("Erik INT", XmlContentParserUtils.getNodeValue(document, "ExpertName"));

        Assert.assertEquals("Deltares", XmlContentParserUtils.getNodeValue(document, "ExpertCompany"));

        Assert.assertEquals(false, XmlContentParserUtils.getNodeValue(document, "AutoFill"));
    }
}
