package validation.utils;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.jce.provider.X509CertificateObject;
import org.bouncycastle.util.encoders.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateParsingException;

public class CertificateUtils {

    public static X509CertificateObject convertToCertificate(String textContent) {
        try (ASN1InputStream inputStream = new ASN1InputStream(new ByteArrayInputStream(Base64.decode(textContent)));) {
            ASN1Sequence sequence = (ASN1Sequence) inputStream.readObject();
            return new X509CertificateObject(Certificate.getInstance(sequence));
        } catch (CertificateParsingException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
