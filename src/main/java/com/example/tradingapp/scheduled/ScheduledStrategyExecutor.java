package com.example.tradingapp.scheduled;

import com.example.tradingapp.TerminalOrderRemover;
import com.example.tradingapp.deribit.fix.DeribitBalancePolicy;
import com.example.tradingapp.deribit.fix.DeribitOrderTracker;
import com.example.tradingapp.deribit.websocket.DeribitPositionPolicy;
import com.example.tradingapp.okx.strategy.GetAccountBalancePolicy;
import com.example.tradingapp.okx.strategy.GetAccountPositionsPolicy;
import com.example.tradingapp.okx.trading.OkxOrderTracker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import quickfix.SessionNotFound;

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
    private final DeribitPositionPolicy deribitPositionPolicy;
    private final DeribitBalancePolicy deribitBalancePolicy;

    @Scheduled(fixedRate = 30000)
    public void executeGetBalanceOkx() throws IOException {
        log.info("Executing get balance");
        balancePolicy.getBalance();
    }

    @Scheduled(fixedRate = 30000)
    public void executeGetPositionsOkx() throws IOException {
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

    @Scheduled(initialDelay = 3000, fixedRate = 5000)
    public void getPositionsDeribit() throws IOException {
        deribitPositionPolicy.getPositions();
    }

    @Scheduled(initialDelay = 1000, fixedRate = 4000)
    public void getBalanceDeribit() throws SessionNotFound {
        deribitBalancePolicy.getBalance();
    }
}


