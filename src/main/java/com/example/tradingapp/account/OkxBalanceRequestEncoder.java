package com.example.tradingapp.account;

import com.example.tradingapp.okx.trading.encoder.OkxUriAndHeaderEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OkxBalanceRequestEncoder {

    private final OkxUriAndHeaderEncoder uriAndHeaderEncoder;

    @Value("${okx.symbols.enabled}")
    private String currencies;

    public HttpRequestBase encode() throws UnsupportedEncodingException {
        String path = "/api/v5/account/balance";

        if (!currencies.isBlank()) {
            path = path + "?ccy=" + currencies;
        }

        log.info("PATH {}", path);
        return uriAndHeaderEncoder.encode("GET", path, "");
    }
}
