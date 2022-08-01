package com.example.tradingapp.deribit.fix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.*;
import quickfix.field.MsgType;

@Slf4j
@Component
public class DeribitFixClientAdapter implements Application {

    private final DeribitFixMessageCracker messageCracker;

    public DeribitFixClientAdapter(DeribitFixMessageCracker messageCracker) {
        this.messageCracker = messageCracker;
    }

    @Override
    public void onCreate(SessionID sessionId) {
        log.info("onCreate: SessionId={}", sessionId);
    }

    @Override
    public void onLogon(SessionID sessionId) {
        log.info("onLogon: SessionId={}", sessionId);
    }

    @Override
    public void onLogout(SessionID sessionId) {
        log.info("onLogout: SessionId={}", sessionId);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        log.info("to admin: message {}, sessionId {}", message, sessionID);
        try {
            String msgType = message.getHeader().getString(MsgType.FIELD);
            log.info("Message type to admin: {}", msgType);
            messageCracker.crack(message, sessionID);
        } catch (UnsupportedMessageType | FieldNotFound | IncorrectTagValue e) {
            log.error("To admin: {}", e.getMessage());
        }
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        log.info("fromAdmin: Message={}, SessionId={}", message, sessionID);
        try {
            var msgType = message.getHeader().getString(MsgType.FIELD);
            log.info("Message type from admin: {}", msgType);
            messageCracker.crack(message, sessionID);
        } catch (UnsupportedMessageType | FieldNotFound | IncorrectTagValue e) {
            log.error("From admin: {}", e.getMessage());
        }
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        log.info("toApp: Message={}, SessionId={}", message, sessionID);
        try {
            String msgType = message.getHeader().getString(MsgType.FIELD);
            log.info("Message type to app: {}", msgType);
            messageCracker.crack(message, sessionID);
        } catch (UnsupportedMessageType | FieldNotFound | IncorrectTagValue e) {
            log.error("To app: {}", e.getMessage());
        }
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        log.info("fromApp: Message={}, SessionId={}", message, sessionID);
        try {
            var msgType = message.getHeader().getString(MsgType.FIELD);
            log.info("Message type from app: {}", msgType);
            messageCracker.crack(message, sessionID);
        } catch (UnsupportedMessageType | FieldNotFound | IncorrectTagValue e) {
            log.error("From app: {}", e.getMessage());
        }
    }

}
