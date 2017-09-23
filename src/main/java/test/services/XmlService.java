package test.services;

import org.example.sipvs.User;

import javax.xml.bind.MarshalException;

public interface XmlService {

    void saveXml(User user);

    void validateXml(User user) throws MarshalException;

}
