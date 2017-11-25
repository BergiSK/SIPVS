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


    public String getManifestTransformsPath() {
        return getXPath("ManifestTransforms");
    }

    public String getManifestDigestMethodPath() {
        return getXPath("ManifestDigestMethod");
    }

    public String getReferencePath() {
        return getXPath("Reference");
    }


    public String getManifestPath() {
        return getXPath("Manifest");
    }


    public String getManifestReferencesPath() {
        return getXPath("ManifestReferences");
    }

    private String getXPath(String elementName) {
        return xpathMap.get(elementName);
    }

    private void initMap() {
        xpathMap = new HashMap<>();
        xpathMap.put("ManifestTransforms", "//Object/Manifest/*[local-name() = 'Reference']/Transforms/Transform");
        xpathMap.put("ManifestDigestMethod", "//Object/Manifest/*[local-name() = 'Reference']/DigestMethod");
        xpathMap.put("SignatureMethod", "//SignedInfo/*[local-name() = 'SignatureMethod']");
        xpathMap.put("CanonicalizationMethod", "//SignedInfo/*[local-name() = 'CanonicalizationMethod']");
        xpathMap.put("Transforms", "//SignedInfo/*[local-name() = 'Reference']/Transforms/Transform");
        xpathMap.put("DigestMethod", "//SignedInfo/*[local-name() = 'Reference']/DigestMethod");
        xpathMap.put("ManifestReferences", "//Object/Manifest/*[local-name() = 'Reference']");
        xpathMap.put("Manifest", "//Object/Manifest");
        xpathMap.put("Reference", "//SignedInfo/*[local-name() = 'Reference']");

    }


}
