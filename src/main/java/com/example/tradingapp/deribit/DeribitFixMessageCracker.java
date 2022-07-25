package com.example.tradingapp.deribit;

import lombok.extern.slf4j.Slf4j;
import quickfix.FieldNotFound;
import quickfix.IncorrectTagValue;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;
import quickfix.fix44.MessageCracker;

@Slf4j
public class DeribitFixMessageCracker extends MessageCracker {

    @Override
    public void onMessage(quickfix.fix44.OrderCancelRequest orderCancelRequest, SessionID sessionID)
            throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {

        // Handle the message here
        log.info("*****************");
        log.info("Message received for sessionID={}: {}", sessionID, orderCancelRequest);
        log.info("*****************");
    }

}
