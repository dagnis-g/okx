package com.example.tradingapp.account;

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
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OkxBalanceResponseDecoderTest {

    @Autowired
    OkxBalanceResponseDecoder decoder;

    @Test
    void shouldDecodeBalance() throws IOException {
        HttpResponseFactory factory = new DefaultHttpResponseFactory();

        HttpResponse response = factory.newHttpResponse(
                new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null), null);

        response.setEntity(new StringEntity(json));

        var balance = decoder.decode(response);
        var details = balance.getData().get(0).getDetails();
        assertThat(details.size()).isEqualTo(3);

        var detail1 = details.get(0);
        var detail2 = details.get(1);
        var detail3 = details.get(2);

        assertThat(detail1.getAvailableBalance()).isEqualTo(BigDecimal.valueOf(2.9963));
        assertThat(detail1.getCurrencyName()).isEqualTo("BTC");

        assertThat(detail2.getAvailableBalance()).isEqualTo(BigDecimal.valueOf(15));
        assertThat(detail2.getCurrencyName()).isEqualTo("ETH");

        assertThat(detail3.getAvailableBalance()).isEqualTo(BigDecimal.valueOf(9076.75023294));
        assertThat(detail3.getCurrencyName()).isEqualTo("USDT");
    }

    private String json = "{" +
            "  \"code\": \"0\"," +
            "  \"data\": [" +
            "    {" +
            "      \"adjEq\": \"\"," +
            "      \"details\": [" +
            "        {" +
            "          \"availBal\": \"2.9963\"," +
            "          \"availEq\": \"\"," +
            "          \"cashBal\": \"2.9963\"," +
            "          \"ccy\": \"BTC\"," +
            "          \"crossLiab\": \"\"," +
            "          \"disEq\": \"62404.838990000004\"," +
            "          \"eq\": \"2.9963\"," +
            "          \"eqUsd\": \"62404.838990000004\"," +
            "          \"frozenBal\": \"0\"," +
            "          \"interest\": \"\"," +
            "          \"isoEq\": \"\"," +
            "          \"isoLiab\": \"\"," +
            "          \"isoUpl\": \"\"," +
            "          \"liab\": \"\"," +
            "          \"maxLoan\": \"\"," +
            "          \"mgnRatio\": \"\"," +
            "          \"notionalLever\": \"\"," +
            "          \"ordFrozen\": \"0\"," +
            "          \"stgyEq\": \"0\"," +
            "          \"twap\": \"0\"," +
            "          \"uTime\": \"1657881862129\"," +
            "          \"upl\": \"\"," +
            "          \"uplLiab\": \"\"" +
            "        }," +
            "        {" +
            "          \"availBal\": \"15\"," +
            "          \"availEq\": \"\"," +
            "          \"cashBal\": \"15\"," +
            "          \"ccy\": \"ETH\"," +
            "          \"crossLiab\": \"\"," +
            "          \"disEq\": \"18260.1\"," +
            "          \"eq\": \"15\"," +
            "          \"eqUsd\": \"18260.1\"," +
            "          \"frozenBal\": \"0\"," +
            "          \"interest\": \"\"," +
            "          \"isoEq\": \"\"," +
            "          \"isoLiab\": \"\"," +
            "          \"isoUpl\": \"\"," +
            "          \"liab\": \"\"," +
            "          \"maxLoan\": \"\"," +
            "          \"mgnRatio\": \"\"," +
            "          \"notionalLever\": \"\"," +
            "          \"ordFrozen\": \"0\"," +
            "          \"stgyEq\": \"0\"," +
            "          \"twap\": \"0\"," +
            "          \"uTime\": \"1657543202826\"," +
            "          \"upl\": \"\"," +
            "          \"uplLiab\": \"\"" +
            "        }," +
            "        {" +
            "          \"availBal\": \"9076.74973294\"," +
            "          \"availEq\": \"\"," +
            "          \"cashBal\": \"9076.75023294\"," +
            "          \"ccy\": \"USDT\"," +
            "          \"crossLiab\": \"\"," +
            "          \"disEq\": \"9071.848787814211\"," +
            "          \"eq\": \"9076.75023294\"," +
            "          \"eqUsd\": \"9071.848787814211\"," +
            "          \"frozenBal\": \"0.0005\"," +
            "          \"interest\": \"\"," +
            "          \"isoEq\": \"\"," +
            "          \"isoLiab\": \"\"," +
            "          \"isoUpl\": \"\"," +
            "          \"liab\": \"\"," +
            "          \"maxLoan\": \"\"," +
            "          \"mgnRatio\": \"\"," +
            "          \"notionalLever\": \"\"," +
            "          \"ordFrozen\": \"0.0005\"," +
            "          \"stgyEq\": \"0\"," +
            "          \"twap\": \"0\"," +
            "          \"uTime\": \"1657881862129\"," +
            "          \"upl\": \"\"," +
            "          \"uplLiab\": \"\"" +
            "        }" +
            "      ]," +
            "      \"imr\": \"\"," +
            "      \"isoEq\": \"\"," +
            "      \"mgnRatio\": \"\"," +
            "      \"mmr\": \"\"," +
            "      \"notionalUsd\": \"\"," +
            "      \"ordFroz\": \"\"," +
            "      \"totalEq\": \"160085.0359578142\"," +
            "      \"uTime\": \"1657881955290\"" +
            "    }" +
            "  ]," +
            "  \"msg\": \"\"" +
            "}";

}
