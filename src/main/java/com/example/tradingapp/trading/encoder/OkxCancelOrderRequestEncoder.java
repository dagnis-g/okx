package com.example.tradingapp.trading.encoder;

import com.example.tradingapp.trading.model.request.OkxCancelOrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OkxCancelOrderRequestEncoder {

    private final OkxUriAndHeaderEncoder uriAndHeaderEncoder;

    private final ObjectMapper mapper = new ObjectMapper();

    private final String PATH = "/api/v5/trade/cancel-order";
    
    public HttpRequestBase encode(OkxCancelOrderRequest request) throws JsonProcessingException, UnsupportedEncodingException {
        String requestJson = mapper.writeValueAsString(request);
        log.info("Cancel Order Request: {}", requestJson);
        return uriAndHeaderEncoder.encode("POST", PATH, requestJson);
    }
}
