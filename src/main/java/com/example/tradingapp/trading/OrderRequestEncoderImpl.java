package com.example.tradingapp.trading;

import com.example.tradingapp.secrets.Secrets;
import com.example.tradingapp.trading.model.OkxOrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Component
public class OrderRequestEncoderImpl implements OrderRequestEncoder {

    private final String timestamp = String.valueOf(Instant.now().truncatedTo(ChronoUnit.MILLIS));
    private final String PATH = "/api/v5/trade/order";

    private final String API_KEY = Secrets.API_KEY;
    private final String SECRET_KEY = Secrets.SECRET_KEY;
    private final String PASSPRHASE = Secrets.PASSPHRASE;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public HttpRequestBase encode(OkxOrderRequest orderRequest) throws JsonProcessingException, UnsupportedEncodingException {
        String orderJson = mapper.writeValueAsString(orderRequest);

        String hmacUri = timestamp + "POST" + PATH + orderJson;
        byte[] hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, SECRET_KEY).hmac(hmacUri);
        var okAccessSign = Base64.getEncoder().encodeToString(hmac);

        HttpPost request = new HttpPost("https://www.okx.com" + PATH);
        StringEntity stringEntity = new StringEntity(orderJson);
        request.setEntity(stringEntity);
        request.addHeader("accept", "application/json");
        request.addHeader("Content-type", "application/json");
        request.addHeader("OK-ACCESS-KEY", API_KEY);
        request.addHeader("OK-ACCESS-SIGN", okAccessSign);
        request.addHeader("OK-ACCESS-PASSPHRASE", PASSPRHASE);
        request.addHeader("OK-ACCESS-TIMESTAMP", timestamp);
        request.addHeader("x-simulated-trading", "1");

        return request;
    }
}
