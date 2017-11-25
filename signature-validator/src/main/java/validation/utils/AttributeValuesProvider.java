package validation.utils;

import java.util.Arrays;
import java.util.List;

public class AttributeValuesProvider {

    private final List<String> algoListDigest = Arrays.asList(
            "http://www.w3.org/2000/09/xmldsig#sha1",
            "http://www.w3.org/2001/04/xmldsig-more#sha224",
            "http://www.w3.org/2001/04/xmlenc#sha256",
            "http://www.w3.org/2001/04/xmldsig-more#sha384",
            "http://www.w3.org/2001/04/xmlenc#sha512"
    );

    private final List<String> algoListSignature = Arrays.asList(
            "http://www.w3.org/2000/09/xmldsig#dsa-sha1",
            "http://www.w3.org/2000/09/xmldsig#rsa-sha1",
            "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256",
            "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384",
            "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512"
    );

    private final List<String> algoListCanonicalization = Arrays.asList(
            "http://www.w3.org/TR/2001/REC-xml-c14n-20010315",
            "http://www.w3.org/2006/12/xml-c14n11", "http://www.w3.org/2001/10/xml-exc-c14n#"
    );

    private final List<String> algoListTransform = Arrays.asList(
            "http://www.w3.org/TR/2001/REC-xml-c14n-20010315"
    );

    private List<String> algoListManifestTransform = Arrays.asList(
            "http://www.w3.org/TR/2001/REC-xml-c14n-20010315",
            "http://www.w3.org/2000/09/xmldsig#base64"
    );

    private List<String> dsNamespaceList = Arrays.asList(
            "http://www.w3.org/2000/09/xmldsig#"
    );

    private List<String> xzepNamespaceList = Arrays.asList(
            "http://www.ditec.sk/ep/signature_formats/xades_zep/v1.0",
            "http://www.ditec.sk/ep/signature_formats/xades_zep/v1.1",
            "http://www.ditec.sk/ep/signature_formats/xades_zep/v2.0"
    );

    private List<String> typeValue = Arrays.asList(
            "http://www.w3.org/2000/09/xmldsig#Object"
    );

    public List<String> getDsNamespaceList() {
        return dsNamespaceList;
    }

    public List<String> getXzepNamespaceList() {
        return xzepNamespaceList;
    }

    public List<String> getTypeValue() {
        return typeValue;
    }

    public List<String> getAlgoListManifestTransform() {
        return algoListManifestTransform;
    }

    public List<String> getAlgoListDigest() {
        return algoListDigest;
    }

    public List<String> getAlgoListSignature() {
        return algoListSignature;
    }

    public List<String> getAlgoListCanonicalization() {
        return algoListCanonicalization;
    }

    public List<String> getAlgoListTransform() {
        return algoListTransform;
    }
}
