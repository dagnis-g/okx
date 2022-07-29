package com.example.tradingapp.okx.trading.encoder;

import com.example.tradingapp.okx.trading.model.request.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.UnsupportedEncodingException;

public interface OrderRequestEncoder {

    HttpRequestBase encode(OrderRequest orderRequest) throws JsonProcessingException, UnsupportedEncodingException;

}
