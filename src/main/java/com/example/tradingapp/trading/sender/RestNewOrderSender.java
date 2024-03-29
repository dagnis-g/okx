package com.example.tradingapp.trading.sender;

import com.example.tradingapp.trading.decoder.OkxOrderResponseDecoder;
import com.example.tradingapp.trading.encoder.OkxOrderRequestEncoder;
import com.example.tradingapp.trading.model.request.OrderRequest;
import com.example.tradingapp.trading.model.response.OrderResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestNewOrderSender implements NewOrderSender {

    private final OkxOrderRequestEncoder orderRequestEncoder;
    private final OkxOrderResponseDecoder orderResponseDecoder;

    @Override
    public OrderResponseStatus send(OrderRequest orderRequest) throws IOException {
        // prepare request based on orderRequest
        HttpRequestBase httpRequest = orderRequestEncoder.encode(orderRequest);
        // send
        // receive response
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpRequest);
        // decode with OrderResponseDecoder
        OrderResponseStatus status = orderResponseDecoder.decode(response);
        log.info("ResponseStatus: {}", status);
        // return OrderResponseStatus with success=true or success=false depending on status code
        return status;
    }
}
