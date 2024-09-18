package com.learn.Routes;

import jakarta.activation.DataHandler;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.FileBinding;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

@Component
public class MyRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("jetty:http://localhost:9092/file?multipart=true")
                .log("Hit received at ${date:now} ")
                .process("FileProcessor")
                .process("RequestProcessor")
                .setHeader("Content-Type", simple("application/json"))
                .log("Hit received at ${date:now} and Final is ${body}");
    }

}
