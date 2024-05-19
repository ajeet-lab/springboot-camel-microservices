package com.datasplit.collect;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class MySpringBootApplication{

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }

}
