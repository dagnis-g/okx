package com.example.tradingapp.websocket;

import com.example.tradingapp.account.Account;
import com.example.tradingapp.account.model.OkxBalanceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountChannelHandler {

    private final Account account;
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public void handleBalance(JsonNode jsonNode) throws JsonProcessingException {
        var balanceResponse = mapper.treeToValue(jsonNode, OkxBalanceResponse.class);
        var details = balanceResponse.getData().get(0).getDetails().get(0);
        account.updateAccountFromOkx(details);

        log.info("Current account balance from WebSocket {}", account.getBalance());
    }

}
