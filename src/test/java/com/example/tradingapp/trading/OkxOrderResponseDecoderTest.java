package com.example.tradingapp.trading;

import com.example.tradingapp.okx.trading.decoder.OkxOrderResponseDecoder;
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
public class OkxOrderResponseDecoderTest {

    @Autowired
    OkxOrderResponseDecoder decoder;

    @Test
    void shouldDecodeSuccessfulOrder() throws IOException {
        HttpResponseFactory factory = new DefaultHttpResponseFactory();

        HttpResponse response = factory.newHttpResponse(
                new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null), null);

        response.setEntity(new StringEntity("{\"code\":\"0\"," +
                "\"data\":[{\"clOrdId\":\"\"," +
                "\"ordId\":\"467708712625516544\"," +
                "\"sCode\":\"0\"," +
                "\"sMsg\":\"\"," +
                "\"tag\":\"\"}]," +
                "\"msg\":\"\"}"));
        var status = decoder.decode(response);

        assertThat(status.isSuccess()).isEqualTo(true);
        assertThat(status.getOrderId()).isEqualTo("467708712625516544");
        assertThat(status.getErrorCode()).isEqualTo("0");
        assertThat(status.getErrorMessage()).isEqualTo("");
    }

    @Test
    void shouldDecodeUnsuccessfulOrder() throws IOException {
        HttpResponseFactory factory = new DefaultHttpResponseFactory();

        HttpResponse response = factory.newHttpResponse(
                new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null), null);

        response.setEntity(new StringEntity("{\"code\":\"1\"," +
                "\"data\":[{\"clOrdId\":\"\"," +
                "\"ordId\":\"\"," +
                "\"sCode\":\"51008\"," +
                "\"sMsg\":\"Order placement failed due to insufficient balance \"," +
                "\"tag\":\"\"}]," +
                "\"msg\":\"Operation failed.\"}"));
        var status = decoder.decode(response);

        assertThat(status.isSuccess()).isEqualTo(false);
        assertThat(status.getOrderId()).isEqualTo("");
        assertThat(status.getErrorCode()).isEqualTo("51008");
        assertThat(status.getErrorMessage()).isEqualTo("Order placement failed due to insufficient balance ");
    }

}
