package form.services;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.bouncycastle.tsp.TSPAlgorithms;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;
import org.example.sipvs.ObjectFactory;
import org.example.sipvs.Team;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Service
public class XmlServiceImpl implements XmlService {

    private static final String VALID = "Valid";

    private static final String XSD_PATH = "/static/Form.xsd";

    private static final String XSL_PATH = "/static/show.xsl";

    private static final String XML_PATH = "c:/tempXml.xml";

    private static final String SIGNED_XML_PATH = "c:/tempSignedXml.xml";
    
    private static final String OCSP = "http://test.ditec.sk/timestampws/TS.aspx";
  

    @Override
    public ByteArrayOutputStream getXmlStream(Team team) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("org.example.sipvs",
                    org.example.sipvs.ObjectFactory.class.getClassLoader());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            ObjectFactory factory = new ObjectFactory();
            JAXBElement<Team> jaxbTeam = factory.createTeam(team);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            jaxbMarshaller.marshal(jaxbTeam, stream);
            return stream;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveXml(Team team) {
        ByteArrayOutputStream xmlStream = this.getXmlStream(team);

        try(OutputStream outputStream = new FileOutputStream(XML_PATH)) {
            xmlStream.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String OCSPconnection(byte[] request) {
    	
    	OutputStream out;
    	HttpURLConnection con;
    	
    	try {
	    	URL url = new URL(OCSP);
	        con = (HttpURLConnection) url.openConnection();
	        con.setDoOutput(true);
	        con.setDoInput(true);
	        con.setRequestMethod("POST");
	        con.setRequestProperty("Content-type", "application/timestamp-query");
	        con.setRequestProperty("Content-length", String.valueOf(request.length));
	        out = con.getOutputStream();
	        out.write(request);
	        out.flush();
	        if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
	               throw new IOException("HTTP Error: " + con.getResponseCode() + " - " + con.getResponseMessage());
	        }
	
	        InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            
            return result.toString();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    	
    }
    
    private byte[] createTSRequest(byte[] data) {
    	TimeStampRequestGenerator requestGenerator = new TimeStampRequestGenerator();
    	TimeStampRequest timeStampRequest = requestGenerator.generate(TSPAlgorithms.SHA256, data);
    	try {
    		byte request[] = timeStampRequest.getEncoded();
			return request;       
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    private TimeStampResponse createTSResponse(String result) {
        try {
        	TimeStampResponse response = new TimeStampResponse(Base64.getDecoder().decode(result));
			return response;
		}  catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    private void createXmlElemets(Document document, String timestampToken, Node qualifyingProperties) {
    	
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
    
    private StreamResult addTimestamp(String signedXml) {
    	
    	
    	byte request[];
    	TimeStampResponse response;
    	String result;
    	try {
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = factory.newDocumentBuilder();
	    	Document document = builder.parse(new InputSource(new StringReader(signedXml)));

	    	Node signatureValue = document.getElementsByTagName("ds:SignatureValue").item(0);
	    	if (signatureValue == null) {
                System.err.println("Can't find ds:SignatureValue tag!");
	    	}
	    	
	    	request = createTSRequest(Base64.getEncoder().encodeToString(signatureValue.getTextContent().getBytes()).getBytes());
	    	result = OCSPconnection(request);
	    	response = createTSResponse(result);
	    	
	    	 Node qualifyingProperties = document.getElementsByTagName("xades:QualifyingProperties").item(0);
	         if (qualifyingProperties == null) {
	        	 System.err.println("Can't find xades:QualifyingProperties tag!");
	         }
	         String timestampToken = new String(Base64.getEncoder().encode(response.getTimeStampToken().getEncoded()));

	         createXmlElemets(document,timestampToken,qualifyingProperties);
	         
	         DOMSource source = new DOMSource(document);
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         StringWriter outWriter = new StringWriter();
	         StreamResult streamResult = new StreamResult( outWriter );
	         transformer.transform(source, streamResult);
		
	         return streamResult;
    	 } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
    }
   
    @Override
    public void saveXml(String signedXml) {
    	
        try (PrintStream out = new PrintStream(new FileOutputStream(SIGNED_XML_PATH))) {
            out.print(addTimestamp(signedXml).getWriter());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String isXmlValid(Team team) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("org.example.sipvs",
                    ObjectFactory.class.getClassLoader());
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("./src/main/xsd/Form.xsd"));

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setSchema(schema);
            ObjectFactory factory = new ObjectFactory();
            JAXBElement<Team> jaxbTeam = factory.createTeam(team);
            marshaller.marshal(jaxbTeam, new DefaultHandler());

        } catch (MarshalException e) {
            System.out.print(e);
            return e.getCause().getMessage().split(": ")[1];
        } catch (JAXBException | SAXException  e) {
            e.printStackTrace();
        }
        return VALID;
    }

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
}
