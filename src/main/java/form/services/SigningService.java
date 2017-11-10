package form.services;

public interface SigningService {

    String getXml();

    String getXsl();

    String getXsd();

    void saveXml(String signedXml);
}
