package nl.deltares.portal.utils;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.osgi.service.component.annotations.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Component(
        immediate = true,
        service = XmlContentUtils.class
)
public class XmlContentUtils {

    private static final Log LOG = LogFactoryUtil.getLog(XmlContentUtils.class);

    public static Document parseContent(JournalArticle article, Locale locale) throws PortalException {
        return parseContent(article.getTitle(), article.getContentByLocale(locale.getLanguage()));
    }

    public static Document parseContent(String siteName, String xmlString) throws PortalException {
        return parseContent(siteName, new ByteArrayInputStream(xmlString.getBytes()));
    }

    public static Document parseContent(String siteName, InputStream xmlInputStream) throws PortalException {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            //API to obtain DOM Document instance
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Parse the content to Document object
            return builder.parse(xmlInputStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new PortalException(String.format("Could not parse content for site %s: %s", siteName, e));
        }

    }

    public static Node getDynamicElementByName(Document xmlDocument, String nodeName, boolean optional) throws PortalException {
        List<Node> dynamicElementsByName = getDynamicElementsByNameAsList(xmlDocument, nodeName);
        int length = dynamicElementsByName.size();
        if (length == 0 && !optional) {
            throw new PortalException(String.format("Node name '%s' not found in document! ", nodeName));
        }
        if (length == 0) return null;
        return dynamicElementsByName.get(0);
    }

    public static List<Node> getDynamicElementsByNameAsList(Element element, String nodeName) {
        final ArrayList<Node> nodes = new ArrayList<>();
        if (nodeName == null) {
            nodes.add(element);
            return nodes;
        }
        final NodeList dynamicElements = element.getElementsByTagName("dynamic-element");
        addNodesWithNameToList(dynamicElements, nodeName, nodes);
        return nodes;
    }

    public static List<Node> getDynamicElementsByNameAsList(Document xmlDocument, String nodeName) {
        final ArrayList<Node> nodes = new ArrayList<>();
        final NodeList dynamicElements = xmlDocument.getElementsByTagName("dynamic-element");
        addNodesWithNameToList(dynamicElements, nodeName, nodes);
        return nodes;
    }

    private static void addNodesWithNameToList(NodeList nodeList, String name, List<Node> matchingNodes) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Element item = (Element) nodeList.item(i);
            if (name == null || name.equals(item.getAttribute("name"))) {
                matchingNodes.add(item);
            } else {
                addNodesWithNameToList(item.getElementsByTagName("dynamic-element"), name, matchingNodes);
            }
        }
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

//    public static Date parseDateTimeFields(Node dateNode, String timeField) throws PortalException {
//        return parseDateTimeFields(dateNode, timeField, TimeZone.getTimeZone("GMT"));
//    }

    public static Date parseDateTimeFields(Node dateNode, String timeField, TimeZone timeZone) throws PortalException {
        String dateValue = XmlContentUtils.getDynamicContentForNode(dateNode);
        String timeValue = XmlContentUtils.getDynamicContentByName(dateNode, timeField, true);
        if (timeValue == null) {
            timeValue = "00:00";
        }
        String dateTimeValue = dateValue + 'T' + timeValue;
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateTimeFormatter.setTimeZone(timeZone);
        try {
            return dateTimeFormatter.parse(dateTimeValue);
        } catch (Exception e) {
            throw new PortalException(String.format("Error parsing dateTime %s: %s", dateTimeValue, e.getMessage()));
        }
    }

//    public static Date parseDateTimeFields(Document xmlDocument, String dateField, String timeField) throws PortalException {
//        Node dateNode = getDynamicElementByName(xmlDocument, dateField, false);
//        return parseDateTimeFields(dateNode, timeField);
//    }

    private static String validateSingleContent(String nodeName, boolean optional, String[] contentValues) throws PortalException {
        int length = contentValues.length;
        if (length == 0 && !optional) {
            throw new PortalException(String.format("Node name '%s' not found in document! ", nodeName));
        }
        if (contentValues.length == 0) return null;
        final String returnValue = contentValues[0].trim();
        if (returnValue.isEmpty()) return null;
        return returnValue;
    }

    public static String[] getDynamicContentsByName(Node node, String nodeName) {
        return privateGetDynamicContentsByName(node, nodeName);
    }

    public static String[] getDynamicContentsByName(Document xmlDocument, String nodeName) {
        return privateGetDynamicContentsByName(xmlDocument, nodeName);
    }

    private static String[] privateGetDynamicContentsByName(Node node, String nodeName) {
        List<Node> nodeList;

        try {
            if (node instanceof Document) {
                nodeList = getDynamicElementsByNameAsList((Document) node, nodeName);
            } else if (node instanceof Element) {
                nodeList = getDynamicElementsByNameAsList((Element) node, nodeName);
            } else {
                throw new UnsupportedOperationException("Node not instance of Document nor Element");
            }
            if (nodeList.size() > 0) {
                return getDynamicContentValues(nodeList);
            }
        } catch (Exception e) {
            LOG.error(String.format("Error retrieving node value %s from content: %s", nodeName, e.getMessage()));
        }
        return new String[0];
    }

    private static String[] getDynamicContentValues(List<Node> nodeList) {
        List<String> nodeValues = new ArrayList<>();
        nodeList.forEach(node -> {

            final NodeList childNodes = node.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                final Node item = childNodes.item(i);
                if (item.getNodeName().equals("dynamic-content") && ((Element) item).getAttribute("name").isEmpty()) {
                    nodeValues.add(item.getTextContent());
                }
            }
        });
        return nodeValues.toArray(new String[0]);
    }

}
