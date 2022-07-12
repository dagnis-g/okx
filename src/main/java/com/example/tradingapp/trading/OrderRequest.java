package com.example.tradingapp.trading;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {

    private String symbol;
    private Side side;
    private OrderType type;
    private double price;
    private double quantity;
}
