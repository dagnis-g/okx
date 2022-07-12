package com.example.tradingapp.trading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;

@Component
@RequiredArgsConstructor
public class RestNewOrderSender implements NewOrderSender {

    private final OrderRequestEncoder orderRequestEncoder;
    private final OrderResponseDecoder orderResponseDecoder;

    @Override
    public OrderResponseStatus send(OrderRequest orderRequest) {
        // prepare request based on orderRequest
        HttpRequest httpRequest = orderRequestEncoder.encode(orderRequest);

        // send
        // receive response
        // decode with OrderResponseDecoder
        // return OrderResponseStatus with success=true or success=false depending on status code

        return OrderResponseStatus.builder().build(); // todo replace
    }
}
