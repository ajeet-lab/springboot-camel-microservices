package com.learn.processor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;


@Component(value = "FileProcessor")
public class FileProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        try {
            // Get the HttpServletRequest from the exchange
            HttpServletRequest request = exchange.getIn().getBody(HttpServletRequest.class);
            // Retrieve the file part (check the input field name)

            Part filePart = request.getPart("demo");

            if (filePart != null) {
                // Use try-with-resources to ensure the stream is closed properly
                try (InputStream inputStream = filePart.getInputStream()) {
                    // Convert the file content to a String
                    String fileContent = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                    // Set the file content as the message body
                    exchange.getIn().setBody(fileContent);
                    // Optionally, log the content for debugging
                }
            } else {
                throw new Exception("File part 'demo' not found in the request.");
            }
        } catch (Exception e) {
            // Log the exception and set an error message
            exchange.getIn().setBody("Error processing file upload: " + e.getMessage());
            throw e;
        }


    }
}
