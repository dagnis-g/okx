package com.example.tradingapp.trading;

import com.example.tradingapp.trading.model.OrderResponseStatus;
import org.apache.http.HttpResponse;

import java.io.IOException;

public interface OrderResponseDecoder {

    OrderResponseStatus decode(HttpResponse httpResponse) throws IOException;

}
