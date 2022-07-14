package com.example.tradingapp;

import com.example.tradingapp.secrets.Secrets;
import com.example.tradingapp.trading.OkxOrderRequestDecoder;
import com.example.tradingapp.trading.OkxOrderRequestEncoder;
import com.example.tradingapp.trading.model.OrderRequest;
import com.example.tradingapp.trading.model.OrderType;
import com.example.tradingapp.trading.model.Side;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;

@SpringBootTest
public class TradingAppApplicationTests {

    @Autowired
    OkxOrderRequestEncoder encoder;
    @Autowired
    OkxOrderRequestDecoder decoder;

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

        Assertions.assertThat(Arrays.toString(encodedReq.getHeaders("OK-ACCESS-KEY")))
                .isEqualTo("[OK-ACCESS-KEY: " + Secrets.API_KEY + "]");
        Assertions.assertThat(Arrays.toString(encodedReq.getHeaders("accept")))
                .isEqualTo("[accept: application/json]");
        Assertions.assertThat(Arrays.toString(encodedReq.getHeaders("OK-ACCESS-PASSPHRASE")))
                .isEqualTo("[OK-ACCESS-PASSPHRASE: " + Secrets.PASSPHRASE + "]");
        Assertions.assertThat(Arrays.toString(encodedReq.getHeaders("x-simulated-trading")))
                .isEqualTo("[x-simulated-trading: " + 1 + "]");
        Assertions.assertThat(Arrays.toString(encodedReq.getHeaders("Content-type")))
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

        Assertions.assertThat(stringEntity)
                .isEqualTo("{\"side\":\"buy\"," +
                        "\"instId\":\"Btc\"," +
                        "\"tdMode\":\"cash\"," +
                        "\"ordType\":\"limit\"," +
                        "\"px\":100.0," +
                        "\"sz\":20.0}");
    }

    @Test
    void shouldEncodeCorrectAccessSign() {
        String toEncode = "daskjhgdaskjgdjashgdajshgdjashgdajshgdjashgdasjhgd";
        String encoded = encoder.encodeAccessSign(toEncode);

        Assertions.assertThat(encoded).isEqualTo("g7FZz9RJ0MYUUS249F2eowlLpS2FrM4uZrvthUXUzWY=");
    }
    
}
