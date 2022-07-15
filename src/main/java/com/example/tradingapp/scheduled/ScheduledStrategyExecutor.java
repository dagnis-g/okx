package com.example.tradingapp.scheduled;

import com.example.tradingapp.strategy.DummyStrategy;
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
}


