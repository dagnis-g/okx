package com.example.tradingapp.trading;

import org.apache.http.client.methods.CloseableHttpResponse;

public interface OrderResponseDecoder {

    OrderResponseStatus decode(CloseableHttpResponse httpResponse);

}
