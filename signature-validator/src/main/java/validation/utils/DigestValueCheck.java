package validation.utils;

import it.svario.xpathapi.jaxp.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import validation.utils.AttributeValuesProvider;
import validation.utils.CheckingUtils;
import validation.utils.DigestAlgNameProvider;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathException;
import org.bouncycastle.util.encoders.Base64;
import org.apache.xml.security.c14n.Canonicalizer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestValueCheck {
    private static final String CN14 = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
    private static final String BASE64 = "http://www.w3.org/2000/09/xmldsig#base64";

    static{
        Canonicalizer.registerDefaultAlgorithms();
    }
    private final boolean useManifestTransformMethods;

    public DigestValueCheck(boolean useManifestTransformMethods){
        this.useManifestTransformMethods = useManifestTransformMethods;
    }
    public void validateReference(Document document ,Element reference, String nodeName){
        DigestAlgNameProvider digestProvider = new DigestAlgNameProvider();
        AttributeValuesProvider attributeValueProvider =  new AttributeValuesProvider();
        String uri = reference.getAttributes().getNamedItem("URI").getNodeValue().replace("#","");
        try {
            Node referencedElement = (nodeName!=null) ?
                    XPathAPI.selectSingleNode(document.getDocumentElement(),String.format("//*[local-name() = '%s' and @Id='%s']", nodeName, uri)):
                    XPathAPI.selectSingleNode(document.getDocumentElement(),String.format("//*[@Id='%s']", uri));

            if(referencedElement !=null){
                // Transform Algorithm
                Element digesteTansformsElement = (Element) reference.getElementsByTagName("ds:Transforms").item(0);

                NodeList digesteTansformElements = (NodeList) digesteTansformsElement.getElementsByTagName("ds:Transform");
                String transformValue = null;
                for(int j=0; j < digesteTansformElements.getLength(); j++){
                    String digestTransformURI = digesteTansformElements.item(j).getAttributes().getNamedItem("Algorithm").getNodeValue();
                    if (!this.useManifestTransformMethods && attributeValueProvider.getAlgoListTransform().contains(digestTransformURI)){
                        transformValue = digestTransformURI;
                        break;
                    }
                    if (this.useManifestTransformMethods && attributeValueProvider.getAlgoListManifestTransform().contains(digestTransformURI)){
                        transformValue = digestTransformURI;
                        break;
                    }
                }
                if(transformValue == null){
                    System.out.println("ERROR in element with id:" + reference.getParentNode().getAttributes().getNamedItem("Id").getNodeValue() +
                            ". Transorm Algorithm is not valid");
                    return;
                }

                // DigestMethod Algorithm
                Element digesteMethodElement = (Element) reference.getElementsByTagName("ds:DigestMethod").item(0);
                String DigesteMethodURI = digesteMethodElement.getAttributes().getNamedItem("Algorithm").getNodeValue();
                String digestMethod = digestProvider.getNameByURI(DigesteMethodURI);
                if(digestMethod == null){
                    System.out.println("ERROR in element with id:" + reference.getAttributes().getNamedItem("Id").getNodeValue() +
                            ". DigestMethod is not valid");
                    return;
                }
                // DigestValue
                Element digestValueElement = (Element) reference.getElementsByTagName("ds:DigestValue").item(0);
                String digestValue = digestValueElement.getTextContent();

                validateReferencedElement(referencedElement, transformValue, digestMethod, digestValue);
            }
        } catch (XPathException e) {
            e.printStackTrace();
        }

    }

    private void validateReferencedElement(Node node, String transformValue, String digestMethod, String digestValue){

        byte[] nodeElementBytes = null;

        // Element to bytes
        try {
            nodeElementBytes = CheckingUtils.elementToString(node).getBytes();
        } catch (TransformerException e) {
            System.out.println("ERROR: " + node.getNodeName() + ". Transformation form element to string failed.");
        }

        if (nodeElementBytes == null){
            return;
        }

        // canonicalization of element
        byte[] canonicalizedElement = null;
        if (CN14.equals(transformValue)) {

            try {
                canonicalizedElement = CheckingUtils.canonicalizeElementbytes(nodeElementBytes, transformValue);
            } catch (Exception e) {
                System.out.println("ERROR: Canonicalization failed");
                return;
            }
        }
        if(this.useManifestTransformMethods && BASE64.equals(transformValue)){
            canonicalizedElement = Base64.decode(nodeElementBytes);
        }

        // hash
        MessageDigest message = getMessageDigest(digestMethod);
        String calculatedDigestValue = new String(Base64.encode(message.digest(canonicalizedElement)));
        if(!digestValue.equals(calculatedDigestValue)){
            System.out.println("ERROR: Digested value is changed in node:" + node.getNodeName() +
                    "(id:" + node.getAttributes().getNamedItem("Id").getNodeValue()+ ")");
        }

    }


    private MessageDigest getMessageDigest(String digestMethod){
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance(digestMethod);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return messageDigest;
    }

}
