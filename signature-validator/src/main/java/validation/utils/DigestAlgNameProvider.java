package validation.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DigestAlgNameProvider {

    private Map<String,String> digestAlgorithm;

    public DigestAlgNameProvider(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("http://www.w3.org/2001/04/xmldsig-more#sha224", "SHA-224");
        map.put("http://www.w3.org/2000/09/xmldsig#sha1", "SHA-1");
        map.put("http://www.w3.org/2001/04/xmlenc#sha256", "SHA-256");
        map.put("http://www.w3.org/2001/04/xmlenc#sha512", "SHA-512");
        map.put("http://www.w3.org/2001/04/xmldsig-more#sha384", "SHA-384");
        digestAlgorithm = Collections.unmodifiableMap(map);
    }


    public String getNameByURI(String uri){
            try{
                return digestAlgorithm.get(uri);
            }catch (EnumConstantNotPresentException e) {
                System.out.println("ERROR: Not supported digest algorithm URI: "  + uri);
            }
            return null;
        }
}
