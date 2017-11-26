package validation.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SignedInfoReferencesProvider {

    private Map<String,String> signedInfoRefrences;

    public SignedInfoReferencesProvider(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("ds:KeyInfo", "http://www.w3.org/2000/09/xmldsig#Object");
        map.put("ds:SignatureProperties", "http://www.w3.org/2000/09/xmldsig#SignatureProperties");
        map.put("xades:SignedProperties", "http://uri.etsi.org/01903#SignedProperties");
        map.put("ds:Manifest", "http://www.w3.org/2000/09/xmldsig#Manifest");
        signedInfoRefrences = Collections.unmodifiableMap(map);
    }


    public String getNameByURI(String uri){
        try{
            return signedInfoRefrences.get(uri);
        }catch (EnumConstantNotPresentException e) {
            System.out.println("ERROR: Not supported digest algorithm URI: "  + uri);
        }
        return null;
    }
}
