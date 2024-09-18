package com.learn.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value = "RequestProcessor")
public class RequestProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        // Sample input data string
        String input = exchange.getIn().getBody().toString();

        // Convert input string to desired response structure
        JSONObject jsonRequest = convertToRequestStructure(input);

        exchange.getIn().setBody(jsonRequest.toString());
    }

    public static JSONObject convertToRequestStructure(String input) {
        // Split the input into parts (e.g., "12345678" -> ["1234", "5678"])
        List<String> parts = new ArrayList<>();
        for (int i = 0; i < input.length(); i += 12) {
            parts.add(input.substring(i, Math.min(i + 12, input.length())));
        }

        // Create a JSON object to hold the response structure
        JSONObject response = new JSONObject();
        response.put("refKey", parts);
        return response;
    }
}
