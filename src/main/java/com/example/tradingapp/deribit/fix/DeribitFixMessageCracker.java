package com.example.tradingapp.deribit.fix;

import com.example.tradingapp.secrets.DeribitSecrets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.Message;
import quickfix.*;
import quickfix.fix44.MessageCracker;
import quickfix.fix44.*;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
public class DeribitFixMessageCracker extends MessageCracker {

    @Override
    public void onMessage(OrderCancelRequest message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        log.warn("Order cancel request: {}", message);
    }

    @Override
    public void onMessage(OrderCancelReject message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        log.warn("Order cancel reject: {}", message);
    }

    @Override
    public void onMessage(Reject message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        log.error("Reject: {}", message);
    }

    @Override
    public void onMessage(ExecutionReport message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        log.info("Execution report: {}", message);
    }


    @Override
    public void onMessage(Logon message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        login(message, sessionID);
    }

    public void login(Message message, SessionID sessionId) {
        String timeStamp = Instant.now().toEpochMilli() + ".";
        String nonce = Base64.getEncoder().encodeToString(convertUUIDToBytes());
        String rawData96 = timeStamp + nonce;
        String password554 = null;

        try {
            password554 = encodePassword554(rawData96, DeribitSecrets.CLIENT_SECRET);
        } catch (NoSuchAlgorithmException e) {
            log.error(String.valueOf(e));
        }

        message.setString(108, "100");
        message.setString(96, rawData96);
        message.setString(553, DeribitSecrets.CLIENT_ID);
        message.setString(554, password554);
        log.info("toAdmin: {}", message);
    }

    private byte[] convertUUIDToBytes() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[32]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    private String encodePassword554(final String rawData98, String clientSecret) throws NoSuchAlgorithmException {
        String password554String = rawData98 + clientSecret;
        return new String(
                Base64.getEncoder().encode(MessageDigest.getInstance("SHA-256").digest(password554String.getBytes(StandardCharsets.UTF_8))));
    }
}
