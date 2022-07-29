package com.example.tradingapp.deribit.websocket.model.response;

import lombok.Data;

import java.util.List;

@Data
public class DeribitResponse {
    private String jsonrpc;
    private String id;
    private List<DeribitResponseResultsPositions> result;
}
