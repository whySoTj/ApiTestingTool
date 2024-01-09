package com.example.ApiTestingTool.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ApiBody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String url;
    private String method;
    private String headers;
    @Column(length = 1000)
    private String body;
    @Column(length = 1000)
    private String validation;
    public String getTestScript() {
        return testScript;
    }
    public void setTestScript(String testScript) {
        this.testScript = testScript;
    }
    @Column(length = 1000)
    private String testScript;
    private String row_num;
    

    
    public String getRow_num() {
        return row_num;
    }
    public void setRow_num(String row_num) {
        this.row_num = row_num;
    }
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
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getHeaders() {
        return headers;
    }
    public void setHeaders(String headers) {
        this.headers = headers;
    }
    public String getValidation() {
        return validation;
    }
    public void setValidation(String validation) {
        this.validation = validation;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

   

}
