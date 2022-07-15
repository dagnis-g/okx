package com.example.tradingapp;

import com.example.tradingapp.secrets.Secrets;
import com.example.tradingapp.trading.decoder.OkxOrderResponseDecoder;
import com.example.tradingapp.trading.encoder.OkxOrderRequestEncoder;
import com.example.tradingapp.trading.model.enums.OrderType;
import com.example.tradingapp.trading.model.enums.Side;
import com.example.tradingapp.trading.model.request.OrderRequest;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TradingAppApplicationTests {

    @Autowired
    OkxOrderRequestEncoder encoder;
    @Autowired
    OkxOrderResponseDecoder decoder;

    @Test
    void shouldAddHeaders() throws IOException {
        var orderReq = OrderRequest.builder()
                .symbol("Btc")
                .side(Side.BUY)
                .type(OrderType.LIMIT)
                .price(100)
                .quantity(20)
                .build();

        HttpPost encodedReq = (HttpPost) encoder.encode(orderReq);

        assertThat(Arrays.toString(encodedReq.getHeaders("OK-ACCESS-KEY")))
                .isEqualTo("[OK-ACCESS-KEY: " + Secrets.API_KEY + "]");
        assertThat(Arrays.toString(encodedReq.getHeaders("accept")))
                .isEqualTo("[accept: application/json]");
        assertThat(Arrays.toString(encodedReq.getHeaders("OK-ACCESS-PASSPHRASE")))
                .isEqualTo("[OK-ACCESS-PASSPHRASE: " + Secrets.PASSPHRASE + "]");
        assertThat(Arrays.toString(encodedReq.getHeaders("x-simulated-trading")))
                .isEqualTo("[x-simulated-trading: " + 1 + "]");
        assertThat(Arrays.toString(encodedReq.getHeaders("Content-type")))
                .isEqualTo("[Content-type: " + "application/json" + "]");

    }

    @Test
    void shouldCreateCorrectJsonBody() throws IOException {
        var orderReq = OrderRequest.builder()
                .symbol("Btc")
                .side(Side.BUY)
                .type(OrderType.LIMIT)
                .price(100)
                .quantity(20)
                .build();

        HttpPost encodedReq = (HttpPost) encoder.encode(orderReq);
        HttpEntity entity = encodedReq.getEntity();
        String stringEntity = EntityUtils.toString(entity);

        assertThat(stringEntity)
                .isEqualTo("{\"side\":\"buy\"," +
                        "\"instId\":\"Btc\"," +
                        "\"tdMode\":\"cash\"," +
                        "\"ordType\":\"limit\"," +
                        "\"px\":100.0," +
                        "\"sz\":20.0}");
    }

}
