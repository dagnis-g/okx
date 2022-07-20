package com.example.tradingapp.strategy;

import com.example.tradingapp.positions.OkxPositions;
import com.example.tradingapp.positions.OkxPositionsRequestEncoder;
import com.example.tradingapp.positions.OkxPositionsResponse;
import com.example.tradingapp.positions.OkxPositionsResponseDecoder;
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
public class GetAccountPositionsPolicy {

    private final OkxPositions okxPositions;
    private final OkxPositionsRequestEncoder encoder;
    private final OkxPositionsResponseDecoder decoder;

    public void getPositions() throws IOException {
        HttpRequestBase httpRequest = encoder.encode();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpRequest);

        OkxPositionsResponse positionsResponse = decoder.decode(response);

        if (positionsResponse.getCode().equals("0")) {
            for (var data : positionsResponse.getData()) {
                okxPositions.getPositions().put(data.getPositionId(), data);
            }
        }
        log.info("Current open future positions {}", okxPositions.getPositions());
    }

}
