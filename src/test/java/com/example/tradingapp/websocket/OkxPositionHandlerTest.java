package com.example.tradingapp.websocket;

import com.example.tradingapp.positions.Positions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OkxPositionHandlerTest {

    @Autowired
    OkxPositionHandler positionHandler;
    @Autowired
    Positions positions;

    ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Test
    void shouldPutCorrectPosition() throws JsonProcessingException {
        JsonNode jsonNode = mapper.readTree(json);
        positionHandler.handlePositions(jsonNode);

        var positionFromPostions = positions.getPositionsOkx().get("307173036051017730");
        System.out.println(positionFromPostions);

        assertThat(positionFromPostions.getCurrency()).isEqualTo("ETH");
        assertThat(positionFromPostions.getInstrumentType()).isEqualTo("FUTURES");
        assertThat(positionFromPostions.getPositionSide()).isEqualTo("long");


    }

    private String json = """
            {
                "arg":{
                    "channel":"positions",
                    "instType":"FUTURES"
                },
                "data":[
                    {
                        "adl":"1",
                        "availPos":"1",
                        "avgPx":"2566.31",
                        "cTime":"1619507758793",
                        "ccy":"ETH",
                        "deltaBS":"",
                        "deltaPA":"",
                        "gammaBS":"",
                        "gammaPA":"",
                        "imr":"",
                        "instId":"ETH-USD-210430",
                        "instType":"FUTURES",
                        "interest":"0",
                        "last":"2566.22",
                        "lever":"10",
                        "liab":"",
                        "liabCcy":"",
                        "liqPx":"2352.8496681818233",
                        "markPx":"2353.849",
                        "margin":"0.0003896645377994",
                        "mgnMode":"isolated",
                        "mgnRatio":"11.731726509588816",
                        "mmr":"0.0000311811092368",
                        "notionalUsd":"2276.2546609009605",
                        "optVal":"",
                        "pTime":"1619507761462",
                        "pos":"1",
                        "posCcy":"",
                        "posId":"307173036051017730",
                        "posSide":"long",
                        "thetaBS":"",
                        "thetaPA":"",
                        "tradeId":"109844",
                        "uTime":"1619507761462",
                        "upl":"-0.0000009932766034",
                        "uplRatio":"-0.0025490556801078",
                        "vegaBS":"",
                        "vegaPA":""
                    }
                ]
            }""";
}