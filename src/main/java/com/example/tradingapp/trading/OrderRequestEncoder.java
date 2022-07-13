package com.example.tradingapp.trading;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.client.methods.HttpRequestBase;

public interface OrderRequestEncoder {

    HttpRequestBase encode(OrderRequest orderRequest) throws JsonProcessingException;

}
