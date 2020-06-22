package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.stream.IntStream;

public class XmlContentParserUtils {

    private static final Log LOG = LogFactoryUtil.getLog(XmlContentParserUtils.class);

    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    static String ARTICLE_ROOT= "root";
    static String ARTICLE_DYNAMIC_ELEMENT= "dynamic-element";
    static String ARTICLE_NAME_ATTRIBUTE_START= "[@name='";
    static String ARTICLE_NAME_ATTRIBUTE_END= "']";
    static String ARTICLE_CONTENT_XML_NODE_END= "dynamic-content";

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

    public static Node getDynamicElementByName(Document xmlDocument, String nodeName, boolean optional) throws PortalException{
        NodeList dynamicElementsByName = getDynamicElementsByName(xmlDocument, nodeName);
        int length = dynamicElementsByName == null ? 0 : dynamicElementsByName.getLength();
        if (length == 0 && !optional){
            throw new PortalException(String.format("Node name '%s' not found in document! ", nodeName));
        }
        if (length > 1){
            throw new PortalException(String.format("Expected single element for '%s' but found '%d'!", nodeName, length));
        }
        if (dynamicElementsByName == null) return null;
        return dynamicElementsByName.item(0);
    }

    public static NodeList getDynamicElementsByName(Document xmlDocument, String nodeName){
        String[] searchLevels = {
                '/' + ARTICLE_ROOT + '/' + ARTICLE_DYNAMIC_ELEMENT + ARTICLE_NAME_ATTRIBUTE_START + nodeName + ARTICLE_NAME_ATTRIBUTE_END,
                '/' + ARTICLE_ROOT + '/' + ARTICLE_DYNAMIC_ELEMENT + '/' + ARTICLE_DYNAMIC_ELEMENT + ARTICLE_NAME_ATTRIBUTE_START + nodeName + ARTICLE_NAME_ATTRIBUTE_END
        };

        for (String expression : searchLevels) {
            try {
                NodeList list = getNodeList(xmlDocument, expression);
                if (list != null && list.getLength() > 0) {
                    return list;
                }
            } catch (XPathExpressionException e) {
                LOG.error(String.format("Error retrieving nodes %s from content: %s", nodeName, e.getMessage()));
            }
        }
        return null;
    }

    public static String getDynamicContentForNode(Node node) throws PortalException {
        String[] contentValues = getDynamicContentsByName(node, null);
        return validateSingleContent(node.getNodeName(), false, contentValues);
    }

    public static String getDynamicContentByName(Node node, String nodeName, boolean optional) throws PortalException {
        String[] contentValues = getDynamicContentsByName(node, nodeName);
        return validateSingleContent(nodeName, optional, contentValues);
    }

    public static String getDynamicContentByName(Document xmlDocument, String nodeName, boolean optional) throws PortalException {
        String[] contentValues = getDynamicContentsByName(xmlDocument, nodeName);
        return validateSingleContent(nodeName, optional, contentValues);
    }

    public static Date parseDateTimeFields(Document xmlDocument, String dateField, String timeField, boolean optional) throws PortalException {
        Node dateNode = getDynamicElementByName(xmlDocument, dateField, optional);
        if (dateNode == null) return null;
        String dateValue = XmlContentParserUtils.getDynamicContentForNode(dateNode);
        String timeValue = XmlContentParserUtils.getDynamicContentByName(dateNode, timeField, optional);
        if (timeValue == null){
            timeValue = "00:00";
        }
        String dateTimeValue = dateValue + 'T' + timeValue;
        try {
            return dateTimeFormatter.parse(dateTimeValue);
        } catch (ParseException e) {
            throw new PortalException(String.format("Error parsing dateTime %s: %s", dateTimeValue, e.getMessage()));
        }
    }
    private static String validateSingleContent(String nodeName, boolean optional, String[] contentValues) throws PortalException {
        int length = contentValues.length;
        if (length == 0 && !optional){
            throw new PortalException(String.format("Node name '%s' not found in document! ", nodeName));
        }
        if (length > 1){
            throw new PortalException(String.format("Expected single element for '%s' but found '%d'!", nodeName, length));
        }
        if (contentValues.length == 0) return null;
        if (contentValues[0].trim().isEmpty()) return null;
        return contentValues[0];
    }

    public static String[] getDynamicContentsByName(Node node, String nodeName){

        String search;
        if (nodeName == null ){
            search = ARTICLE_CONTENT_XML_NODE_END;
        } else {
            search = ARTICLE_DYNAMIC_ELEMENT  + ARTICLE_NAME_ATTRIBUTE_START + nodeName + ARTICLE_NAME_ATTRIBUTE_END + '/' + ARTICLE_CONTENT_XML_NODE_END;
        }
        return privateGetDynamicContentsByName(node, nodeName, new String[]{search});
    }

    public static String[] getDynamicContentsByName(Document xmlDocument, String nodeName){
        String[] searchLevels = {
                '/' + ARTICLE_ROOT + '/' + ARTICLE_DYNAMIC_ELEMENT + ARTICLE_NAME_ATTRIBUTE_START + nodeName + ARTICLE_NAME_ATTRIBUTE_END + '/' + ARTICLE_CONTENT_XML_NODE_END,
                '/' + ARTICLE_ROOT + '/' + ARTICLE_DYNAMIC_ELEMENT + '/' + ARTICLE_DYNAMIC_ELEMENT + ARTICLE_NAME_ATTRIBUTE_START + nodeName + ARTICLE_NAME_ATTRIBUTE_END + '/' + ARTICLE_CONTENT_XML_NODE_END
        };
        return privateGetDynamicContentsByName(xmlDocument, nodeName, searchLevels);
    }

    private static String[] privateGetDynamicContentsByName(Node node, String nodeName, String[] searchLevels) {
        NodeList nodeList;

        for (String expression : searchLevels) {
            try {
                nodeList = getNodeList(node, expression);
                if (nodeList != null) {
                    return getDynamicContentValues(nodeList);
                }
            } catch (Exception e) {
                LOG.error(String.format("Error retrieving node value %s from content: %s", nodeName, e.getMessage()));
            }
        }
        return new String[0];
    }

    private static String[] getDynamicContentValues(NodeList nodeList) {
        return IntStream.range(0, nodeList.getLength())
                .mapToObj(nodeList::item)
                .map(node -> {
                    String nodeValue = null;
                    try {
                        nodeValue = node.getTextContent();
                    } catch (Exception e) {
                        LOG.error("Error parsing node value: " + e.getMessage());
                    }
                    return nodeValue;
                })
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .toArray(String[]::new);
    }

    private static NodeList getNodeList(Node xmlObject, String expression) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        return (NodeList) xPath.compile(expression).evaluate(xmlObject, XPathConstants.NODESET);
    }

}
