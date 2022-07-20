package com.example.tradingapp.websocket.model;

import lombok.Data;

import java.util.List;

@Data
public class OkxOrderStatusUpdate {
    private String channel;
    List<OkxOrderStatusUpdateData> data;
}
