package com.example.tradingapp.deribit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quickfix.*;

@Configuration
public class DeribitFixConfig {

    @Bean
    public Application clientApplication(quickfix.fix44.MessageCracker messageCracker) {
        return new DeribitFixClientAdapter(messageCracker);
    }

    @Bean
    public quickfix.fix44.MessageCracker messageCracker() {
        return new DeribitFixMessageCracker();
    }

    @Bean
    public Initiator clientInitiator(quickfix.Application clientApplication, MessageStoreFactory clientMessageStoreFactory,
                                     SessionSettings clientSessionSettings, LogFactory clientLogFactory,
                                     MessageFactory clientMessageFactory) throws ConfigError {

        return new ThreadedSocketInitiator(clientApplication, clientMessageStoreFactory, clientSessionSettings,
                clientLogFactory, clientMessageFactory);
    }

    @Bean
    public LogFactory clientLogFactory(SessionSettings clientSessionSettings) {
        return new FileLogFactory(clientSessionSettings);
    }

}
