package com.example.tradingapp.deribit.fix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.*;
import quickfix.field.MsgType;

import java.util.Objects;

@Slf4j
@Component
public class DeribitFixClientAdapter implements Application {

    private final DeribitFixMessageCracker messageCracker;

    public DeribitFixClientAdapter(DeribitFixMessageCracker messageCracker) {
        this.messageCracker = messageCracker;
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectTagValue {
        var msgType = message.getHeader().getString(MsgType.FIELD);
        log.warn("Message type from admin: {}", msgType);
        log.info("fromAdmin: Message={}, SessionId={}", message, sessionId);
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) {
        log.info("fromApp: Message={}, SessionId={}", message, sessionId);
    }

    @Override
    public void onCreate(SessionID sessionId) {
        log.info("onCreate: SessionId={}", sessionId);
    }

    @Override
    public void onLogon(SessionID sessionId) {
        log.warn("onLogon: SessionId={}", sessionId);
    }

    @Override
    public void onLogout(SessionID sessionId) {
        log.info("onLogout: SessionId={}", sessionId);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        log.warn("to admin: message {}, sessionId {}", sessionId, sessionId);
        String msgType = null;
        try {
            msgType = message.getHeader().getString(MsgType.FIELD);
        } catch (FieldNotFound e) {
            log.error("toAdmin {}", e.getMessage());
        }

        if (MsgType.LOGON.compareTo(Objects.requireNonNull(msgType)) == 0) {
            messageCracker.login(message, sessionId);
        }
        
    }

    @Override
    public void toApp(Message message, SessionID sessionId) {
        log.info("toApp: Message={}, SessionId={}", message, sessionId);
    }

}
