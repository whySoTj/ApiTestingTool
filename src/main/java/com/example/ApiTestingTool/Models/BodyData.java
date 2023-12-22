package com.example.ApiTestingTool.Models;

import com.fasterxml.jackson.databind.JsonNode;

public class BodyData {

    private String url;
    private String method;
    private JsonNode headers;
    private JsonNode body;
    private JsonNode validation;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public JsonNode getBody() {
        return body;
    }
    public void setBody(JsonNode body) {
        this.body = body;
    }
    public JsonNode getHeaders() {
        return headers;
    }
    public void setHeaders(JsonNode headers) {
        this.headers = headers;
    }
    public JsonNode getValidation() {
        return validation;
    }
    public void setValidation(JsonNode validation) {
        this.validation = validation;
    }

    
    
}
