package com.example.ApiTestingTool.Services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ApiTestingTool.Models.BodyData;
import com.example.ApiTestingTool.Models.TestResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CreatingTestCase {
    @Autowired
    private ApiCall apiCall;

    public ArrayList<TestResult> generateTestcasesForString(BodyData data, String attribute, String comparator,
            String replaceValue) {

        ArrayList<TestResult> result = new ArrayList<>();
        if (comparator.equals("<") || comparator.equals(">")) {
            return generateTestcasesForStringLength(data, attribute, comparator, Integer.valueOf(replaceValue));
        } else {

            JsonNode Json1, Json2;
            Json1 = data.getBody().deepCopy();
            Json2 = data.getBody().deepCopy();
            replaceJsonStringValue(Json2, attribute, replaceValue);
            if (apiCall.apiCalls(data, Json1).getStatusCode().is2xxSuccessful()) {
                result.add(new TestResult(true, "For " + attribute + " value " + Json1.get(attribute)));
            } else
                result.add(new TestResult(false, "For " + attribute + " value " + Json1.get(attribute)));

            if (apiCall.apiCalls(data, Json2).getStatusCode().is2xxSuccessful()) {
                result.add(new TestResult(false, "For " + attribute + " value " + replaceValue));
            } else
                result.add(new TestResult(true, "For " + attribute + " value " + replaceValue));

        }
        return result;
    }

    public ArrayList<TestResult> generateTestcasesForStringLength(BodyData data, String attribute, String comparator,
            int maxLength) {

        JsonNode Json1, Json2;
        Json1 = data.getBody().deepCopy();
        Json2 = data.getBody().deepCopy();
        ArrayList<TestResult> result = new ArrayList<>();
        if (comparator.equals("<")) {

            String newString = "";
            for (int i = 1; i < maxLength; i++) {
                newString += "a";
            }
            replaceJsonStringValue(Json1, attribute, newString);
            replaceJsonStringValue(Json2, attribute, newString + "aa");
              

            if (apiCall.apiCalls(data, Json1).getStatusCode().is2xxSuccessful()) {
                result.add(new TestResult(true,
                        "Length for " + attribute + " is " + (Json1.get(attribute)).asText().length() ));
            } else
                result.add(new TestResult(false,
                        "Length for " + attribute + " is " + (Json1.get(attribute)).asText().length() ));

            if (apiCall.apiCalls(data, Json2).getStatusCode().is2xxSuccessful()) {
                result.add(new TestResult(false,
                        "Length for " + attribute + " is " + (Json2.get(attribute)).asText().length() ));
            } else
                result.add(new TestResult(true,
                        "Length for " + attribute + " is " + (Json2.get(attribute)).asText().length() ));

        }
        return result;
    }

    public ArrayList<TestResult> generateTestcases(BodyData data, String attribute, String comparator, int value) {

        JsonNode Json1, Json2;
        Json1 = data.getBody().deepCopy();
        Json2 = data.getBody().deepCopy();
        ArrayList<TestResult> result = new ArrayList<>();
        if (comparator.equals(">")) {

            
            replaceJsonIntegerValue(Json1, attribute, value - 1);
            replaceJsonIntegerValue(Json2, attribute, value + 1);
            if (apiCall.apiCalls(data, Json1).getStatusCode().is2xxSuccessful()) {

                result.add(new TestResult(false, "Value of " + attribute + " is " + (value - 1)));
            } else {
                result.add(new TestResult(true, "Value of " + attribute + " is " + (value - 1)));
            }

            if (apiCall.apiCalls(data, Json2).getStatusCode().is2xxSuccessful()) {
                result.add(new TestResult(true, "Value of " + attribute + " is " + (value + 1)));
            } else
                result.add(new TestResult(false, "Value of " + attribute + " is " + (value + 1)));

        } else if (comparator.equals("<")) {

            replaceJsonIntegerValue(Json1, attribute, value - 1);
            replaceJsonIntegerValue(Json2, attribute, value + 1);

            if (apiCall.apiCalls(data, Json1).getStatusCode().is2xxSuccessful())
                result.add(new TestResult(true, "Value of " + attribute + " is " + (value - 1)));
            else
                result.add(new TestResult(false, "Value of " + attribute + " is " + (value - 1)));

            if (apiCall.apiCalls(data, Json2).getStatusCode().is2xxSuccessful())
                result.add(new TestResult(false, "Value of " + attribute + " is " + (value + 1)));
            else
                result.add(new TestResult(true, "Value of " + attribute + " is " + (value + 1)));
        }

        return result;
    }

    // private static void traverseJson(JsonNode node) {
    // if (node.isObject()) {
    // node.fields().forEachRemaining(entry -> {
    // String fieldName = entry.getKey();
    // JsonNode value = entry.getValue();
    // traverseJson(value);
    // });
    // }
    // }

     public void traverseJson(JsonNode node) {
        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                String fieldName = entry.getKey();
                JsonNode value = entry.getValue();
                traverseJson(value);
            });
        } else if (node.isArray()) {
            for (JsonNode arrayNode : node) {
                traverseJson(arrayNode);
            }
        }
    }

    private static void replaceJsonIntegerValue(JsonNode node, String attribute, int replaceValue) {
       
        // if (node.isObject()) {
        //     node.fields().forEachRemaining(entry -> {
        //         String fieldName = entry.getKey();
        //         JsonNode value = entry.getValue();

        //         if (fieldName.equals(attribute)) {
        //             ObjectMapper mapper = new ObjectMapper();
        //             JsonNode intNode = mapper.convertValue(replaceValue, JsonNode.class);
        //             entry.setValue(intNode);
        //         }

        //         replaceJsonIntegerValue(value, attribute, replaceValue);
        //     });
        // }


         if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                String fieldName = entry.getKey();
                JsonNode value = entry.getValue();
                
                if (fieldName.equals(attribute)) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode intNode = mapper.convertValue(replaceValue, JsonNode.class);
                    entry.setValue(intNode);
                }

                replaceJsonIntegerValue(value, attribute, replaceValue);

            });
        } else if (node.isArray()) {
            for (JsonNode arrayNode : node) {
                replaceJsonIntegerValue(arrayNode, attribute, replaceValue);
            }
        }

    }

    private void replaceJsonStringValue(JsonNode node, String attribute, String replaceValue) {
        // if (node.isObject()) {
        //     node.fields().forEachRemaining(entry -> {
        //         String fieldName = entry.getKey();
        //         JsonNode value = entry.getValue();

        //         if (fieldName.equals(attribute)) {
        //             ObjectMapper mapper = new ObjectMapper();
        //             JsonNode intNode = mapper.convertValue(replaceValue, JsonNode.class);
        //             entry.setValue(intNode);
        //         }

        //         replaceJsonStringValue(value, attribute, replaceValue);
        //     });
        // }



        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                String fieldName = entry.getKey();
                JsonNode value = entry.getValue();
               
                 if (fieldName.equals(attribute)) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode intNode = mapper.convertValue(replaceValue, JsonNode.class);
                    entry.setValue(intNode);
                }
                
                replaceJsonStringValue(value, attribute, replaceValue);

            });
        } else if (node.isArray()) {
            for (JsonNode arrayNode : node) {
                replaceJsonStringValue(arrayNode, attribute, replaceValue);
            }
        }
    }

}
