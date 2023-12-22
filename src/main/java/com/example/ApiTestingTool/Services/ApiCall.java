package com.example.ApiTestingTool.Services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.example.ApiTestingTool.Models.BodyData;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class ApiCall {
    
  public ResponseEntity<String> apiCalls(BodyData data,JsonNode jsonNode) {
        String uri = data.getUrl().toString();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<?> requestEntity = new HttpEntity<>(jsonNode, headers);
        HttpMethod httpMethod = getHttpMethodFromString(data.getMethod());

        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, httpMethod, requestEntity, String.class);
            return response;
        } catch (HttpStatusCodeException e) {

            return new ResponseEntity<>("Error occurred during API call", e.getStatusCode());
        }
    }


    private HttpMethod getHttpMethodFromString(String method) {
        switch (method.toUpperCase()) {
            case "GET":
                return HttpMethod.GET;
            case "POST":
                return HttpMethod.POST;
            case "PUT":
                return HttpMethod.PUT;
            case "DELETE":
                return HttpMethod.DELETE;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
    }

}
