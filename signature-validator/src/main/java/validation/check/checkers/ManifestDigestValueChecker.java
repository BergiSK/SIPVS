package validation.check.checkers;

import it.svario.xpathapi.jaxp.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import validation.check.DigestValueCheck;
import validation.utils.XPathProvider;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathException;
import java.io.File;

public class ManifestDigestValueChecker implements Checker {

    public ManifestDigestValueChecker(){

    }

    @Override
    public boolean check(File file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        XPathProvider xpathtProvider = new XPathProvider();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList manifestReferences =
                    XPathAPI.selectNodeList(document.getDocumentElement(),
                            xpathtProvider.getManifestReferencesPath());
            for (int i=0; i < manifestReferences.getLength(); i++){
                new DigestValueCheck(true)
                        .validateReference(document,(Element)manifestReferences.item(i), null);
            }
            checkNumberOfReferencesInManifests(document, xpathtProvider);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;

    }

    private void checkNumberOfReferencesInManifests(Document document, XPathProvider xPathProvider) throws XPathException {
        NodeList manifests =
                XPathAPI.selectNodeList(document.getDocumentElement(),
                        xPathProvider.getManifestPath());
        for (int i=0; i < manifests.getLength(); i++) {
            NodeList nodeList = manifests.item(i).getChildNodes();
            if(nodeList.getLength() > 1 ){
                System.out.println("ERROR: Number of Reference nodes in manifest node is higher than 1.");
            }
            boolean referecesObject = false;
            for(int j = 0; j < nodeList.getLength(); j++){
                String uri = nodeList.item(j).getAttributes().getNamedItem("URI").getNodeValue().replace("#","");
                Node referencedNode = XPathAPI.selectSingleNode(document.getDocumentElement(),String.format("//*[@Id='%s']", uri));

                if(referencedNode.getNodeName().equals("ds:Object")){
                    referecesObject = true;
                }
            }
            if(!referecesObject){
                System.out.println("ERROR: manifest node must reference one ds:Object node");
            }
        }


    }
}
