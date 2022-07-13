package com.example.tradingapp.trading;

import com.example.tradingapp.trading.model.OrderResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OrderRequestDecoderImpl implements OrderResponseDecoder {
    @Override
    public OrderResponseStatus decode(CloseableHttpResponse httpResponse) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        String result = EntityUtils.toString(entity);
        log.warn("Order response entity {}", result);
        return null;
    }
}
