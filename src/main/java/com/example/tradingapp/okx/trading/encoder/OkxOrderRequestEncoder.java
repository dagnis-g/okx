package com.example.tradingapp.okx.trading.encoder;

import com.example.tradingapp.okx.trading.model.request.OkxOrderRequest;
import com.example.tradingapp.okx.trading.model.request.OrderRequest;
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
    private final ModelMapper modelMapper;

    private final String PATH = "/api/v5/trade/order";

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public HttpRequestBase encode(OrderRequest orderRequest) throws JsonProcessingException, UnsupportedEncodingException {
        OkxOrderRequest okxOrderRequest = modelMapper.map(orderRequest, OkxOrderRequest.class);
        okxOrderRequest.setTradeMode("cash");
        String orderJson = mapper.writeValueAsString(okxOrderRequest);
        log.info("OkxOrderRequestEncoder {}", orderJson);

        return uriAndHeaderEncoder.encode("Post", PATH, orderJson);

    }

}
