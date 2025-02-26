import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;

public class Utils {
    public static String getImage(String nasaUrl) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpGet request = new HttpGet(nasaUrl);

            CloseableHttpResponse response = httpClient.execute(request);

            NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);
            String imageUrl = answer.url;
            return imageUrl;
        } catch (IOException e) {
            return "";
        }
    }
}
