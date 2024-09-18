package com.learn;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
            // Sample input data string
            String input = "12345678012378909876";

            // Convert input string to desired response structure
            JSONObject jsonResponse = convertToResponseStructure(input);

            // Print the response
            System.out.println(jsonResponse.toString());
        }

        public static JSONObject convertToResponseStructure(String input) {
            // Split the input into parts (e.g., "12345678" -> ["1234", "5678"])
            List<String> parts = new ArrayList<>();
            for (int i = 0; i < input.length(); i += 4) {
                parts.add(input.substring(i, Math.min(i + 4, input.length())));
            }

            // Create a JSON object to hold the response structure
            JSONObject response = new JSONObject();
            response.put("refKey", parts);

            return response;
        }
}
