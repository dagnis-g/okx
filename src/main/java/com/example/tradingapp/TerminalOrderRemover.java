package com.example.tradingapp;

import com.example.tradingapp.tracker.OrderTrackerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TerminalOrderRemover {

    public void removeTerminalOrders(OrderTrackerImpl orderTracker) {
        orderTracker.getPlacedOrders().entrySet()
                .removeIf(entry -> {
                    boolean isTerminal = entry.getValue().getStatus().isTerminal();
                    if (isTerminal) {
                        log.info("Removed order({}), reason: terminal Status-{}",
                                entry.getValue().getId(), entry.getValue().getStatus());
                        orderTracker.getPlacedOrderIds().remove(entry.getValue().getId());
                    }
                    return isTerminal;
                });
    }

}
