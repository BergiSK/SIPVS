package test.services;

import org.example.sipvs.ObjectFactory;
import org.example.sipvs.Team;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

@Service
public class XmlServiceImpl implements XmlService {

    @Override
    public void saveXml(Team team) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("org.example.sipvs",
                    org.example.sipvs.ObjectFactory.class.getClassLoader());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            ObjectFactory factory = new ObjectFactory();
            JAXBElement<Team> jaxbTeam = factory.createTeam(team);
            jaxbMarshaller.marshal(jaxbTeam, new File("c:/tempXml.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isXmlValid(Team team) {
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
            return false;
        } catch (JAXBException | SAXException  e) {
            e.printStackTrace();
        }
        return true;
    }
}
