package com.example.ApiTestingTool.Models;

public class TestResult {


    boolean testRes;
    String description;


    public TestResult(boolean testRes,String description)
    {
        this.testRes=testRes;
        this.description=description;
    }

    public boolean isTestRes() {
        return testRes;
    }
    public void setTestRes(boolean testRes) {
        this.testRes = testRes;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    
    
}
