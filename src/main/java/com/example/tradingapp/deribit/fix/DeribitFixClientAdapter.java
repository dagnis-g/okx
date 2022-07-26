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
        String msgType = null;
        try {
            msgType = message.getHeader().getString(MsgType.FIELD);
        } catch (FieldNotFound e) {
            throw new RuntimeException(e);
        }
        log.info("Message type to admin: {}", msgType);
        try {
            messageCracker.crack(message, sessionID);
        } catch (UnsupportedMessageType | FieldNotFound | IncorrectTagValue e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        var msgType = message.getHeader().getString(MsgType.FIELD);
        log.info("Message type from admin: {}", msgType);
        log.info("fromAdmin: Message={}, SessionId={}", message, sessionID);
        try {
            messageCracker.crack(message, sessionID);
        } catch (UnsupportedMessageType e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        String msgType = null;
        try {
            msgType = message.getHeader().getString(MsgType.FIELD);
        } catch (FieldNotFound e) {
            throw new RuntimeException(e);
        }
        log.info("Message type to app: {}", msgType);
        log.info("toApp: Message={}, SessionId={}", message, sessionID);
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        var msgType = message.getHeader().getString(MsgType.FIELD);
        log.info("Message type from app: {}", msgType);
        log.info("fromApp: Message={}, SessionId={}", message, sessionID);
    }

}
