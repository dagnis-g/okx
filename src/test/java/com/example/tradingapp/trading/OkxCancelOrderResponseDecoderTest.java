package com.example.tradingapp.trading;

import com.example.tradingapp.okx.trading.decoder.OkxCancelOrderResponseDecoder;
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
public class OkxCancelOrderResponseDecoderTest {

    @Autowired
    OkxCancelOrderResponseDecoder decoder;

    @Test
    void shouldDecodeCancelledOrderResponse() throws IOException {
        String jsonCancelled = "{" +
                "    \"code\":\"0\"," +
                "    \"msg\":\"\"," +
                "    \"data\":[" +
                "        {" +
                "            \"clOrdId\":\"oktswap6\"," +
                "            \"ordId\":\"12345689\"," +
                "            \"sCode\":\"0\"," +
                "            \"sMsg\":\"\"" +
                "        }" +
                "    ]" +
                "}";
        HttpResponseFactory factory = new DefaultHttpResponseFactory();
        HttpResponse response = factory.newHttpResponse(
                new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null), null);
        response.setEntity(new StringEntity(jsonCancelled));

        var orderResponse = decoder.decode(response);

        assertThat(orderResponse.getOrderId()).isEqualTo("12345689");
        assertThat(orderResponse.isSuccess()).isEqualTo(true);
        assertThat(orderResponse.getErrorCode()).isEqualTo("0");
        assertThat(orderResponse.getErrorMessage()).isEqualTo("");
    }

    @Test
    void shouldDecodeFailedCancelOrder() throws IOException {
        String jsonFailedCancel = "{" +
                "    \"code\":\"1\"," +
                "    \"msg\":\"\"," +
                "    \"data\":[" +
                "        {" +
                "            \"clOrdId\":\"oktswap6\"," +
                "            \"ordId\":\"12345689\"," +
                "            \"sCode\":\"1\"," +
                "            \"sMsg\":\"Order not cancelled because reasons\"" +
                "        }" +
                "    ]" +
                "}";

        HttpResponseFactory factory = new DefaultHttpResponseFactory();
        HttpResponse response = factory.newHttpResponse(
                new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null), null);
        response.setEntity(new StringEntity(jsonFailedCancel));

        var orderResponse = decoder.decode(response);

        assertThat(orderResponse.getOrderId()).isEqualTo("12345689");
        assertThat(orderResponse.isSuccess()).isEqualTo(false);
        assertThat(orderResponse.getErrorCode()).isEqualTo("1");
        assertThat(orderResponse.getErrorMessage()).isEqualTo("Order not cancelled because reasons");
    }

}
