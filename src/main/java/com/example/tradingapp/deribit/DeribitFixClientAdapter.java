package com.example.tradingapp.deribit;

import lombok.extern.slf4j.Slf4j;
import quickfix.*;
import quickfix.field.MsgType;
import quickfix.fix44.MessageCracker;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Slf4j
public class DeribitFixClientAdapter implements Application {

    private final MessageCracker messageCracker;

    public DeribitFixClientAdapter(MessageCracker messageCracker) {
        this.messageCracker = messageCracker;
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) {
        log.info("fromAdmin: Message={}, SessionId={}", message, sessionId);
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) {
        log.info("fromApp: Message={}, SessionId={}", message, sessionId);

        try {
            messageCracker.crack(message, sessionId);
        } catch (UnsupportedMessageType | FieldNotFound | IncorrectTagValue e) {
            log.error(e.getMessage(), e);
        }
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
    public void toAdmin(Message message, SessionID sessionId) {
        log.error("aaaaaaaaaaadmiiiiiiiiiin");
        String msgType = null;
        try {
            msgType = message.getHeader().getString(MsgType.FIELD);
        } catch (FieldNotFound e) {
            log.error(String.valueOf(e));
        }

        if (MsgType.LOGON.compareTo(msgType) == 0) {

            String timeStamp = Instant.now().toEpochMilli() + ".";
            String nonce = Base64.getEncoder().encodeToString(convertUUIDToBytes());

            String rawData96 = timeStamp + nonce;

            String password554 = null;
            try {
                password554 = encode(rawData96 + "cd_k-CSFUtIFaijYdVzgtxfmIOf0kkL-TE6OHxkkUDQ");
            } catch (NoSuchAlgorithmException e) {
                log.error(String.valueOf(e));
            }

            log.info("toAdmin: Message={}, SessionId={}", message, sessionId);
            message.setString(108, "100");
            message.setString(96, rawData96);
            message.setString(553, "OrFUL-Wg");
            message.setString(554, password554);
            log.info("Message {}", message.toXML());
        }


    }

    @Override
    public void toApp(Message message, SessionID sessionId) {
        log.info("toApp: Message={}, SessionId={}", message, sessionId);
    }

    private byte[] convertUUIDToBytes() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[32]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public String encode(final String clearText) throws NoSuchAlgorithmException {
        return new String(
                Base64.getEncoder().encode(MessageDigest.getInstance("SHA-256").digest(clearText.getBytes(StandardCharsets.UTF_8))));
    }
}
