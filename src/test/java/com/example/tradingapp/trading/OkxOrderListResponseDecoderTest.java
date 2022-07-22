package com.example.tradingapp.trading;

import com.example.tradingapp.trading.decoder.OkxOrderListResponseDecoder;
import com.example.tradingapp.trading.model.enums.OkxOrderStatus;
import com.example.tradingapp.trading.model.enums.OrderType;
import com.example.tradingapp.trading.model.enums.Side;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.message.BasicStatusLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OkxOrderListResponseDecoderTest {

    @Autowired
    OkxOrderListResponseDecoder decoder;

    @Test
    void shouldDecodeOpenOrders() throws IOException {
        HttpResponseFactory factory = new DefaultHttpResponseFactory();

        HttpResponse response = factory.newHttpResponse(
                new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null), null);

        response.setEntity(new StringEntity(json));

        var openOrders = decoder.decode(response);

        var order1 = openOrders.getData().get(0);
        var order2 = openOrders.getData().get(1);

        assertThat(openOrders.getData().size()).isEqualTo(2);

        assertThat(order1.getId()).isEqualTo("467740559887708160");
        assertThat(order1.getStatus()).isEqualTo(OkxOrderStatus.Live);
        assertThat(order1.getSymbol()).isEqualTo("BTC-USDT");
        assertThat(order1.getSide()).isEqualTo(Side.BUY);
        assertThat(order1.getType()).isEqualTo(OrderType.LIMIT);
        assertThat(order1.getPrice()).isEqualTo(1);
        assertThat(order1.getQuantity()).isEqualTo(0.0001);

        assertThat(order2.getId()).isEqualTo("467740540212228096");
        assertThat(order2.getStatus()).isEqualTo(OkxOrderStatus.Live);
        assertThat(order2.getSymbol()).isEqualTo("BTC-USDT");
        assertThat(order2.getSide()).isEqualTo(Side.BUY);
        assertThat(order2.getType()).isEqualTo(OrderType.LIMIT);
        assertThat(order2.getPrice()).isEqualTo(1);
        assertThat(order2.getQuantity()).isEqualTo(0.0001);

    }

    private String json = "{" +
            "\"code\": \"0\"," +
            "\"data\": [" +
            "{" +
            "\"accFillSz\": \"0\"," +
            "\"avgPx\": \"\"," +
            "\"cTime\": \"1657790039676\"," +
            "\"category\": \"normal\"," +
            "\"ccy\": \"\"," +
            "\"clOrdId\": \"\"," +
            "\"fee\": \"0\"," +
            "\"feeCcy\": \"BTC\"," +
            "\"fillPx\": \"\"," +
            "\"fillSz\": \"0\"," +
            "\"fillTime\": \"\"," +
            "\"instId\": \"BTC-USDT\"," +
            "\"instType\": \"SPOT\"," +
            "\"lever\": \"\"," +
            "\"ordId\": \"467740559887708160\"," +
            "\"ordType\": \"limit\"," +
            "\"pnl\": \"0\"," +
            "\"posSide\": \"net\"," +
            "\"px\": \"1\"," +
            "\"rebate\": \"0\"," +
            "\"rebateCcy\": \"USDT\"," +
            "\"side\": \"buy\"," +
            "\"slOrdPx\": \"\"," +
            "\"slTriggerPx\": \"\"," +
            "\"slTriggerPxType\": \"\"," +
            "\"source\": \"\"," +
            "\"state\": \"live\"," +
            "\"sz\": \"0.0001\"," +
            "\"tag\": \"\"," +
            "\"tdMode\": \"cash\"," +
            "\"tgtCcy\": \"\"," +
            "\"tpOrdPx\": \"\"," +
            "\"tpTriggerPx\": \"\"," +
            "\"tpTriggerPxType\": \"\"," +
            "\"tradeId\": \"\"," +
            "\"uTime\": \"1657790039676\"" +
            "}," +
            "{" +
            "\"accFillSz\": \"0\"," +
            "\"avgPx\": \"\"," +
            "\"cTime\": \"1657790034985\"," +
            "\"category\": \"normal\"," +
            "\"ccy\": \"\"," +
            "\"clOrdId\": \"\"," +
            "\"fee\": \"0\"," +
            "\"feeCcy\": \"BTC\"," +
            "\"fillPx\": \"\"," +
            "\"fillSz\": \"0\"," +
            "\"fillTime\": \"\"," +
            "\"instId\": \"BTC-USDT\"," +
            "\"instType\": \"SPOT\"," +
            "\"lever\": \"\"," +
            "\"ordId\": \"467740540212228096\"," +
            "\"ordType\": \"limit\"," +
            "\"pnl\": \"0\"," +
            "\"posSide\": \"net\"," +
            "\"px\": \"1\"," +
            "\"rebate\": \"0\"," +
            "\"rebateCcy\": \"USDT\"," +
            "\"side\": \"buy\"," +
            "\"slOrdPx\": \"\"," +
            "\"slTriggerPx\": \"\"," +
            "\"slTriggerPxType\": \"\"," +
            "\"source\": \"\"," +
            "\"state\": \"live\"," +
            "\"sz\": \"0.0001\"," +
            "\"tag\": \"\"," +
            "\"tdMode\": \"cash\"," +
            "\"tgtCcy\": \"\"," +
            "\"tpOrdPx\": \"\"," +
            "\"tpTriggerPx\": \"\"," +
            "\"tpTriggerPxType\": \"\"," +
            "\"tradeId\": \"\"," +
            "\"uTime\": \"1657790034985\"" +
            "}" +
            "]," +
            "\"msg\": \"\"" +
            "}";
}
