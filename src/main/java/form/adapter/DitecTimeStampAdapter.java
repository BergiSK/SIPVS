package form.adapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.tsp.TSPAlgorithms;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;

import static java.net.HttpURLConnection.HTTP_OK;

public class DitecTimeStampAdapter {

    private static final String OCSP = "http://test.ditec.sk/timestampws/TS.aspx";

    private static Logger log = Logger.getLogger(DitecTimeStampAdapter.class);

    private TimeStampRequestGenerator requestGenerator = new TimeStampRequestGenerator();

    public String getTimeStampDataApacheApi(byte[] request) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(OCSP);

        httpPost.addHeader("Content-type", "application/timestamp-query");
//        httpPost.addHeader("Content-length", String.valueOf(request.length));
        ByteArrayEntity entity = new ByteArrayEntity(request, ContentType.create("application/timestamp-query"));
        entity.setContentEncoding("base64");
        httpPost.setEntity(entity);
        HttpResponse response = client.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    public String getTimeStampData(byte[] request) {
        OutputStream out;
        HttpURLConnection con;

        try {
            URL url = new URL(OCSP);
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-type", "application/timestamp-query");
            con.setRequestProperty("Content-length", String.valueOf(request.length));
            out = con.getOutputStream();
            out.write(request);
            out.flush();
            if (con.getResponseCode() != HTTP_OK) {
                throw new IOException("HTTP Error: " + con.getResponseCode() + " - " + con.getResponseMessage());
            }

            InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] createTSRequest(byte[] data) {
        TimeStampRequest timeStampRequest = requestGenerator.generate(TSPAlgorithms.SHA256, data);
        try {
            return timeStampRequest.getEncoded();
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
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
