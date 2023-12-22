package com.example.ApiTestingTool.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.ApiTestingTool.Models.BodyData;
import com.example.ApiTestingTool.Models.TestResult;
import com.example.ApiTestingTool.Services.CreatingTestCase;
import com.fasterxml.jackson.databind.JsonNode;


@RestController
public class Mycontroller {
    @Autowired
    private CreatingTestCase creatingTestCase;
    
    @PostMapping("/create")
    public ArrayList<TestResult> createTestcases(@RequestBody BodyData data) {
        
        ArrayList<TestResult> results=new ArrayList<>();

         for ( JsonNode iterable_element : data.getValidation()) {
            
            if(data.getBody().get((iterable_element.get("attribute").textValue())).isNumber())
            {
                System.out.println( iterable_element.get("attribute").textValue()+" isNumber");
               results.addAll(creatingTestCase.generateTestcases(data,iterable_element.get("attribute").textValue(), iterable_element.get("comparator").textValue(), iterable_element.get("value").asInt()));
            }
            else
            {
              results.addAll(creatingTestCase.generateTestcasesForString(data,iterable_element.get("attribute").textValue(), iterable_element.get("comparator").textValue(), iterable_element.get("value").textValue()));
            }
           
         }
        return results;

    }

}
