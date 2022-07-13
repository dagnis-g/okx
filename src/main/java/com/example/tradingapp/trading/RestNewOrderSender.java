package com.example.tradingapp.trading;

import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RestNewOrderSender implements NewOrderSender {

    private final OrderRequestEncoder orderRequestEncoder;
    private final OrderResponseDecoder orderResponseDecoder;

    @Override
    public OrderResponseStatus send(OrderRequest orderRequest) throws IOException {
        // prepare request based on orderRequest
        HttpRequestBase httpRequest = orderRequestEncoder.encode(orderRequest);
        // send
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // receive response
        CloseableHttpResponse response = httpClient.execute(httpRequest);
        // decode with OrderResponseDecoder

        // return OrderResponseStatus with success=true or success=false depending on status code

        return OrderResponseStatus.builder().build(); // todo replace
    }
}
