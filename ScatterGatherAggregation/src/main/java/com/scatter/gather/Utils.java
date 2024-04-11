package com.scatter.gather;

import org.apache.camel.Exchange;

import java.util.Map;

public class Utils {
    public void fecthKey(Exchange ex){
        Map<String, Integer> body = ex.getIn().getBody(Map.class);
        for(Map.Entry<String, Integer> entry : body.entrySet()){
            //System.out.println("Key : "+entry.getKey() + " Value : "+entry.getValue());
            ex.getIn().setHeader("key", entry.getKey());
            ex.getIn().setHeader("value", entry.getValue());
        }
    }
}
