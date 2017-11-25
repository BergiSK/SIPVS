package validation.utils;

import java.util.HashMap;
import java.util.Map;

public class XPathProvider {

    private Map<String, String> xpathMap;

    public XPathProvider() {
        initMap();
    }

    public String getSignatureMethodPath() {
        return getXPath("SignatureMethod");
    }

    public String getCanonicalizationMethodPath() {
        return getXPath("CanonicalizationMethod");
    }

    public String getTransformsPath() {
        return getXPath("Transforms");
    }

    public String getDigestMethodPath() {
        return getXPath("DigestMethod");
    }

    private String getXPath(String elementName) {
        return xpathMap.get(elementName);
    }

    private void initMap() {
        xpathMap = new HashMap<>();
        xpathMap.put("SignatureMethod", "//SignedInfo/*[local-name() = 'SignatureMethod']");
        xpathMap.put("CanonicalizationMethod", "//SignedInfo/*[local-name() = 'CanonicalizationMethod']");
        xpathMap.put("Transforms", "//SignedInfo/*[local-name() = 'Reference']/Transforms");
        xpathMap.put("DigestMethod", "//SignedInfo/*[local-name() = 'Reference']/DigestMethod");
    }


}
