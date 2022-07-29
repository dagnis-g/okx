package com.example.tradingapp.deribit.websocket.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeribitRequest {

    private final String jsonrpc = "2.0";
    private String id;
    private String method;
    private DeribitRequestParams params;
}
