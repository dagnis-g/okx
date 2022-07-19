package com.example.tradingapp.scheduled;

import com.example.tradingapp.strategy.DummyStrategy;
import com.example.tradingapp.strategy.GetAccountBalancePolicy;
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

    private final DummyStrategy dummyStrategy;
    private final GetAccountBalancePolicy balancePolicy;
    private final OkxOrderTracker orderTracker;

    @Scheduled(fixedRate = 5000, initialDelay = 1500)
    public void executeStrategy() throws IOException {
        log.info("Executing new order");
        dummyStrategy.execute();
    }

    @Scheduled(fixedRate = 6000, initialDelay = 2000)
    public void executeCancelOrder() throws IOException {
        log.info("Executing cancel order");
        dummyStrategy.cancelOrder();
    }

    @Scheduled(fixedRate = 5000)
    public void executeGetBalance() throws IOException {
        log.info("Executing get balance");
        balancePolicy.getBalance();
    }

    @Scheduled(fixedRate = 5000)
    public void removeTerminalOrders() {
        orderTracker.getPlacedOrders().entrySet()
                .removeIf(entry -> {
                    boolean isTerminal = entry.getValue().getStatus().isTerminal();
                    if (isTerminal) {
                        log.info("Removed order({}),reason: terminal Status-{}",
                                entry.getValue().getId(), entry.getValue().getStatus());
                    }
                    return isTerminal;
                });
    }
}


