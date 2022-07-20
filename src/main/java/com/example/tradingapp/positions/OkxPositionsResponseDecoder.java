package com.example.tradingapp.positions;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OkxPositionsResponseDecoder {
    ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public OkxPositionsResponse decode(HttpResponse httpResponse) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        String entityString = EntityUtils.toString(entity);
        return mapper.readValue(entityString, OkxPositionsResponse.class);
    }
}
