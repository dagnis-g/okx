package com.example.tradingapp.okx.trading.encoder;

import lombok.AllArgsConstructor;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@AllArgsConstructor
public class OkxOrderListRequestEncoder {

    private final OkxUriAndHeaderEncoder uriAndHeaderEncoder;

    private final String PATH = "/api/v5/trade/orders-pending";

    public HttpRequestBase encode() throws UnsupportedEncodingException {
        return uriAndHeaderEncoder.encode("get", PATH, "");
    }
}
