package validation.check.checkers;

import it.svario.xpathapi.jaxp.XPathAPI;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.X509CertificateObject;
import org.bouncycastle.util.encoders.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import validation.utils.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

public class SignatureValueChecker implements Checker{

    public SignatureValueChecker(){
        // this is neccessary for asymmetric keyInfo convertion
        new BouncyCastleProvider();
    }

    @Override
    public boolean check(File file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        XPathProvider xpathtProvider = new XPathProvider();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            Node signatureElement =
                    XPathAPI.selectSingleNode(document.getDocumentElement(),
                            xpathtProvider.getSignaturePath());
            validateSignature((Element)signatureElement);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    private void validateSignature(Element signatureElement) throws TransformerException {
        AttributeValuesProvider attProvider = new AttributeValuesProvider();

        byte[] signatureValueBytes  =  Base64.decode(signatureElement.getElementsByTagName("ds:SignatureValue").item(0).getTextContent().getBytes());

        Element signedInfoElement = (Element) signatureElement.getElementsByTagName("ds:SignedInfo").item(0);

        Element canonicalizationMethodElement = (Element) signedInfoElement.getElementsByTagName("ds:CanonicalizationMethod").item(0);
        String canonicalizationAlgorithm = canonicalizationMethodElement.getAttributes().getNamedItem("Algorithm").getNodeValue();
        if(!attProvider.getAlgoListCanonicalization().contains(canonicalizationAlgorithm)){
            System.out.println("ERROR: Not supported CanonicalizationMethod used: " + canonicalizationAlgorithm + ".");
            return;
        }

        Element signatureMethodElement = (Element) signedInfoElement.getElementsByTagName("ds:SignatureMethod").item(0);
        String signatureMethodAlgorithm = signatureMethodElement.getAttributes().getNamedItem("Algorithm").getNodeValue();
        if(!attProvider.getAlgoListSignature().contains(signatureMethodAlgorithm)){
            System.out.println("ERROR: Not supported SignatureMethod used: " + signatureMethodAlgorithm + ".");
            return;
        }

        byte[] signedInfoBytes  = CheckingUtils.elementToString(signedInfoElement).getBytes();
        byte[] canonicalizedBytes = null;
        try {
            canonicalizedBytes = CheckingUtils.canonicalizeElementbytes(signedInfoBytes,canonicalizationAlgorithm);
        } catch (Exception e) {
            System.out.println("ERROR: Canonicalization failed on element:" +signedInfoElement.getNodeName() + ".");
        }

        Element certificateElement = (Element) signatureElement.getElementsByTagName("ds:X509Certificate").item(0);
        X509CertificateObject certificate = CertificateUtils.convertToCertificate(certificateElement.getTextContent());

        Signature signature = initializeSignatureObject(signatureMethodAlgorithm, certificate, canonicalizedBytes);

        try {
            if(!signature.verify(signatureValueBytes)){
                System.out.println("ERROR: Values of ds:SignatureInfo and signed ds:SignedInfo do not match.");
            }
        } catch (SignatureException e) {
            System.out.println("ERROR: Signature verificaiton can not be realized.");
        }
    }

    private Signature initializeSignatureObject(String signatureMethodAlgorithm, X509CertificateObject certificate,
                                                byte[] canonicalizedBytes){
        Signature signature = null;
        try {
            signature = Signature.getInstance(new SignAlgNameProvider().getNameByURI(signatureMethodAlgorithm));
            signature.initVerify(certificate.getPublicKey());
            signature.update(canonicalizedBytes);

        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException  e) {
            System.out.println("ERROR: Initialization of signature failed.");
        }
        return signature;
    }

}
