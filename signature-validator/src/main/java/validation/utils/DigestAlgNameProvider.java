package validation.utils;

import java.util.HashMap;
import java.util.Map;

public class DigestAlgNameProvider {
    private static final Map<String, String> DIGEST_ALGORITHM = new HashMap<String, String>();

    static {
        DIGEST_ALGORITHM.put("http://www.w3.org/2001/04/xmldsig-more#sha224", "SHA-224");
        DIGEST_ALGORITHM.put("http://www.w3.org/2000/09/xmldsig#sha1", "SHA-1");
        DIGEST_ALGORITHM.put("http://www.w3.org/2001/04/xmlenc#sha256", "SHA-256");
        DIGEST_ALGORITHM.put("http://www.w3.org/2001/04/xmlenc#sha512", "SHA-512");
        DIGEST_ALGORITHM.put("http://www.w3.org/2001/04/xmldsig-more#sha384", "SHA-384");
    }

        public String getNameByNamespace(String namespace){
            try{
                return DIGEST_ALGORITHM.get(namespace);
            }catch (EnumConstantNotPresentException e) {
                System.out.println("ERROR: Not supported digest algorithm namespace: "  + namespace);
            }
            return "";
        }
}
