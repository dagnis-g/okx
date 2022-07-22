package com.example.tradingapp.trading.model.request;

import com.example.tradingapp.trading.model.enums.OrderType;
import com.example.tradingapp.trading.model.enums.Side;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String symbol;
    private Side side;
    private OrderType type;
    private double price;
    private double quantity;
}
