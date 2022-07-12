package com.example.tradingapp.scheduled;

import com.example.tradingapp.strategy.DummyStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledStrategyExecutor {

    private final DummyStrategy dummyStrategy;

    @Scheduled(fixedRate = 5000)
    public void executeStrategy() {
        log.info("Executing");
        dummyStrategy.execute();
    }
}


