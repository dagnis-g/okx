package com.example.tradingapp.trading.encoder;

import com.example.tradingapp.trading.model.OkxOrderRequest;
import com.example.tradingapp.trading.model.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpRequestBase;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OkxOrderRequestEncoder implements OrderRequestEncoder {

    private final OkxUriAndHeaderEncoder uriAndHeaderEncoder;
    private final String PATH = "/api/v5/trade/order";

    private final ObjectMapper mapper = new ObjectMapper();
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public HttpRequestBase encode(OrderRequest orderRequest) throws JsonProcessingException, UnsupportedEncodingException {
        OkxOrderRequest okxOrderRequest = modelMapper.map(orderRequest, OkxOrderRequest.class);
        okxOrderRequest.setTradeMode("cash");
        String orderJson = mapper.writeValueAsString(okxOrderRequest);
        log.info("OkxOrderRequestEncoder {}", orderJson);

        return uriAndHeaderEncoder.encode("Post", PATH, orderJson);
        
    }

}
