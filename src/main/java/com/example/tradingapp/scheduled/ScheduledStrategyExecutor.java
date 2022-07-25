package com.example.tradingapp.scheduled;

import com.example.tradingapp.strategy.GetAccountBalancePolicy;
import com.example.tradingapp.strategy.GetAccountPositionsPolicy;
import com.example.tradingapp.trading.OkxOrderTracker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledStrategyExecutor {

    private final GetAccountBalancePolicy balancePolicy;
    private final OkxOrderTracker orderTracker;
    private final GetAccountPositionsPolicy positionsPolicy;

    @Scheduled(fixedRate = 30000)
    public void executeGetBalance() throws IOException {
        log.info("Executing get balance");
        balancePolicy.getBalance();
    }

    @Scheduled(fixedRate = 30000)
    public void executeGetPositions() throws IOException {
        log.info("Executing get positions");
        positionsPolicy.getPositions();
    }

    @Scheduled(fixedRate = 5000)
    public void removeTerminalOrders() {
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


