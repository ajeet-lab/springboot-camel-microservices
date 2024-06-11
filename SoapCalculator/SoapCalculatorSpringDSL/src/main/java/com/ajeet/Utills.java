package com.ajeet;


import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component(value = "Utills")
public class Utills {

    Logger log = LoggerFactory.getLogger(Utills.class);

    public Map<String, Object> checkEmptyOrNullValue(Exchange ex){
        Map<String, Object> map =  ex.getIn().getBody(Map.class);

        if(map.get("intA") == null || map.get("intB") == null ) {
            throw new IllegalArgumentException("Request should not be null value");
        }
        if(map.get("intA").toString() == "" || map.get("intB").toString() == "" ) {
            throw new IllegalArgumentException("Request should not be Empty");
        }

        return map;
    }

    public void setOperationNameAndSpace(Exchange ex) {
        String method =  ex.getIn().getHeader("CamelHttpUri", String.class).split("/")[3];
        log.info("httpUri :: "+ method);
        if(method.contains("Add") || method.contains("Subtract") || method.contains("Multiply") || method.contains("Divide")){
            ex.getIn().setHeader("operationName", method);
            ex.getIn().setHeader("operationNameSpace", "http://tempuri.org/");
        }else {
            throw new IllegalArgumentException("Method name should contain one of these (Add, Subtract, Multiply, or Divide) at the end of the URL. Ex. http://localhost:4000/api/v1/Add");
        }

    }
}
