package com.example.tradingapp.positions;

import lombok.Data;

import java.util.List;

@Data
public class OkxPositionsResponse {

    private String code;
    private List<OkxPositionsResponseData> data;
    private String msg;

}
