package com.example.tradingapp.trading.decoder;

import com.example.tradingapp.trading.model.response.OkxOrderResponse;
import com.example.tradingapp.trading.model.response.OrderResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OkxOrderResponseDecoder implements OrderResponseDecoder {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public OrderResponseStatus decode(HttpResponse httpResponse) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        String entityString = EntityUtils.toString(entity);
        OkxOrderResponse response = mapper.readValue(entityString, OkxOrderResponse.class);
        log.info("OkxOrderResponse entity {}", response);

        return OrderResponseStatus.builder()
                .success(response.getCode() == 0)
                .orderId(response.getData().get(0).getOrderId())
                .errorCode(response.getData().get(0).getSuccessCode())
                .errorMessage(response.getData().get(0).getMessage())
                .build();
    }
}
