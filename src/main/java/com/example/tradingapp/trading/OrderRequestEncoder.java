package com.example.tradingapp.trading;

import com.example.tradingapp.trading.model.OkxOrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.UnsupportedEncodingException;

public interface OrderRequestEncoder {

    HttpRequestBase encode(OkxOrderRequest orderRequest) throws JsonProcessingException, UnsupportedEncodingException;

}
