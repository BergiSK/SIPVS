package form.services;

import form.adapter.DitecTimeStampAdapter;
import org.apache.log4j.Logger;
import org.bouncycastle.tsp.TimeStampResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
public class SigningServiceImpl implements SigningService {

    private static Logger log = Logger.getLogger(SigningServiceImpl.class);

    private static final String XSD_PATH = "/static/Form.xsd";

    private static final String XSL_PATH = "/static/show.xsl";

    private static final String XML_PATH = "c:/tempXml.xml";

    private static final String SIGNED_XML_PATH = "c:/tempSignedXml.xml";

    private DitecTimeStampAdapter adapter = new DitecTimeStampAdapter();

    @Override
    public String getXml() {
        try {
            return Files.lines(new File(XML_PATH).toPath()).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getXsl() {
        try {
            return Files.lines(new ClassPathResource(XSL_PATH).getFile().toPath()).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getXsd() {
        try {
            return Files.lines(new ClassPathResource(XSD_PATH).getFile().toPath()).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void saveXml(String signedXml) {
        saveWithTimeStamp(signedXml);
    }

    private void saveWithTimeStamp(String signedXml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(signedXml)));

            Node signatureValue = document.getElementsByTagName("ds:SignatureValue").item(0);
            if (signatureValue == null) {
                log.error("Can't find ds:SignatureValue tag!");
            }

            byte request[] = adapter.createTSRequest(Base64.getEncoder()
                    .encode(signatureValue.getTextContent().getBytes()));
            String result = adapter.getTimeStampData(request);
            TimeStampResponse response = adapter.getTSResponse(result);

            Node qualifyingProperties = document.getElementsByTagName("xades:QualifyingProperties").item(0);
            if (qualifyingProperties == null) {
                System.err.println("Can't find xades:QualifyingProperties tag!");
            }

            String timestampToken = new String(Base64.getEncoder().encode(response.getTimeStampToken().getEncoded()));
            createXmlElements(document, timestampToken, qualifyingProperties);
            saveDomDocument(document);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void saveDomDocument(Document document) throws TransformerException {
        DOMSource source = new DOMSource(document);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File(SIGNED_XML_PATH));
        transformer.transform(source, output);
    }

    private void createXmlElements(Document document, String timestampToken, Node qualifyingProperties) {
        Element unsignedProperties = document.createElement("xades:UnsignedProperties");
        Element unsignedSignatureProperties = document.createElement("xades:UnsignedSignatureProperties");
        Element signatureTimestamp = document.createElement("xades:SignatureTimeStamp");
        Element encapsulatedTimeStamp = document.createElement("xades:EncapsulatedTimeStamp");
        unsignedProperties.appendChild(unsignedSignatureProperties);
        unsignedSignatureProperties.appendChild(signatureTimestamp);
        signatureTimestamp.appendChild(encapsulatedTimeStamp);
        Text timestampNode = document.createTextNode(timestampToken);
        encapsulatedTimeStamp.appendChild(timestampNode);
        qualifyingProperties.appendChild(unsignedProperties);
    }
}
