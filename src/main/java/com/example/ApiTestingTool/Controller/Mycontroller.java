package com.example.ApiTestingTool.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.ApiTestingTool.Models.ApiBody;
import com.example.ApiTestingTool.Models.BodyData;
import com.example.ApiTestingTool.Models.TestResult;
import com.example.ApiTestingTool.Repository.BodyDataRepository;
import com.example.ApiTestingTool.Services.CreatingTestCase;
import com.example.ApiTestingTool.Services.DefaultTestCases;
import com.example.ApiTestingTool.Services.UpdateHistory;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class Mycontroller {
    @Autowired
    private CreatingTestCase creatingTestCase;
    @Autowired
    private BodyDataRepository bodyDataRepository;
    @Autowired
    private DefaultTestCases defaultTestCases;
    @Autowired
    private UpdateHistory updateHistory;
    
    @PostMapping("/create")
    public ArrayList<TestResult> createTestcases(@RequestBody BodyData data) {
        
       

        ArrayList<TestResult> results=new ArrayList<>();
          results.addAll(defaultTestCases.validateStatusCode(data,data.getBody()));
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
         //creatingTestCase.traverseJson(data.getBody());
        return results;

    }

    @PostMapping("save")
    public ApiBody SaveAll(@RequestBody ApiBody data){
        return  bodyDataRepository.save(data);
    }

    @GetMapping("getAll")
    public List<ApiBody> getAll()
    {
        List<ApiBody> dataList = bodyDataRepository.findAll();
        Collections.reverse(dataList); // Reverse the list
        return dataList;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteId(@PathVariable int id) {
        // Logic to delete by ID from your repository
        bodyDataRepository.deleteById(id);

        // Returning an appropriate response (e.g., success or error status)
        return ResponseEntity.ok().body("Deleted successfully");
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateData(@PathVariable int id, @RequestBody ApiBody data) {
      
        updateHistory.UpdateData(id, data);
        return ResponseEntity.ok().body("Deleted successfully");
    }

    
}
