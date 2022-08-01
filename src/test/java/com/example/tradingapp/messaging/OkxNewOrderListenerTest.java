package com.example.tradingapp.messaging;

import com.example.tradingapp.okx.messaging.OkxNewOrderListener;
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
class OkxNewOrderListenerTest {

    @Autowired
    OkxNewOrderListener orderListener;
    @Autowired
    ConnectionFactory connectionFactory;

    @Test
    void shouldCreateCorrectRequestObjectAndLog(CapturedOutput output) throws JMSException, IOException {
        Session session = connectionFactory.createConnection().createSession(false, 1);
        TextMessage msg = session.createTextMessage();
        msg.setText(json);
        orderListener.handle(msg);

        assertThat(output.getOut()).contains("Order Request from Solace: OrderRequest(symbol=BTC-USDT, side=BUY, type=LIMIT, price=2.0, quantity=0.01)");
    }

    @Test
    void shouldCatchAndLogErrorOnInvalidEnumValue(CapturedOutput output) throws JMSException, IOException {
        Session session = connectionFactory.createConnection().createSession(false, 1);
        TextMessage msg = session.createTextMessage();
        msg.setText(jsonInvalid);
        orderListener.handle(msg);

        assertThat(output.getOut()).contains("""
                from String "bu": not one of the values accepted for Enum class: [zero, sell, buy]
                 at [Source: (String)"{
                  "symbol": "BTC-USDT",
                  "side": "bu",
                  "type": "limit",
                  "price": 2,
                  "quantity": 0.01
                }"; line: 3, column: 11] (through reference chain""");
    }

    String json = """
            {
              "symbol": "BTC-USDT",
              "side": "buy",
              "type": "limit",
              "price": 2,
              "quantity": 0.01
            }""";

    String jsonInvalid = """
            {
              "symbol": "BTC-USDT",
              "side": "bu",
              "type": "limit",
              "price": 2,
              "quantity": 0.01
            }""";
}