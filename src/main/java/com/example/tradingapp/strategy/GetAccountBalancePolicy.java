package com.example.tradingapp.strategy;

import com.example.tradingapp.account.Account;
import com.example.tradingapp.account.OkxBalanceRequestEncoder;
import com.example.tradingapp.account.OkxBalanceResponseDecoder;
import com.example.tradingapp.account.model.OkxBalanceResponse;
import com.example.tradingapp.account.model.OkxBalanceResponseData;
import com.example.tradingapp.account.model.OkxBalanceResponseDataDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetAccountBalancePolicy {

    private final Account account;
    private final OkxBalanceRequestEncoder encoder;
    private final OkxBalanceResponseDecoder decoder;

    public void getBalance() throws IOException {

        HttpRequestBase httpRequest = encoder.encode();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpRequest);
        OkxBalanceResponse balanceResponse = decoder.decode(response);

        OkxBalanceResponseData balanceData = balanceResponse.getData().get(0);
        List<OkxBalanceResponseDataDetails> details = balanceData.getDetails();

        for (var detail : details) {
            String name = detail.getCurrencyName();
            BigDecimal balance = detail.getAvailableBalance();
            account.getBalance().put(name, balance);
        }

        log.info("Current account balance {}", account.getBalance());

    }
}
