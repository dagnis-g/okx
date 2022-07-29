package com.example.tradingapp.account;

import com.example.tradingapp.account.model.OkxBalanceResponseDataDetails;
import com.example.tradingapp.deribit.fix.messaging.DeribitAccountBalancePublisher;
import com.example.tradingapp.messaging.OkxAccountBalancePublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.FieldNotFound;
import quickfix.fix44.UserResponse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Component
public class Account {
    private final Map<String, BigDecimal> balanceOkx = new HashMap<>();
    private final Map<String, BigDecimal> balanceDeribit = new HashMap<>();

    private final OkxAccountBalancePublisher okxPublisher;
    private final DeribitAccountBalancePublisher deribitPublisher;
    private final ObjectMapper mapper = new ObjectMapper();

    public void updateBalanceOkx(OkxBalanceResponseDataDetails details) {
        String name = details.getCurrencyName();
        BigDecimal balance = details.getAvailableBalance();
        getBalanceOkx().put(name, balance);
        try {
            String jsonBalance = mapper.writeValueAsString(getBalanceOkx());
            okxPublisher.sendEvent(jsonBalance);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }

    public void updateBalanceDeribit(UserResponse message) throws FieldNotFound {
        String currency = message.getString(15);
        var userBalance = new BigDecimal(message.getString(100002));
        getBalanceDeribit().put(currency, userBalance);
        log.info("Deribit balance: {}", balanceDeribit);

        try {
            String jsonBalance = mapper.writeValueAsString(getBalanceDeribit());
            deribitPublisher.sendEvent(jsonBalance);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
