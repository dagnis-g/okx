package com.example.tradingapp.trading;

import java.io.IOException;

public interface NewOrderSender {

    OrderResponseStatus send(OrderRequest orderRequest) throws IOException;
}
