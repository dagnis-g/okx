package com.example.tradingapp.trading;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderRequestDecoderImpl implements OrderResponseDecoder {
    @Override
    public OrderResponseStatus decode(CloseableHttpResponse httpResponse) {
        return null;
    }
}
