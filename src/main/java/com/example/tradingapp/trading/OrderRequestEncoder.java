package com.example.tradingapp.trading;

import java.net.http.HttpRequest;

public interface OrderRequestEncoder {

    HttpRequest encode(OrderRequest orderRequest);

}
