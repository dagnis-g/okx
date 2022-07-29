package com.example.tradingapp.deribit.fix.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeribitAccountBalancePublisher {

    private final JmsTemplate jmsTemplate;
    private final String TOPIC = "balance/deribit";

    @PostConstruct
    private void customizeJmsTemplate() {
        CachingConnectionFactory ccf = new CachingConnectionFactory();
        ccf.setTargetConnectionFactory(jmsTemplate.getConnectionFactory());
        jmsTemplate.setConnectionFactory(ccf);
        jmsTemplate.setPubSubDomain(true);
    }

    public void sendEvent(String balance) {
        log.info("Publishing Deribit balance {}", balance);
        jmsTemplate.convertAndSend(TOPIC, balance);
    }

}
