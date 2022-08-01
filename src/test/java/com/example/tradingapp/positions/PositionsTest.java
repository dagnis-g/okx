package com.example.tradingapp.positions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PositionsTest {

    @Autowired
    Positions positions;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldDecodeAndUpdatePositions() throws JsonProcessingException {
        JsonNode jsonNode = mapper.readTree(json);
        positions.updatePositionsDeribit(jsonNode);

        var position = positions.getPositionsDeribit().get("ABC-PERPETUAL");

        assertThat(position.getPositionId()).isEqualTo("ABC-PERPETUAL");
        assertThat(position.getCurrency()).isEqualTo("ABC");
        assertThat(position.getInstrumentType()).isEqualTo("FUTURE");
        assertThat(position.getPositionSide()).isEqualTo("SELL");
    }

    String json = """
            {
            "jsonrpc": "2.0",
            "id": "positions",
            "result": [
            {
            "total_profit_loss": 0.0,
            "size_currency": 0.0,
            "size": 0.0,
            "settlement_price": 1714.99,
            "realized_profit_loss": 0.0,
            "realized_funding": 0.0,
            "open_orders_margin": 0.045350068,
            "mark_price": 1681.15,
            "maintenance_margin": 0.0,
            "leverage": 50,
            "kind": "future",
            "interest_value": -0.019789441759433234,
            "instrument_name": "ABC-PERPETUAL",
            "initial_margin": 0.0,
            "index_price": 1682.01,
            "floating_profit_loss": 0.0,
            "estimated_liquidation_price": null,
            "direction": "sell",
            "delta": 0.0,
            "average_price": 0.0
            }
            ],
            "usIn": 1659097249169806,
            "usOut": 1659097249170870,
            "usDiff": 1064,
            "testnet": true
            }""";
}