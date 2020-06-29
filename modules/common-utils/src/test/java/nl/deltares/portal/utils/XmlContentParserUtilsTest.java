package nl.deltares.portal.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static nl.deltares.portal.utils.XmlContentParserUtils.parseContent;

public class XmlContentParserUtilsTest {


    @Test
    public void testParseContent() throws IOException, PortalException {
        URL xml = this.getClass().getResource("/data/dsdevent.xml");

        Document document = parseContent("testParseContent", new FileInputStream(xml.getFile()));
        Assert.assertEquals(1, document.getElementsByTagName("root").getLength());

    }

    @Test
    public void testGetOptionalDynamicElementByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentParserUtils.parseContent("testGetDynamicElementByName", new FileInputStream(xml.getFile()));
        Node node = XmlContentParserUtils.getDynamicElementByName(document, "doesNotExist", true);
        Assert.assertNull(node);

        try {
            XmlContentParserUtils.getDynamicElementByName(document, "doesNotExist", false);
            Assert.fail("Expected exception");
        } catch (PortalException e){
            Assert.assertEquals("Node name 'doesNotExist' not found in document!", e.getMessage().trim());
        }


    }

    @Test
    public void testGetDynamicElementByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentParserUtils.parseContent("testGetDynamicElementByName", new FileInputStream(xml.getFile()));
        Node startDay = XmlContentParserUtils.getDynamicElementByName(document, "startTime", false);
        Assert.assertEquals("dynamic-element", startDay.getNodeName());
        Assert.assertEquals("startTime", ((DeferredElementImpl)startDay).getAttribute("name"));
    }

    @Test
    public void testGetOptionalDynamicElementsByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentParserUtils.parseContent("testGetOptionalDynamicElementsByName", new FileInputStream(xml.getFile()));
        NodeList nodes = XmlContentParserUtils.getDynamicElementsByName(document, "doesNotExist");
        Assert.assertNull(nodes);

    }

    @Test
    public void testGetDynamicElementsByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentParserUtils.parseContent("testGetDynamicElementsByName", new FileInputStream(xml.getFile()));
        NodeList nodes = XmlContentParserUtils.getDynamicElementsByName(document, "eventSession");
        Assert.assertEquals(4, nodes.getLength());
    }

    @Test
    public void testGetOptionalDynamicContentByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentParserUtils.parseContent("testGetOptionalDynamicContentByName", new FileInputStream(xml.getFile()));
        String content = XmlContentParserUtils.getDynamicContentByName(document, "doesNotExist", true);
        Assert.assertNull(content);

    }

    @Test
    public void testGetDynamicContentByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentParserUtils.parseContent("testGetDynamicContentByName", new FileInputStream(xml.getFile()));
        String content = XmlContentParserUtils.getDynamicContentByName(document, "startTime", false);
        Assert.assertEquals("2020-06-15", content);

    }

    @Test
    public void testGetDynamicContentsByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentParserUtils.parseContent("testGetDynamicContentsByName", new FileInputStream(xml.getFile()));
        String[] content = XmlContentParserUtils.getDynamicContentsByName(document, "eventSession");
        Assert.assertEquals(4, content.length);
        Assert.assertEquals("{\"classPK\":\"41748\",\"groupId\":\"41296\",\"className\":\"com.liferay.journal.model.JournalArticle\",\"title\":\"Delft FEWS Dinner 2020\",\"titleMap\":\"{\\\"en_US\\\":\\\"Delft FEWS Dinner 2020\\\"}\",\"uuid\":\"0e1a2d52-a15d-c4eb-a7d9-90ee025f1b52\"}", content[0]);

    }

    @Test
    public void testGetDynamicContentsFromChildrenByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/sessionregistration.xml");
        Document document = XmlContentParserUtils.parseContent("testGetDynamicContentsFromChildrenByName", new FileInputStream(xml.getFile()));
        Node parent = XmlContentParserUtils.getDynamicElementByName(document, "start", false);
        Assert.assertNotNull(parent);
        String starttime = XmlContentParserUtils.getDynamicContentByName(parent, "starttime", false);
        Assert.assertEquals("09:00", starttime);

    }

    @Test
    public void testGetDynamicContentsForNode() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/sessionregistration.xml");
        Document document = XmlContentParserUtils.parseContent("testGetDynamicContentsForNode", new FileInputStream(xml.getFile()));
        Node parent = XmlContentParserUtils.getDynamicElementByName(document, "start", false);
        Assert.assertNotNull(parent);
        String date = XmlContentParserUtils.getDynamicContentForNode(parent);
        Assert.assertEquals("2020-06-18", date);

    }

}
