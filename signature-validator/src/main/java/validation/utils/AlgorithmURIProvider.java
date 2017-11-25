package validation.utils;

import java.util.Arrays;
import java.util.List;

public class AlgorithmURIProvider {

    private final List<String> algoListDigest = Arrays.asList("http://www.w3.org/2000/09/xmldsig#sha1",
            "http://www.w3.org/2001/04/xmlenc#sha256");

    private final List<String> algoListSignature = Arrays.asList("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256",
            "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256", "http://www.w3.org/2000/09/xmldsig#dsa-sha1");

    private final List<String> algoListCanonicalization = Arrays.asList("http://www.w3.org/TR/2001/REC-xml-c14n-20010315",
            "http://www.w3.org/2006/12/xml-c14n11", "http://www.w3.org/2001/10/xml-exc-c14n#");

    private final List<String> algoListTransform = Arrays.asList("http://www.w3.org/2000/09/xmldsig#base64",
            "http://www.w3.org/2000/09/xmldsig#enveloped-signature");

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
