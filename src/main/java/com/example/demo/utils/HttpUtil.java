package com.example.demo.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

@Component
public class HttpUtil {
    public HttpUtil() {}
    public HttpResponse post(HttpClient client, String url, String message) throws Exception {
        try {
            HttpPost postRequest = new HttpPost(url);
            postRequest.setHeader("Accept", "application/json");
            postRequest.setHeader("Connection", "keep-alive");
            postRequest.setHeader("Content-Type", "application/json");

            postRequest.setEntity(new StringEntity(message, "UTF-8")); //json 메시지
            HttpResponse response = client.execute(postRequest);
            return response;
        } catch(Exception e) {
            throw e;
        }
    }
}
