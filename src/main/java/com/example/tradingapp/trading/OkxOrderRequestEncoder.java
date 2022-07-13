package com.example.tradingapp.trading;

import com.example.tradingapp.secrets.Secrets;
import com.example.tradingapp.trading.model.OkxOrderRequest;
import com.example.tradingapp.trading.model.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Slf4j
@Component
public class OkxOrderRequestEncoder implements OrderRequestEncoder {

    private final String timestamp = String.valueOf(Instant.now().truncatedTo(ChronoUnit.MILLIS));
    private final String PATH = "/api/v5/trade/order";

    private final String API_KEY = Secrets.API_KEY;
    private final String SECRET_KEY = Secrets.SECRET_KEY;
    private final String PASSPRHASE = Secrets.PASSPHRASE;
    private final ObjectMapper mapper = new ObjectMapper();
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public HttpRequestBase encode(OrderRequest orderRequest) throws JsonProcessingException, UnsupportedEncodingException {
        OkxOrderRequest okxOrderRequest = modelMapper.map(orderRequest, OkxOrderRequest.class);
        okxOrderRequest.setTradeMode("cash");
        String orderJson = mapper.writeValueAsString(okxOrderRequest);
        log.info("OkxOrderRequest {}", orderJson);

        String hmacUri = timestamp + "POST" + PATH + orderJson;
        String okAccessSign = encodeAccessSign(hmacUri);

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

    public String encodeAccessSign(String hmacUri) {
        byte[] hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, SECRET_KEY).hmac(hmacUri);
        return Base64.getEncoder().encodeToString(hmac);
    }
}
