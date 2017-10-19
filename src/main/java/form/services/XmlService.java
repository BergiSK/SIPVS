package form.services;

import org.example.sipvs.Team;

import javax.xml.bind.MarshalException;
import java.io.ByteArrayOutputStream;

public interface XmlService {

    ByteArrayOutputStream getXmlStream(Team team);

    void saveXml(Team team);

    void saveXml(String signedXml);

    String isXmlValid(Team team) throws MarshalException;

    String getXml();

    String getXsl();

    String getXsd();

}
