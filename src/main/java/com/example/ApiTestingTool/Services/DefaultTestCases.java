package com.example.ApiTestingTool.Services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ApiTestingTool.Models.BodyData;
import com.example.ApiTestingTool.Models.TestResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class DefaultTestCases {
    @Autowired
    private ApiCall apiCall;

    public ArrayList<TestResult> validateStatusCode(BodyData data, JsonNode jsonBody)
    { 
        ArrayList<TestResult> result = new ArrayList<>();

           if (apiCall.apiCalls(data,jsonBody).getStatusCode().is2xxSuccessful()) {
                result.add(new TestResult(true, "Success status code" ));
            }
            else
                result.add(new TestResult(false, "Success status code" ));

        result.addAll( invalidParameters(data, jsonBody));
        result.addAll(invalidJsonBody(data,jsonBody));  
        return result;    
    }

    public ArrayList<TestResult> invalidParameters(BodyData data, JsonNode jsonBody) {
        ArrayList<TestResult> result = new ArrayList<>();
        if (replaceJsonStringValue(data, jsonBody)) {
            result.add(new TestResult(true, "Handling of Missing Parameters"));
        } else
            result.add(new TestResult(false, "Handling of Missing Parameters"));

        return result;
    }

    public ArrayList<TestResult> invalidJsonBody(BodyData data, JsonNode jsonBody)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode emptyNode = objectMapper.createObjectNode();
        apiCall.apiCalls(data,emptyNode);
        ArrayList<TestResult> result=new ArrayList<>();
          if(apiCall.apiCalls(data,jsonBody).getStatusCode().is2xxSuccessful())
          {
            result.add(new TestResult(true, "Api not crashing when invalid body passed" ));
          }
          else
            result.add(new TestResult(false, "Api crashing when invalid body passed" ));  

            return result;
    }

    private boolean replaceJsonStringValue(BodyData data, JsonNode node) {
        JsonNode jsonBody = node.deepCopy();
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonBody;
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String fieldName = entry.getKey();
                JsonNode value = entry.getValue();
                objectNode.remove(fieldName);
                if (apiCall.apiCalls(data, jsonBody).getStatusCode().is2xxSuccessful()) {
                    return false;
                }
                objectNode.put(fieldName, value);
                boolean success = replaceJsonStringValue(data, value);
                if (!success) {
                    return false;
                }
            }
        } else if (node.isArray()) {
            for (JsonNode arrayNode : node) {
                boolean success = replaceJsonStringValue(data, arrayNode);
                if (!success) {
                    // Handle failure condition if needed
                    return false;
                }
            }
        }
        return true;
    }

}
