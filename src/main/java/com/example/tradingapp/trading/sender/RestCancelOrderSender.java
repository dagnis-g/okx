package com.example.tradingapp.trading.sender;

import com.example.tradingapp.trading.decoder.OkxCancelOrderResponseDecoder;
import com.example.tradingapp.trading.encoder.OkxCancelOrderRequestEncoder;
import com.example.tradingapp.trading.model.request.OkxCancelOrderRequest;
import com.example.tradingapp.trading.model.response.OrderResponseStatus;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RestCancelOrderSender implements CancelOrderSender {

    private final OkxCancelOrderRequestEncoder orderRequestEncoder;
    private final OkxCancelOrderResponseDecoder orderResponseDecoder;

    @Override
    public OrderResponseStatus cancel(OkxCancelOrderRequest cancelRequest) throws IOException {

        HttpRequestBase httpRequest = orderRequestEncoder.encode(cancelRequest);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpRequest);
        OrderResponseStatus status = orderResponseDecoder.decode(response);

        return status;

    }
}
