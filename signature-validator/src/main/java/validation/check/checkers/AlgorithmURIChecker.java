package validation.check.checkers;

import it.svario.xpathapi.jaxp.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import validation.utils.AlgorithmURIProvider;
import validation.utils.XPathProvider;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import static validation.utils.CheckingUtils.verifyElementAttributeValue;

public class AlgorithmURIChecker implements SignatureChecker {

    private static final String algorithmAttribute = "Algorithm";

    private AlgorithmURIProvider algorithmURIProvider = new AlgorithmURIProvider();

    private XPathProvider xPathProvider = new XPathProvider();

    @Override
    public boolean check(File file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element signatureMethod = (Element) XPathAPI.selectSingleNode(document.getDocumentElement(),
                    xPathProvider.getSignatureMethodPath());
            Element canonicalizationMethod = (Element) XPathAPI.selectSingleNode(document.getDocumentElement(),
                    xPathProvider.getCanonicalizationMethodPath());
            NodeList transforms = XPathAPI.selectNodeList(document.getDocumentElement(), xPathProvider.getTransformsPath());
            NodeList digestValues = XPathAPI.selectNodeList(document.getDocumentElement(), xPathProvider.getDigestMethodPath());

            verifyElementAttributeValue(xPathProvider.getSignatureMethodPath(), signatureMethod,
                    algorithmAttribute, algorithmURIProvider.getAlgoListSignature());
            verifyElementAttributeValue(xPathProvider.getCanonicalizationMethodPath(), canonicalizationMethod,
                    algorithmAttribute, algorithmURIProvider.getAlgoListCanonicalization());
            verifyTransforms(transforms);
            verifyDigestMethods(digestValues);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private void verifyTransforms(NodeList transforms) {
        for (int i=0; i < transforms.getLength(); i++) {
            Element transformsElement = (Element) transforms.item(i);
            Element transformElement = (Element) transformsElement.getElementsByTagName("ds:Transform").item(0);
            verifyElementAttributeValue(transformElement, algorithmAttribute, algorithmURIProvider.getAlgoListTransform());
        }
    }

    private void verifyDigestMethods(NodeList digestValues) {
        for (int i=0; i < digestValues.getLength(); i++) {
            Element digestElement = (Element) digestValues.item(i);
            verifyElementAttributeValue(digestElement, algorithmAttribute, algorithmURIProvider.getAlgoListDigest());
        }
    }

}
