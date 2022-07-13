package com.example.tradingapp.trading;

import com.example.tradingapp.trading.model.OrderResponseStatus;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

public interface OrderResponseDecoder {

    OrderResponseStatus decode(CloseableHttpResponse httpResponse) throws IOException;

}
