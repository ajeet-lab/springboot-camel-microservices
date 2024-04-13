package org.mycompany;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	private Logger log = LoggerFactory.getLogger(Utils.class);
	
	public Map<String, Object> checkEmptyOrNullValue(Exchange ex) {
		Map<String, Object> map = ex.getIn().getBody(Map.class);
		Map<String, Object> message = new HashMap<>();
		
		if(map.get("intA") == null || map.get("intB") == null ) {
			throw new IllegalArgumentException("Request should not be null value");
		}		
		if(map.get("intA").toString() == "" || map.get("intB").toString() == "" ) {
			throw new IllegalArgumentException("Request should not be Empty");
		}	
		return map;
	}

	public void setOperationNameAndSpace(Exchange ex) {
		String httpUri =  ex.getIn().getHeader("CamelHttpUri").toString().split("/")[3];
		String method = httpUri.substring(0,1).toUpperCase().concat(httpUri.substring(1));
		ex.getIn().setHeader("operationName", method);
		ex.getIn().setHeader("operationNameSpace", "http://tempuri.org/");
	}
}
