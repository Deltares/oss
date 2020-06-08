package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import java.text.ParseException;

public class XmlContentParserUtils {

    private static final Log LOG = LogFactoryUtil.getLog(XmlContentParserUtils.class);
    static String ARTICLE_ROOT= "/root";
    static String ARTICLE_DYNAMIC_ELEMENT= "/dynamic-element";
    static String ARTICLE_NAME_ATTRIBUTE_START= "[@name='";
    static String ARTICLE_NAME_ATTRIBUTE_END= "']";
    static String ARTICLE_CONTENT_XML_NODE_END= "/dynamic-content";

    public static Document parseContent(JournalArticle article) throws PortalException{
        return parseContent(article.getTitle(), article.getContent());
    }

    public static Document parseContent(String siteName, String xmlString) throws PortalException {
        return  parseContent(siteName, new ByteArrayInputStream(xmlString.getBytes()));
    }

    public static Document parseContent(String siteName, InputStream xmlInputStream) throws PortalException {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            //API to obtain DOM Document instance
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Parse the content to Document object
            return builder.parse(xmlInputStream);
        } catch (ParserConfigurationException | SAXException | IOException e){
            throw new PortalException(String.format("Could not parse content for site %s: %s", siteName, e));
        }

    }

    public static Object getNodeValue(Document xmlDocument, String nodeName, boolean optional) throws PortalException {

        String[] searchLevels = {
                ARTICLE_ROOT + ARTICLE_DYNAMIC_ELEMENT + ARTICLE_NAME_ATTRIBUTE_START + nodeName + ARTICLE_NAME_ATTRIBUTE_END + ARTICLE_CONTENT_XML_NODE_END,
                ARTICLE_ROOT + ARTICLE_DYNAMIC_ELEMENT + ARTICLE_DYNAMIC_ELEMENT + ARTICLE_NAME_ATTRIBUTE_START + nodeName + ARTICLE_NAME_ATTRIBUTE_END + ARTICLE_CONTENT_XML_NODE_END
        };
        Node node;

        for (String expression : searchLevels) {
            try {
                node = getNode(xmlDocument, expression);
                if (node != null) {
                    try {
                        return getNodeValue(node);
                    } catch (ParseException e) {
                        LOG.error("Error parsing node value: " + e.getMessage());
                    }
                }
            } catch (XPathExpressionException e) {
                LOG.error(String.format("Error retrieving node value %s from content: %s", nodeName, e.getMessage()));
            }
        }
        if (optional) return null;
        throw new PortalException(String.format("Node name '%s' not found in document! ", nodeName));

    }

    private static Object getNodeValue(Node node) throws ParseException {

        Node parentNode = node.getParentNode();
        Node typeNode = parentNode.getAttributes().getNamedItem("type");
        String type = typeNode.getTextContent();
        String textValue = node.getTextContent();
        if (textValue != null){
            textValue = textValue.trim();
            if (textValue.isEmpty()) return null;
        }

        if ("boolean".equals(type)){
            return Boolean.valueOf(textValue);
        }
        if ("ddm-integer".equals(type)){
            return Integer.valueOf(textValue);
        }
        if ("ddm-decimal".equals(type)){
            return Double.valueOf(textValue);
        }
        if ("ddm-date".equals(type)){
            return textValue;
        }
        return textValue;
    }

    public static Node getNode(Document xmlDocument, String expression) throws SystemException, XPathExpressionException, PortalException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
        int length;
        if (( length = nodeList.getLength()) < 2) {
            return length == 1 ? nodeList.item(0) : null;
        } else {
            throw new PortalException(String.format("Expected single node value for '%s' but found '%d'!", expression, length));
        }

    }
}
