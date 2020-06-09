package nl.deltares.dsd.registration.service.impl;


import com.liferay.portal.kernel.exception.PortalException;
import nl.deltares.portal.utils.XmlContentParserUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import static nl.deltares.portal.utils.XmlContentParserUtils.parseContent;

public class JournalArticleUtilsTest {


    @Test
    public void testParseContent() throws IOException, PortalException {
        URL expertXml = this.getClass().getResource("/data/ExpertUser.xml");

        Document document = parseContent("testParseContent", new FileInputStream(expertXml.getFile()));
        Assert.assertEquals(1, document.getElementsByTagName("root").getLength());

    }

    @Test
    public void testGetNodeValue() throws IOException, PortalException {
        URL expertXml = this.getClass().getResource("/data/ExpertUser.xml");

        Document document = parseContent("testGetNodeValue", new FileInputStream(expertXml.getFile()));

        Assert.assertEquals(
                "{\"classPK\":\"52262\",\"groupId\":\"52076\",\"name\":\"sam.jpg\",\"alt\":\"Erik INT\",\"title\":\"sam.jpg\",\"type\":\"journal\",\"uuid\":\"75bccfc6-2d24-26eb-84e1-46ca05fa50fa\",\"fileEntryId\":\"52270\",\"resourcePrimKey\":\"52268\"}",
                XmlContentParserUtils.getNodeValue(document, "ExpertImage", true));
        Assert.assertEquals("Erik INT", XmlContentParserUtils.getNodeValue(document, "ExpertName", false));

        Assert.assertEquals("Deltares", XmlContentParserUtils.getNodeValue(document, "ExpertCompany", false));

        Assert.assertEquals(false, XmlContentParserUtils.getNodeValue(document, "AutoFill", true));
    }
}
