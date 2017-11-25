package validation.check.checkers;

import org.bouncycastle.jce.provider.X509CertificateObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Collections;

import static validation.utils.CertificateUtils.convertToCertificate;
import static validation.utils.CheckingUtils.getAttributeValue;
import static validation.utils.CheckingUtils.verifyNodes;

public class ElementContentChecker implements Checker {

    private static final String ID_ATTRIBUTE = "Id";
    private static final String TARGET_ATTRIBUTE = "Target";

    @Override
    public boolean check(File file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            verifySignatureProperties(document);
            verifyKeyInfo(document);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private void verifyKeyInfo(Document document) {
        Element keyInfoElement = (Element) document.getElementsByTagName("ds:KeyInfo").item(0);
        Element xDataElement = (Element) keyInfoElement.getElementsByTagName("ds:X509Data").item(0);

        if (xDataElement == null) {
            System.out.format("Element xDataElement not found\n");
            return;
        }

        Element xCertificateElement = (Element) xDataElement.getElementsByTagName("ds:X509Certificate").item(0);
        Element xIssuerSerialElement = (Element) xDataElement.getElementsByTagName("ds:X509IssuerSerial").item(0);
        Element xSubjectNameElement = (Element) xDataElement.getElementsByTagName("ds:X509SubjectName").item(0);

        if (xCertificateElement == null) {
            System.out.format("Element xCertificateElement not found\n");
            return;
        }

        if (xIssuerSerialElement == null) {
            System.out.format("Element xIssuerSerialElement not found\n");
            return;
        }

        if (xSubjectNameElement == null) {
            System.out.format("Element xSubjectNameElement not found\n");
            return;
        }

        Element xIssuerNameElement = (Element) xIssuerSerialElement.getElementsByTagName("ds:X509IssuerName").item(0);
        Element xSerialNumberElement = (Element) xIssuerSerialElement.getElementsByTagName("ds:X509SerialNumber").item(0);

        if (xIssuerNameElement == null) {
            System.out.format("Element xIssuerNameElement not found\n");
            return;
        }

        if (xSerialNumberElement == null) {
            System.out.format("Element xSerialNumberElement not found\n");
            return;
        }

        X509CertificateObject certificate = convertToCertificate(xCertificateElement.getTextContent());

        //        TODO waaat replace??
        String certifIssuerName = certificate.getIssuerX500Principal().toString().replaceAll("ST=", "S=");
        String certifSerialNumber = certificate.getSerialNumber().toString();
        String certifSubjectName = certificate.getSubjectX500Principal().toString();

        if (xIssuerNameElement.getTextContent().equals(certifIssuerName) == false) {
            System.out.format("Element ds:X509IssuerName does not match certificate value");
        }

        if (xSerialNumberElement.getTextContent().equals(certifSerialNumber) == false) {
            System.out.format("Element ds:X509SerialNumber does not match certificate value");
        }

        if (xSubjectNameElement.getTextContent().equals(certifSubjectName) == false) {
            System.out.format("Element ds:X509SubjectName does not match certificate value");
        }
    }

    private void verifySignatureProperties(Document document) {
        Element signature = (Element) document.getElementsByTagName("ds:Signature").item(0);
        String signatureId = getAttributeValue(signature, ID_ATTRIBUTE);

        if (signatureId == null || signatureId.isEmpty()) {
            System.out.format("SignatureId not found\n");
        }

        NodeList signatureProperties = document.getElementsByTagName("ds:SignatureProperties");
        if (signatureProperties.getLength() == 0) {
            System.out.format("Element SignatureProperties not found\n");
            return;
        }

        Element signaturePropertiesElement = (Element) signatureProperties.item(0);
        NodeList signaturePropertyList = signaturePropertiesElement.getElementsByTagName("ds:SignatureProperty");

        if (signaturePropertyList.getLength() != 2) {
            System.out.format("Wrong lenght of signature properties: %d\n", signatureProperties.getLength());
            return;
        }

        if (!containsBothProperties(signaturePropertyList)) {
            System.out.format("Missing a property\n");
            return;
        }

        verifyNodes("ds:SignatureProperty", signaturePropertyList, TARGET_ATTRIBUTE, Collections.singletonList("#"+signatureId));
    }

    private boolean containsBothProperties(NodeList signaturePropertyList) {
        Element signatureVersion = null;
        Element productInfos = null;
        for (int i = 0; i < signaturePropertyList.getLength(); i++) {
            Element e = (Element) signaturePropertyList.item(i);
            if (e.getElementsByTagName("xzep:SignatureVersion").getLength() != 0) {
                signatureVersion = e;
            } else if (e.getElementsByTagName("xzep:ProductInfos").getLength() != 0) {
                productInfos = e;
            }
        }

        return (signatureVersion != null && productInfos != null) ? true : false;
    }
}
