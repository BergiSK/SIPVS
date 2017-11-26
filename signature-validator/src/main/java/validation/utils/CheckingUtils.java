package validation.utils;

import org.apache.xml.security.c14n.Canonicalizer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;


public class CheckingUtils {

    public static String getAttributeValue(Element element, String attributeName) {
        Node attribute = element.getAttributes().getNamedItem(attributeName);

        if (attribute == null) {
            System.out.format("Attribute: [%s] not found\n", attributeName);
            return null;
        }

        return attribute.getNodeValue();
    }

    public static void verifyNodes(String nodePath, NodeList nodes, String attributeName, List<String> expectedValues) {
        if (nodes.getLength() == 0) {
            System.out.format("Node at: [%s] not found\n", nodePath);
            return;
        }

        for (int i=0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            verifyElementAttributeValue(nodePath, element, attributeName, expectedValues);
        }
    }

    public static void verifyNodes(String nodesName, NodeList nodes, String attributeName) {
        if (nodes.getLength() == 0) {
            System.out.format("Node at: [%s] not found\n", nodesName);
            return;
        }

        for (int i=0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            verifyElementAttributeExists(element, attributeName);
        }
    }

    public static void verifyElementAttributeExists(Node node, String attributeName) {
        if (node.getAttributes().getNamedItem(attributeName) == null) {
            System.out.format("Attribute: [%s] of node: [%s] not found\n", attributeName, node.getNodeName());
        }
    }

    private static void verifyElementAttributeValue(String nodePath, Node node, String attributeName, List<String> expectedValues) {
        if (node == null) {
            System.out.format("Node at: [%s] not found\n", nodePath);
        }

        if (!expectedValues.contains(node.getAttributes().getNamedItem(attributeName).getNodeValue())) {
            System.out.format("Found attribute: [%s] of node: [%s] with unexpected value: [%s], expected values are [%s]\n",
                    attributeName, node.getNodeName(), node.getAttributes().getNamedItem(attributeName).getNodeValue(), expectedValues);
        }
    }

    public static String elementToString(Node element) throws TransformerException {

        StreamResult result = new StreamResult(new StringWriter());

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(element), result);
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        return result.getWriter().toString();

    }

    public static byte[] canonicalizeElementbytes(byte[] nodeElementBytes, String transformValue) throws Exception{
        byte[] canonicalizedElement = null;
        Canonicalizer canonicalizer = Canonicalizer.getInstance(transformValue);
        canonicalizedElement = canonicalizer.canonicalize(nodeElementBytes);
        return canonicalizedElement;
    }
}
