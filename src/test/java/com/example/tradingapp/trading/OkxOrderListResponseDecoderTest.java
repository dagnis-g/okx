package com.example.tradingapp.trading;

import com.example.tradingapp.tracker.OrderStatus;
import com.example.tradingapp.trading.decoder.OkxOrderListResponseDecoder;
import com.example.tradingapp.trading.model.OrderType;
import com.example.tradingapp.trading.model.Side;
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
        assertThat(order1.getStatus()).isEqualTo(OrderStatus.Live);
        assertThat(order1.getSymbol()).isEqualTo("BTC-USDT");
        assertThat(order1.getSide()).isEqualTo(Side.BUY);
        assertThat(order1.getType()).isEqualTo(OrderType.LIMIT);
        assertThat(order1.getPrice()).isEqualTo(1);
        assertThat(order1.getQuantity()).isEqualTo(0.0001);

        assertThat(order2.getId()).isEqualTo("467740540212228096");
        assertThat(order2.getStatus()).isEqualTo(OrderStatus.Live);
        assertThat(order2.getSymbol()).isEqualTo("BTC-USDT");
        assertThat(order2.getSide()).isEqualTo(Side.BUY);
        assertThat(order2.getType()).isEqualTo(OrderType.LIMIT);
        assertThat(order2.getPrice()).isEqualTo(1);
        assertThat(order2.getQuantity()).isEqualTo(0.0001);

    }

    private String json = "{\n" +
            "\t\"code\": \"0\",\n" +
            "\t\"data\": [\n" +
            "\t\t{\n" +
            "\t\t\t\"accFillSz\": \"0\",\n" +
            "\t\t\t\"avgPx\": \"\",\n" +
            "\t\t\t\"cTime\": \"1657790039676\",\n" +
            "\t\t\t\"category\": \"normal\",\n" +
            "\t\t\t\"ccy\": \"\",\n" +
            "\t\t\t\"clOrdId\": \"\",\n" +
            "\t\t\t\"fee\": \"0\",\n" +
            "\t\t\t\"feeCcy\": \"BTC\",\n" +
            "\t\t\t\"fillPx\": \"\",\n" +
            "\t\t\t\"fillSz\": \"0\",\n" +
            "\t\t\t\"fillTime\": \"\",\n" +
            "\t\t\t\"instId\": \"BTC-USDT\",\n" +
            "\t\t\t\"instType\": \"SPOT\",\n" +
            "\t\t\t\"lever\": \"\",\n" +
            "\t\t\t\"ordId\": \"467740559887708160\",\n" +
            "\t\t\t\"ordType\": \"limit\",\n" +
            "\t\t\t\"pnl\": \"0\",\n" +
            "\t\t\t\"posSide\": \"net\",\n" +
            "\t\t\t\"px\": \"1\",\n" +
            "\t\t\t\"rebate\": \"0\",\n" +
            "\t\t\t\"rebateCcy\": \"USDT\",\n" +
            "\t\t\t\"side\": \"buy\",\n" +
            "\t\t\t\"slOrdPx\": \"\",\n" +
            "\t\t\t\"slTriggerPx\": \"\",\n" +
            "\t\t\t\"slTriggerPxType\": \"\",\n" +
            "\t\t\t\"source\": \"\",\n" +
            "\t\t\t\"state\": \"live\",\n" +
            "\t\t\t\"sz\": \"0.0001\",\n" +
            "\t\t\t\"tag\": \"\",\n" +
            "\t\t\t\"tdMode\": \"cash\",\n" +
            "\t\t\t\"tgtCcy\": \"\",\n" +
            "\t\t\t\"tpOrdPx\": \"\",\n" +
            "\t\t\t\"tpTriggerPx\": \"\",\n" +
            "\t\t\t\"tpTriggerPxType\": \"\",\n" +
            "\t\t\t\"tradeId\": \"\",\n" +
            "\t\t\t\"uTime\": \"1657790039676\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"accFillSz\": \"0\",\n" +
            "\t\t\t\"avgPx\": \"\",\n" +
            "\t\t\t\"cTime\": \"1657790034985\",\n" +
            "\t\t\t\"category\": \"normal\",\n" +
            "\t\t\t\"ccy\": \"\",\n" +
            "\t\t\t\"clOrdId\": \"\",\n" +
            "\t\t\t\"fee\": \"0\",\n" +
            "\t\t\t\"feeCcy\": \"BTC\",\n" +
            "\t\t\t\"fillPx\": \"\",\n" +
            "\t\t\t\"fillSz\": \"0\",\n" +
            "\t\t\t\"fillTime\": \"\",\n" +
            "\t\t\t\"instId\": \"BTC-USDT\",\n" +
            "\t\t\t\"instType\": \"SPOT\",\n" +
            "\t\t\t\"lever\": \"\",\n" +
            "\t\t\t\"ordId\": \"467740540212228096\",\n" +
            "\t\t\t\"ordType\": \"limit\",\n" +
            "\t\t\t\"pnl\": \"0\",\n" +
            "\t\t\t\"posSide\": \"net\",\n" +
            "\t\t\t\"px\": \"1\",\n" +
            "\t\t\t\"rebate\": \"0\",\n" +
            "\t\t\t\"rebateCcy\": \"USDT\",\n" +
            "\t\t\t\"side\": \"buy\",\n" +
            "\t\t\t\"slOrdPx\": \"\",\n" +
            "\t\t\t\"slTriggerPx\": \"\",\n" +
            "\t\t\t\"slTriggerPxType\": \"\",\n" +
            "\t\t\t\"source\": \"\",\n" +
            "\t\t\t\"state\": \"live\",\n" +
            "\t\t\t\"sz\": \"0.0001\",\n" +
            "\t\t\t\"tag\": \"\",\n" +
            "\t\t\t\"tdMode\": \"cash\",\n" +
            "\t\t\t\"tgtCcy\": \"\",\n" +
            "\t\t\t\"tpOrdPx\": \"\",\n" +
            "\t\t\t\"tpTriggerPx\": \"\",\n" +
            "\t\t\t\"tpTriggerPxType\": \"\",\n" +
            "\t\t\t\"tradeId\": \"\",\n" +
            "\t\t\t\"uTime\": \"1657790034985\"\n" +
            "\t\t}\n" +
            "\t],\n" +
            "\t\"msg\": \"\"\n" +
            "}";
}
