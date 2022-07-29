package com.example.tradingapp.scheduled;

import com.example.tradingapp.TerminalOrderRemover;
import com.example.tradingapp.deribit.fix.DeribitOrderTracker;
import com.example.tradingapp.okx.strategy.GetAccountBalancePolicy;
import com.example.tradingapp.okx.strategy.GetAccountPositionsPolicy;
import com.example.tradingapp.okx.trading.OkxOrderTracker;
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
    private final OkxOrderTracker okxOrderTracker;
    private final DeribitOrderTracker deribitOrderTracker;
    private final GetAccountPositionsPolicy positionsPolicy;
    private final TerminalOrderRemover terminalOrderRemover;

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
    public void removeTerminalOrdersOkx() {
        terminalOrderRemover.removeTerminalOrders(okxOrderTracker);
    }

    @Scheduled(initialDelay = 2500, fixedRate = 5000)
    public void removeTerminalOrderDeribit() {
        terminalOrderRemover.removeTerminalOrders(deribitOrderTracker);
    }
}


