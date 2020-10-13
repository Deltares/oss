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

import static nl.deltares.portal.utils.XmlContentUtils.parseContent;

public class XmlContentUtilsTest {

    @Test
    public void testParseContent() throws IOException, PortalException {
        URL xml = this.getClass().getResource("/data/dsdevent.xml");

        Document document = parseContent("testParseContent", new FileInputStream(xml.getFile()));
        Assert.assertEquals(1, document.getElementsByTagName("root").getLength());

    }

    @Test
    public void testGetOptionalDynamicElementByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentUtils.parseContent("testGetDynamicElementByName", new FileInputStream(xml.getFile()));
        Node node = XmlContentUtils.getDynamicElementByName(document, "doesNotExist", true);
        Assert.assertNull(node);

        try {
            XmlContentUtils.getDynamicElementByName(document, "doesNotExist", false);
            Assert.fail("Expected exception");
        } catch (PortalException e){
            Assert.assertEquals("Node name 'doesNotExist' not found in document!", e.getMessage().trim());
        }


    }

    @Test
    public void testGetDynamicElementByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentUtils.parseContent("testGetDynamicElementByName", new FileInputStream(xml.getFile()));
        Node startDay = XmlContentUtils.getDynamicElementByName(document, "starttime", false);
        Assert.assertEquals("dynamic-element", startDay.getNodeName());
        Assert.assertEquals("starttime", ((DeferredElementImpl)startDay).getAttribute("name"));
    }

    @Test
    public void testGetOptionalDynamicElementsByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentUtils.parseContent("testGetOptionalDynamicElementsByName", new FileInputStream(xml.getFile()));
        NodeList nodes = XmlContentUtils.getDynamicElementsByName(document, "doesNotExist");
        Assert.assertNull(nodes);

    }

    @Test
    public void testGetDynamicElementsByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentUtils.parseContent("testGetDynamicElementsByName", new FileInputStream(xml.getFile()));
        NodeList nodes = XmlContentUtils.getDynamicElementsByName(document, "eventLocation");
        Assert.assertEquals(1, nodes.getLength());
    }

    @Test
    public void testGetOptionalDynamicContentByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentUtils.parseContent("testGetOptionalDynamicContentByName", new FileInputStream(xml.getFile()));
        String content = XmlContentUtils.getDynamicContentByName(document, "doesNotExist", true);
        Assert.assertNull(content);

    }

    @Test
    public void testGetDynamicContentByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentUtils.parseContent("testGetDynamicContentByName", new FileInputStream(xml.getFile()));
        String content = XmlContentUtils.getDynamicContentByName(document, "start", false);
        Assert.assertEquals("2020-07-06", content);

    }

    @Test
    public void testGetDynamicContentsByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/dsdevent.xml");
        Document document = XmlContentUtils.parseContent("testGetDynamicContentsByName", new FileInputStream(xml.getFile()));
        String[] content = XmlContentUtils.getDynamicContentsByName(document, "eventLocation");
        Assert.assertEquals(1, content.length);
        Assert.assertEquals("{\"className\":\"com.liferay.journal.model.JournalArticle\",\"classPK\":\"80868\",\"title\":\"Deltares\",\"titleMap\":\"{\\\"en_US\\\":\\\"Deltares\\\"}\"}", content[0]);

    }

    @Test
    public void testGetDynamicContentsFromChildrenByName() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/sessionregistration.xml");
        Document document = XmlContentUtils.parseContent("testGetDynamicContentsFromChildrenByName", new FileInputStream(xml.getFile()));
        Node parent = XmlContentUtils.getDynamicElementByName(document, "start", false);
        Assert.assertNotNull(parent);
        String starttime = XmlContentUtils.getDynamicContentByName(parent, "starttime", false);
        Assert.assertEquals("09:00", starttime);

    }

    @Test
    public void testGetDynamicContentsForNode() throws FileNotFoundException, PortalException {

        URL xml = this.getClass().getResource("/data/sessionregistration.xml");
        Document document = XmlContentUtils.parseContent("testGetDynamicContentsForNode", new FileInputStream(xml.getFile()));
        Node parent = XmlContentUtils.getDynamicElementByName(document, "start", false);
        Assert.assertNotNull(parent);
        String date = XmlContentUtils.getDynamicContentForNode(parent);
        Assert.assertEquals("2020-06-18", date);

    }

}
