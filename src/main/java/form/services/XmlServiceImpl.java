package form.services;

import org.example.sipvs.ObjectFactory;
import org.example.sipvs.Team;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;

@Service
public class XmlServiceImpl implements XmlService {

    private static final String VALID = "Valid";

    private static final String XML_PATH = "c:/tempXml.xml";

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
}
