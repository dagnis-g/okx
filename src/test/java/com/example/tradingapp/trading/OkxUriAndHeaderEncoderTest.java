package com.example.tradingapp.trading;

import com.example.tradingapp.secrets.Secrets;
import com.example.tradingapp.trading.encoder.OkxUriAndHeaderEncoder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OkxUriAndHeaderEncoderTest {

    @Autowired
    OkxUriAndHeaderEncoder encoder;

    @Test
    void shouldBeGetRequest() throws UnsupportedEncodingException {
        HttpRequestBase request1 = encoder.encode("get", "", "");
        HttpRequestBase request2 = encoder.encode("gEt", "", "");
        HttpRequestBase request3 = encoder.encode("GET", "", "");

        assertThat(request1.getMethod()).isEqualTo("GET");
        assertThat(request2.getMethod()).isEqualTo("GET");
        assertThat(request3.getMethod()).isEqualTo("GET");

    }

    @Test
    void shouldBePostRequest() throws UnsupportedEncodingException {
        HttpRequestBase request1 = encoder.encode("post", "", "");
        HttpRequestBase request2 = encoder.encode("POst", "", "");
        HttpRequestBase request3 = encoder.encode("POST", "", "");

        assertThat(request1.getMethod()).isEqualTo("POST");
        assertThat(request2.getMethod()).isEqualTo("POST");
        assertThat(request3.getMethod()).isEqualTo("POST");
    }

    @Test
    void shouldThrowOnIncorrectHttpRequestType() {
        Assertions.assertThrows(RuntimeException.class,
                () -> encoder.encode("das", "", ""));
    }

    @Test
    void shouldAddHeaders() throws UnsupportedEncodingException {
        HttpPost encodedReq = (HttpPost) encoder.encode("post", "", "");
        
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

}
