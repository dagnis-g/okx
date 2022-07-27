package com.example.tradingapp.trading.encoder;

import com.example.tradingapp.secrets.OkxSecrets;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Component
public class OkxUriAndHeaderEncoder {

    private final String API_KEY = OkxSecrets.API_KEY;
    private final String SECRET_KEY = OkxSecrets.SECRET_KEY;
    private final String PASSPRHASE = OkxSecrets.PASSPHRASE;
    private final String URL_PATH = "https://www.okx.com";

    public HttpRequestBase encode(String requestType, String path, String orderJson) throws UnsupportedEncodingException {
        String timestamp = String.valueOf(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        String hmacUri = timestamp + requestType.toUpperCase() + path + orderJson;
        String okAccessSign = encodeAccessSign(hmacUri);
        StringEntity stringEntity = new StringEntity(orderJson);

        if (requestType.equalsIgnoreCase("post")) {
            HttpPost request = new HttpPost(URL_PATH + path);
            request.setEntity(stringEntity);
            addHeaders(request, okAccessSign, timestamp);

            return request;
        } else if (requestType.equalsIgnoreCase("get")) {
            HttpGet request = new HttpGet(URL_PATH + path);
            addHeaders(request, okAccessSign, timestamp);

            return request;
        } else {
            throw new RuntimeException("Incorrect HTTP request type: " + requestType);
        }

    }

    private void addHeaders(HttpRequestBase request, String okAccessSign, String timestamp) {
        request.addHeader("accept", "application/json");
        request.addHeader("Content-type", "application/json");
        request.addHeader("OK-ACCESS-KEY", API_KEY);
        request.addHeader("OK-ACCESS-SIGN", okAccessSign);
        request.addHeader("OK-ACCESS-PASSPHRASE", PASSPRHASE);
        request.addHeader("OK-ACCESS-TIMESTAMP", timestamp);
        request.addHeader("x-simulated-trading", "1");
    }

    private String encodeAccessSign(String hmacUri) {
        byte[] hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, SECRET_KEY).hmac(hmacUri);
        return Base64.getEncoder().encodeToString(hmac);
    }
}
