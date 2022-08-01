package com.example.tradingapp.okx.trading.decoder;

import com.example.tradingapp.okx.trading.model.response.OrderResponseStatus;
import org.apache.http.HttpResponse;

import java.io.IOException;

public interface OrderResponseDecoder {

    OrderResponseStatus decode(HttpResponse httpResponse) throws IOException;

}
