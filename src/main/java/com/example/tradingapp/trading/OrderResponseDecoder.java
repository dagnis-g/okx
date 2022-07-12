package com.example.tradingapp.trading;

import java.net.http.HttpResponse;

public interface OrderResponseDecoder {

    OrderResponseStatus decode(HttpResponse httpResponse);

}
