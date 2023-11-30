package com.spring.camel.messages;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class CamelMessages {
	
	public void afterDataPushInQueue(Exchange ex) {
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("Message","Data push in queue successfully..");
		ex.getIn().setBody(map);
		
	}
}
