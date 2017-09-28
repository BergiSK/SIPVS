package form.services;

import org.example.sipvs.Team;

import javax.xml.bind.MarshalException;
import java.io.ByteArrayOutputStream;

public interface XmlService {

    ByteArrayOutputStream getXmlStream(Team team);

    void saveXml(Team team);

    String isXmlValid(Team team) throws MarshalException;

}
