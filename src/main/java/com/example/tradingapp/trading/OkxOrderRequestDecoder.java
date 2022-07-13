package com.example.tradingapp.trading;

import com.example.tradingapp.trading.model.OkxResponse;
import com.example.tradingapp.trading.model.OrderResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OkxOrderRequestDecoder implements OrderResponseDecoder {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public OrderResponseStatus decode(CloseableHttpResponse httpResponse) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        String result = EntityUtils.toString(entity);
        OkxResponse response = mapper.readValue(result, OkxResponse.class);
        log.info("OkxResponse entity {}", response);

        return OrderResponseStatus.builder()
                .success(response.getCode() == 0)
                .orderId(response.getData().get(0).getOrderId())
                .errorCode(response.getData().get(0).getSuccessCode())
                .errorMessage(response.getData().get(0).getMessage())
                .build();
    }
}
