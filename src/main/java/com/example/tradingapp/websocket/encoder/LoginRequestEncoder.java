package com.example.tradingapp.websocket.encoder;

import com.example.tradingapp.secrets.OkxSecrets;
import com.example.tradingapp.websocket.model.request.LoginArg;
import com.example.tradingapp.websocket.model.request.LoginRequest;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class LoginRequestEncoder {

    private final String API_KEY = OkxSecrets.API_KEY;
    private final String SECRET_KEY = OkxSecrets.SECRET_KEY;
    private final String PASSPRHASE = OkxSecrets.PASSPHRASE;
    private final String PATH = "/users/self/verify";

    public LoginRequest encode() throws UnsupportedEncodingException {

        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        String hmacUri = timestamp + "GET" + PATH;
        String okAccessSign = encodeAccessSign(hmacUri);

        var loginArg = LoginArg.builder()
                .apiKey(API_KEY)
                .passphrase(PASSPRHASE)
                .timestamp(timestamp)
                .sign(okAccessSign)
                .build();
        List<LoginArg> args = new ArrayList<>();
        args.add(loginArg);
        var login = LoginRequest.builder()
                .op("login")
                .args(args)
                .build();

        return login;
    }

    private String encodeAccessSign(String hmacUri) {
        byte[] hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, SECRET_KEY).hmac(hmacUri);
        return Base64.getEncoder().encodeToString(hmac);
    }

}
