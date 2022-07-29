package com.example.tradingapp.okx.trading.decoder;

import com.example.tradingapp.okx.trading.model.response.OkxOrderListResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OkxOrderListResponseDecoder {

    ObjectMapper mapper = new ObjectMapper();

    public OkxOrderListResponse decode(HttpResponse httpResponse) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        String entityString = EntityUtils.toString(entity);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(entityString, OkxOrderListResponse.class);
    }
}
