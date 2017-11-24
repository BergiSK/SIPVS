package form.adapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.tsp.TimeStampResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;

public class DitecTimeStampAdapter {

    private static final String OCSP = "http://test.ditec.sk/timestampws/TS.aspx";

    private static Logger log = Logger.getLogger(DitecTimeStampAdapter.class);

    public String getTimeStampDataApacheApi(byte[] request) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(OCSP);

        httpPost.addHeader("Content-type", "application/timestamp-query");
        ByteArrayEntity entity = new ByteArrayEntity(request, ContentType.create("application/timestamp-query"));
        entity.setContentEncoding("base64");
        httpPost.setEntity(entity);
        HttpResponse response = client.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    public TimeStampResponse getTSResponse(String result) {
        try {
            TimeStampResponse response = new TimeStampResponse(Base64.getDecoder().decode(result));
            return response;
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
