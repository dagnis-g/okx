package com.example.tradingapp.positions;

import com.example.tradingapp.trading.encoder.OkxUriAndHeaderEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OkxPositionsRequestEncoder {

    private final OkxUriAndHeaderEncoder uriAndHeaderEncoder;

    public HttpRequestBase encode() throws UnsupportedEncodingException {
        String path = "/api/v5/account/positions?instType=FUTURES";

        log.info("Getting future positions");
        return uriAndHeaderEncoder.encode("GET", path, "");
    }
}
