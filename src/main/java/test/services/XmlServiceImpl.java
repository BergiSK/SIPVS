package test.services;

import org.example.sipvs.ObjectFactory;
import org.example.sipvs.User;
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
    public void saveXml(User user) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("org.example.sipvs",
                    org.example.sipvs.ObjectFactory.class.getClassLoader());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            ObjectFactory factory = new ObjectFactory();
            JAXBElement<User> jaxbUser = factory.createUser(user);
            jaxbMarshaller.marshal(jaxbUser, System.out);
            System.out.println();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void validateXml(User user) throws MarshalException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("org.example.sipvs",
                    ObjectFactory.class.getClassLoader());
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("./src/main/xsd/Form.xsd"));

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setSchema(schema);
            ObjectFactory factory = new ObjectFactory();
            JAXBElement<User> jaxbUser = factory.createUser(user);
            marshaller.marshal(jaxbUser, new DefaultHandler());

        } catch (MarshalException e) {
            throw e;
        } catch (JAXBException | SAXException  e) {
            e.printStackTrace();
        }

    }
}
