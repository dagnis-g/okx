package com.example.tradingapp.deribit.fix.config;

import com.example.tradingapp.deribit.fix.DeribitFixClientAdapter;
import com.example.tradingapp.deribit.fix.DeribitFixMessageCracker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quickfix.*;

@Configuration
public class DeribitFixConfig {

    @Bean
    public Application clientApplication(DeribitFixMessageCracker messageCracker) {
        return new DeribitFixClientAdapter(messageCracker);
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
