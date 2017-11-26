package validation.check.checkers;

import it.svario.xpathapi.jaxp.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import validation.utils.SignedInfoReferencesProvider;
import validation.utils.XPathProvider;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathException;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SignedInfoReferencesChecker implements Checker {

    private final String KEY_INFO = "ds:KeyInfo";
    private final String SIGNATURE_PROPERTIES = "ds:SignatureProperties";
    private final String SIGNED_PROPRERTIES = "xades:SignedProperties";

    @Override
    public boolean check(File file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        XPathProvider xpathtProvider = new XPathProvider();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            validateSignedInfoChildElements(document, XPathAPI.selectNodeList(document.getDocumentElement(),
                    xpathtProvider.getReferencePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void validateSignedInfoChildElements(Document document, NodeList references) {
        SignedInfoReferencesProvider signedInfoReferencesProvider = new SignedInfoReferencesProvider();
        Map<String, Integer> counter = new HashMap<String, Integer>();
        counter.put(KEY_INFO, 0);
        counter.put(SIGNATURE_PROPERTIES, 0);
        counter.put(SIGNED_PROPRERTIES, 0);

        for (int i = 0; i < references.getLength(); i++) {
            Element reference = (Element) references.item(i);
            String referenceType = reference.getAttributes().getNamedItem("Type").getNodeValue();
            String uri = reference.getAttributes().getNamedItem("URI").getNodeValue().replace("#", "");

            Element referencedElement = null;
            String referencedElementName = null;
            try {
                referencedElement = (Element) XPathAPI.selectSingleNode(document.getDocumentElement(), String.format("//*[@Id='%s']", uri));
                referencedElementName = referencedElement.getNodeName();
            } catch (XPathException | NullPointerException e ) {
                System.out.println("ERROR: Referenced element with id: " + uri + " does not exist.");
                return;
            }

            String referenceId = reference.getAttributes().getNamedItem("Id").getNodeValue();
            String elementType = signedInfoReferencesProvider.getNameByURI(referencedElement.getNodeName());

            if (elementType == null) {
                System.out.println("ERROR: Type attribute in reference element with id: " + referenceId + " is not valid.");
                return;
            }
            // check if type attribute in reference element match type of real element
            if (!elementType.equals(referenceType)) {

                System.out.println("ERROR: Type attribute in reference element with id: " + referenceId + " match type of real element with id: " + uri);
                return;
            }
            Integer count = counter.get(referencedElementName);
            if (count != null) {
                counter.put(referencedElementName, count + 1);
            }
        }
        Iterator it = counter.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if((Integer)pair.getValue() > 1){
                System.out.println("ERROR: Number of elements " + pair.getKey() + " in ds:SignedInfo " +
                        String.valueOf((Integer)pair.getValue() + ". There must be only one."));
            }
            if((Integer)pair.getValue() < 1) {
                System.out.println("ERROR: There must be at least one element " + pair.getKey() + " in ds:SignedInfo");
            }

        }
    }
}
