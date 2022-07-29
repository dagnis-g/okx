package com.example.tradingapp.deribit.websocket;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Data
@Component
public class DeribitLoginStateWS {

    private boolean isLoggedIn;
    private WebSocketSession session;

}
