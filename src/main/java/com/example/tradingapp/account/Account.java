package com.example.tradingapp.account;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class Account {
    private final Map<String, BigDecimal> balance = new HashMap<>();
}
