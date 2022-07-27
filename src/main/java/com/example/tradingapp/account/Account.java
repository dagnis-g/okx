package com.example.tradingapp.account;

import com.example.tradingapp.account.model.OkxBalanceResponseDataDetails;
import com.example.tradingapp.messaging.OkxPublishAccountBalance;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Component
public class Account {
    private final Map<String, BigDecimal> balance = new HashMap<>();
    
    private final OkxPublishAccountBalance publishAccountBalance;
    private final ObjectMapper mapper = new ObjectMapper();

    public void updateAccountFromOkx(OkxBalanceResponseDataDetails details) {
        String name = details.getCurrencyName();
        BigDecimal balance = details.getAvailableBalance();
        getBalance().put(name, balance);
        try {
            String jsonBalance = mapper.writeValueAsString(getBalance());
            publishAccountBalance.sendEvent(jsonBalance);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }
}
