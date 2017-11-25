package validation.utils;

import org.w3c.dom.Node;

import java.util.List;

public class CheckingUtils {

    public static void verifyElementAttributeValue(Node node, String attributeName, List<String> expectedValues) {
        verifyElementAttributeValue(null, node, attributeName, expectedValues);
    }

    public static void verifyElementAttributeValue(String nodePath, Node node, String attributeName, List<String> expectedValues) {
        if (node == null) {
            System.out.format("Node at: [%s] not found\n", nodePath);
        }

        if (!expectedValues.contains(node.getAttributes().getNamedItem(attributeName).getNodeValue())) {
            System.out.format("Found attribute: [%s] of node: [%s] with unexpected value: [%s], expected values are [%s]\n",
                    attributeName, node.getNodeName(), node.getAttributes().getNamedItem(attributeName).getNodeValue(), expectedValues);
        }
    }

    public static void verifyElementAttributeExists(Node node, String attributeName) {
        if (node.getAttributes().getNamedItem(attributeName) == null) {
            System.out.format("Attribute: [%s] of node: [%s] not found\n", attributeName, node.getNodeName());
        }
    }
}
