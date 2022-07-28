package com.example.tradingapp.messaging;

import com.example.tradingapp.NoOrderFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class OkxCancelOrderListenerTest {

    @Autowired
    OkxCancelOrderListener cancelOrderListener;
    @Autowired
    ConnectionFactory connectionFactory;

    @Test
    void shouldLogCorrectOrderRequest(CapturedOutput output) throws JMSException, IOException {
        Session session = connectionFactory.createConnection().createSession(false, 1);
        TextMessage msg = session.createTextMessage();
        msg.setText(json);
        cancelOrderListener.handle(msg);

        assertThat(output.getOut()).contains("Order Request from Solace: OrderRequest(symbol=BTC-USDT, side=BUY, type=LIMIT, price=2.0, quantity=0.01)");
    }

    @Test
    void shouldLogErrorOnIncorrectOrderRequestObject(CapturedOutput output) throws JMSException, IOException {
        Session session = connectionFactory.createConnection().createSession(false, 1);
        TextMessage msg = session.createTextMessage();
        msg.setText(jsonIncorrect);
        cancelOrderListener.handle(msg);

        assertThat(output.getOut()).contains("Not valid OrderRequest com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `com.example.tradingapp.trading.model.enums.OrderType` from String \"lim\"");
    }

    @Test
    void shouldThrowOnOrderNotFound() throws JMSException {
        Session session = connectionFactory.createConnection().createSession(false, 1);
        TextMessage msg = session.createTextMessage();
        msg.setText(jsonNotFound);
        ///Why is it failing? if - Caused by: com.example.tradingapp.NoOrderFoundException
        Assertions.assertThrows(NoOrderFoundException.class, () -> cancelOrderListener.handle(msg));
    }

    String json = """
            {
              "symbol": "BTC-USDT",
              "side": "buy",
              "type": "limit",
              "price": 2,
              "quantity": 0.01
            }""";

    String jsonIncorrect = """
            {
              "symbol": "BTC-USDT",
              "side": "buy",
              "type": "lim",
              "price": 2,
              "quantity": 0.01
            }""";

    String jsonNotFound = """
            {
              "symbol": "BTC-USDT",
              "side": "buy",
              "type": "limit",
              "price": 90909090909,
              "quantity": 0.01
            }""";
}