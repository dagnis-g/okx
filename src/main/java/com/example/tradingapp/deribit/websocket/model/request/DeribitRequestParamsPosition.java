package com.example.tradingapp.deribit.websocket.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeribitRequestParamsPosition implements DeribitRequestParams {
    private String currency;
    private String kind;
}
