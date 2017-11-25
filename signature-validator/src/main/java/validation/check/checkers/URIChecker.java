package validation.check.checkers;

import it.svario.xpathapi.jaxp.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class URIChecker implements SignatureChecker {

    private static final String algorithmAttribute = "Algorithm";

    private static final List<String> algoListDigest = Arrays.asList("http://www.w3.org/2000/09/xmldsig#sha1",
            "http://www.w3.org/2001/04/xmlenc#sha256");

    private static final List<String> algoListSignature = Arrays.asList("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256",
            "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256", "http://www.w3.org/2000/09/xmldsig#dsa-sha1");

    private static final List<String> algoListCanonicalization = Arrays.asList("http://www.w3.org/TR/2001/REC-xml-c14n-20010315",
            "http://www.w3.org/2006/12/xml-c14n11", "http://www.w3.org/2001/10/xml-exc-c14n#");

    private static final List<String> algoListTransform = Arrays.asList("http://www.w3.org/2000/09/xmldsig#base64",
            "http://www.w3.org/2000/09/xmldsig#enveloped-signature");

    @Override
    public boolean check(File file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element signatureMethod = (Element) XPathAPI.selectSingleNode(document.getDocumentElement(),
                    "//SignedInfo/*[local-name() = 'SignatureMethod']");
            Element canonicalizationMethod = (Element) XPathAPI.selectSingleNode(document.getDocumentElement(),
                    "//SignedInfo/*[local-name() = 'CanonicalizationMethod']");

            NodeList transforms =
                    XPathAPI.selectNodeList(document.getDocumentElement(),
                            "//SignedInfo/*[local-name() = 'Reference']/Transforms");

            NodeList digestValues =
                    XPathAPI.selectNodeList(document.getDocumentElement(),
                            "//SignedInfo/*[local-name() = 'Reference']/DigestMethod");

            verifyElementAttributeValue(signatureMethod, algorithmAttribute, algoListSignature);
            verifyElementAttributeValue(canonicalizationMethod, algorithmAttribute, algoListCanonicalization);

            verifyTransforms(transforms);
            verifyDigestMethods(digestValues);

            if (signatureMethod == null) {
                return false;
            }

            System.out.println(signatureMethod.getTextContent());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return true;
    }

    private void verifyTransforms(NodeList transforms) {
        for (int i=0; i < transforms.getLength(); i++) {
            Element transformsElement = (Element) transforms.item(i);
            Element transformElement = (Element) transformsElement.getElementsByTagName("ds:Transform").item(0);

            verifyElementAttributeValue(transformElement, algorithmAttribute, algoListTransform);
        }
    }

    private void verifyDigestMethods(NodeList digestValues) {
        for (int i=0; i < digestValues.getLength(); i++) {
            Element digestElement = (Element) digestValues.item(i);
            verifyElementAttributeValue(digestElement, algorithmAttribute, algoListDigest);
        }
    }

    private boolean verifyElementAttributeValue(Node node, String attributeName, List<String> expectedValues) {
        if (!expectedValues.contains(node.getAttributes().getNamedItem(attributeName).getNodeValue())) {
            System.out.format("Found attribute: [%s] of node: [%s] with unexpected value: [%s], expected values are [%s]\n",
                    attributeName, node.getNodeName(), node.getAttributes().getNamedItem(attributeName).getNodeValue(), expectedValues);
            return false;
        }
        return true;
    }
}