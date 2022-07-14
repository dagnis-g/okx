package com.example.tradingapp.strategy;

import com.example.tradingapp.tracker.Order;
import com.example.tradingapp.trading.OrderTracker;
import com.example.tradingapp.trading.decoder.OkxOrderListResponseDecoder;
import com.example.tradingapp.trading.encoder.OkxOrderListRequestEncoder;
import com.example.tradingapp.trading.model.OkxOrderListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenOrderPolicy {

    private final OkxOrderListRequestEncoder encoder;
    private final OkxOrderListResponseDecoder decoder;
    private final OrderTracker orderTracker;


    @EventListener(ApplicationReadyEvent.class)
    public void getOpenOrders() throws IOException {
        log.info("Sending GET open orders");

        HttpRequestBase httpRequest = encoder.encode();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpRequest);
        OkxOrderListResponse status = decoder.decode(response);

        for (var data : status.getData()) {
            var order = Order.builder()
                    .id(data.getId())
                    .status(data.getStatus())
                    .symbol(data.getSymbol())
                    .side(data.getSide())
                    .type(data.getType())
                    .price(data.getPrice())
                    .quantity(data.getQuantity())
                    .build();
            orderTracker.create(order);
        }

    }

}
