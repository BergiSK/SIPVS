package validation.check.checkers;

import it.svario.xpathapi.jaxp.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import validation.check.DigestValueCheck;
import validation.utils.XPathProvider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import org.apache.xml.security.c14n.Canonicalizer;

public class SignedInfoDigestValueChecker implements Checker {
    private static final String CN14 = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";

    static{
        Canonicalizer.registerDefaultAlgorithms();
    }

    @Override
    public boolean check(File file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        XPathProvider xpathtProvider = new XPathProvider();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList references =
                    XPathAPI.selectNodeList(document.getDocumentElement(),
                            xpathtProvider.getReferencePath());
            for (int i=0; i < references.getLength(); i++){
                new DigestValueCheck(false).validateReference(document,(Element)references.item(i), "Manifest");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}
