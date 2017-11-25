package validation.check.checkers;

import it.svario.xpathapi.jaxp.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import validation.utils.AttributeValuesProvider;
import validation.utils.XPathProvider;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import static validation.utils.CheckingUtils.verifyNodes;

public class AttributeChecker implements Checker {

    private static final String ALGORITHM_ATTRIBUTE = "Algorithm";

    private static final String ID_ATTRIBUTE = "Id";

    private static final String XMLNS_DS_ATTRIBUTE = "xmlns:ds";

    private static final String XMLNS_XZEP_ATTRIBUTE = "xmlns:xzep";

    private static final String TYPE_ATTRIBUTE = "Type";

    private AttributeValuesProvider attributeValuesProvider = new AttributeValuesProvider();

    private XPathProvider xPathProvider = new XPathProvider();

    @Override
    public boolean check(File file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            NodeList envelope = document.getElementsByTagName("xzep:DataEnvelope");
            NodeList signature = document.getElementsByTagName("ds:Signature");
            NodeList signatureValue = document.getElementsByTagName("ds:SignatureValue");
            NodeList keyInfo = document.getElementsByTagName("ds:KeyInfo");
            NodeList signatureProperties = document.getElementsByTagName("ds:SignatureProperties");
            NodeList signatureMethod = XPathAPI.selectNodeList(document.getDocumentElement(),
                    xPathProvider.getSignatureMethodPath());
            NodeList canonicalizationMethod = XPathAPI.selectNodeList(document.getDocumentElement(),
                    xPathProvider.getCanonicalizationMethodPath());
            NodeList transforms = XPathAPI.selectNodeList(document.getDocumentElement(), xPathProvider.getTransformsPath());
            NodeList digestValues = XPathAPI.selectNodeList(document.getDocumentElement(), xPathProvider.getDigestMethodPath());

            NodeList manifestTransforms =
                    XPathAPI.selectNodeList(document.getDocumentElement(), xPathProvider.getManifestTransformsPath());
            NodeList manifestDigestValues =
                    XPathAPI.selectNodeList(document.getDocumentElement(), xPathProvider.getManifestDigestMethodPath());

            NodeList manifestElements =
                    XPathAPI.selectNodeList(document.getDocumentElement(), xPathProvider.getManifestPath());

            NodeList manifestReferenceElements =
                    XPathAPI.selectNodeList(document.getDocumentElement(), xPathProvider.getManifestReferencesPath());

            verifyAlgorithms(signatureMethod, canonicalizationMethod, transforms, digestValues, manifestTransforms, manifestDigestValues);
            verifyIdsPresent(signature, signatureValue, keyInfo, signatureProperties, manifestElements);
            verifyNamespaces(envelope, signature);

            verifyNodes(xPathProvider.getManifestReferencesPath(), manifestReferenceElements,
                    TYPE_ATTRIBUTE, attributeValuesProvider.getTypeValue());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private void verifyNamespaces(NodeList envelope, NodeList signature) {
        verifyNodes("xzep:DataEnvelope", envelope, XMLNS_DS_ATTRIBUTE, attributeValuesProvider.getDsNamespaceList());
        verifyNodes("xzep:DataEnvelope", envelope, XMLNS_XZEP_ATTRIBUTE, attributeValuesProvider.getXzepNamespaceList());
        verifyNodes("ds:Signature", signature, XMLNS_DS_ATTRIBUTE, attributeValuesProvider.getDsNamespaceList());
    }

    private void verifyIdsPresent(NodeList signature, NodeList signatureValue, NodeList keyInfo,
                                  NodeList signatureProperties, NodeList manifestElements) {
        verifyNodes("ds:Signature", signature, ID_ATTRIBUTE);
        verifyNodes("ds:SignatureValue", signatureValue, ID_ATTRIBUTE);
        verifyNodes("ds:KeyInfo", keyInfo, ID_ATTRIBUTE);
        verifyNodes("ds:SignatureProperties", signatureProperties, ID_ATTRIBUTE);
        verifyNodes(xPathProvider.getManifestPath(), manifestElements, ID_ATTRIBUTE);
    }

    private void verifyAlgorithms(NodeList signatureMethod, NodeList canonicalizationMethod, NodeList transforms,
                                  NodeList digestValues, NodeList manifestTransforms, NodeList manifestDigestValues) {
        verifyNodes(xPathProvider.getSignatureMethodPath(), signatureMethod,
                ALGORITHM_ATTRIBUTE, attributeValuesProvider.getAlgoListSignature());

        verifyNodes(xPathProvider.getCanonicalizationMethodPath(), canonicalizationMethod,
                ALGORITHM_ATTRIBUTE, attributeValuesProvider.getAlgoListCanonicalization());

        verifyNodes(xPathProvider.getTransformsPath(), transforms,
                ALGORITHM_ATTRIBUTE, attributeValuesProvider.getAlgoListTransform());

        verifyNodes(xPathProvider.getDigestMethodPath(), digestValues,
                ALGORITHM_ATTRIBUTE, attributeValuesProvider.getAlgoListDigest());

        verifyNodes(xPathProvider.getManifestTransformsPath(), manifestTransforms,
                ALGORITHM_ATTRIBUTE, attributeValuesProvider.getAlgoListManifestTransform());

        verifyNodes(xPathProvider.getManifestDigestMethodPath(), manifestDigestValues,
                ALGORITHM_ATTRIBUTE, attributeValuesProvider.getAlgoListDigest());
    }


}
