package com.example.tradingapp.websocket;

import com.example.tradingapp.account.Account;
import com.example.tradingapp.okx.websocket.AccountChannelHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountChannelHandlerTest {

    @Autowired
    AccountChannelHandler accountChannelHandler;
    @Autowired
    Account account;

    ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Test
    void shouldPutCorrectBalance() throws JsonProcessingException {
        JsonNode jsonNode = mapper.readTree(json);
        accountChannelHandler.handleBalance(jsonNode);
        var btc = account.getBalanceOkx().get("BTC");

        assertThat(btc).isEqualTo(BigDecimal.valueOf(11));
    }

    private String json = """
            {
              "arg": {
                "channel": "account",
                "uid": "77982378738415879"
              },
              "data": [
                {
                  "uTime": "1614846244194",
                  "totalEq": "91884.8502560037982063",
                  "adjEq": "91884.8502560037982063",
                  "isoEq": "0",
                  "ordFroz": "0",
                  "imr": "0",
                  "mmr": "0",
                  "notionalUsd": "",
                  "mgnRatio": "100000",
                  "details": [{
                      "availBal": "",
                      "availEq": "1",
                      "ccy": "BTC",
                      "cashBal": "11",
                      "uTime": "1617279471503",
                      "disEq": "50559.01",
                      "eq": "1",
                      "eqUsd": "45078.3790756226851775",
                      "frozenBal": "0",
                      "interest": "0",
                      "isoEq": "0",
                      "liab": "0",
                      "maxLoan": "",
                      "mgnRatio": "",
                      "notionalLever": "0.0022195262185864",
                      "ordFrozen": "0",
                      "upl": "0",
                      "uplLiab": "0",
                      "crossLiab": "0",
                      "isoLiab": "0",
                      "coinUsdPrice": "60000",
                      "stgyEq":"0",
                      "isoUpl":""
                    }
                  ]
                }
              ]
            }""";
}