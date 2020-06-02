package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.exception.SystemException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class JournalArticleUtils {

    static String ARTICLE_ROOT= "/root";
    static String ARTICLE_DYNAMIC_ELEMENT= "/dynamic-element";
    static String ARTICLE_NAME_ATTRIBUTE_START= "[@name='";

    static String ARTICLE_CONTENT_XML_NODE_START= "/root/dynamic-element";
    static String ARTICLE_CONTENT_XML_NODE_END= "']/dynamic-content";
    static String ARTICLE_STATUS= "STATUS";
    static String ARTICLE_ACTIVE= "Active";

    public static Document parseContent(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        return  parseContent(new ByteArrayInputStream(xmlString.getBytes()));
    }

    public static Document parseContent(InputStream xmlInputStream) throws ParserConfigurationException, IOException, SAXException {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = factory.newDocumentBuilder();
        //Parse the content to Document object
        return builder.parse(xmlInputStream);

    }

    public static String getNodeValue(Document xmlDocument, String nodeName) throws IOException {

        String[] searchLevels = {
                ARTICLE_ROOT + ARTICLE_DYNAMIC_ELEMENT + ARTICLE_NAME_ATTRIBUTE_START + nodeName + ARTICLE_CONTENT_XML_NODE_END,
                ARTICLE_ROOT + ARTICLE_DYNAMIC_ELEMENT + ARTICLE_DYNAMIC_ELEMENT + ARTICLE_NAME_ATTRIBUTE_START + nodeName + ARTICLE_CONTENT_XML_NODE_END
        };
        Node node = null;

        for (int i = 0; i < searchLevels.length; i++) {
            String expression = searchLevels[i];
            try {
                node = getNode(xmlDocument, expression);
                if (node != null) return node.getTextContent();
            } catch (XPathExpressionException e) {
                //ignore
            }
        }
        throw new IOException(String.format("Node name '%s' not found in document! ", nodeName));

    }

    public static Node getNode(Document xmlDocument, String expression) throws SystemException, XPathExpressionException, IOException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
        int length;
        if (( length = nodeList.getLength()) < 2) {
            return length == 1 ? nodeList.item(0) : null;
        } else {
            throw new IOException(String.format("Expected single node value for '%s' but found '%d'!", expression, length));
        }

    }
}
