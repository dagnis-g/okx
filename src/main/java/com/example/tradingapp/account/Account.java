package com.example.tradingapp.account;

import com.example.tradingapp.account.model.OkxBalanceResponseDataDetails;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class Account {
    private final Map<String, BigDecimal> balance = new HashMap<>();

    public void updateAccount(OkxBalanceResponseDataDetails details) {
        String name = details.getCurrencyName();
        BigDecimal balance = details.getAvailableBalance();
        getBalance().put(name, balance);
    }
}
