package com.womack.ordersworkflow.helpers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpHelper {

    public static HttpEntity<Object> getHttpEntity(Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(object, headers);
        return requestEntity;
    }
}
