package com.example.tradingapp.websocket;

import com.example.tradingapp.trading.OkxOrderTracker;
import com.example.tradingapp.trading.model.enums.OkxOrderStatus;
import com.example.tradingapp.websocket.model.OkxOrderStatusUpdate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderChannelHandler {

    private final OkxOrderTracker orderTracker;
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public void handleOrderStatus(JsonNode jsonNode) throws JsonProcessingException {

        var orderStatusUpdate = mapper.treeToValue(jsonNode, OkxOrderStatusUpdate.class);
        var orderStatusData = orderStatusUpdate.getData().get(0);
        String orderId = orderStatusData.getOrderId();

        if (orderStatusData.getState() == OkxOrderStatus.Live) {
            orderTracker.live(orderId);
        } else if (orderStatusData.getState() == OkxOrderStatus.Canceled) {
            orderTracker.canceled(orderId);
        } else if (orderStatusData.getState() == OkxOrderStatus.PartiallyFilled) {
            orderTracker.partiallyFilled(orderId);
        } else if (orderStatusData.getState() == OkxOrderStatus.FullyFilled) {
            orderTracker.filled(orderId, true);
        }
    }

}
