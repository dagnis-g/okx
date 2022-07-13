package com.example.tradingapp.trading;

import com.example.tradingapp.trading.model.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.UnsupportedEncodingException;

public interface OrderRequestEncoder {

    HttpRequestBase encode(OrderRequest orderRequest) throws JsonProcessingException, UnsupportedEncodingException;

}
