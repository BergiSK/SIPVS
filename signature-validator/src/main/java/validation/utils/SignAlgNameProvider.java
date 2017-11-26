package validation.utils;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SignAlgNameProvider {

    private Map<String,String> signAlgorithm;

    public SignAlgNameProvider(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("http://www.w3.org/2000/09/xmldsig#dsa-sha1", "SHA1withDSA");
        map.put("http://www.w3.org/2000/09/xmldsig#rsa-sha1", "SHA1withRSA/ISO9796-2");
        map.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", "SHA256withRSA");
        map.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha384", "SHA384withRSA");
        map.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512", "SHA512withRSA");
        signAlgorithm = Collections.unmodifiableMap(map);
    }

    public String getNameByURI(String uri){
        try{
            return signAlgorithm.get(uri);
        }catch (EnumConstantNotPresentException e) {
            System.out.println("ERROR: Not supported sign algorithm URI: "  + uri);
        }
        return null;
    }
}
