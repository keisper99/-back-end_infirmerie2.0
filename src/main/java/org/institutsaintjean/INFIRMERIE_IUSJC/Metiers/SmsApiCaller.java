

package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SmsApiCaller {

    private static final String API_BASE_URL = "https://obitsms.com/api/v2/bulksms";
    private static final String API_KEY = "5V93fKgJlHYs56dZlSdsSDFdtA5MDssG";

    public static void sendSms(String recipientPhoneNumber, String message) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String apiUrl = buildApiUrl(recipientPhoneNumber, message);

        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            CloseableHttpResponse response = httpClient.execute(httpGet);

            // Process response if needed
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println(responseBody);

            response.close();
        } finally {
            httpClient.close();
        }
    }

    private static String buildApiUrl(String phoneNumber, String message) throws UnsupportedEncodingException {
        String encodedMessage = URLEncoder.encode(message, "UTF-8");
        return API_BASE_URL + "?key_api=" + API_KEY + "&sender=IUSJ-CLINIC&destination=237"+ phoneNumber + "&message=" + encodedMessage;
    }
}



