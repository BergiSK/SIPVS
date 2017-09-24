package test.services;

import org.example.sipvs.Team;

import javax.xml.bind.MarshalException;

public interface XmlService {

    void saveXml(Team team);

    boolean isXmlValid(Team team) throws MarshalException;

}
