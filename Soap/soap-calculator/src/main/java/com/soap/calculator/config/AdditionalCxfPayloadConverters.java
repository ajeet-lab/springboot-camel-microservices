package com.soap.calculator.config;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.component.cxf.CxfPayload;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.staxutils.StaxUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditionalCxfPayloadConverters {
	 static Logger log = LoggerFactory.getLogger(AdditionalCxfPayloadConverters.class);
	  
	    @Converter
	    public static CxfPayload<SoapHeader> toCxfPayload(String xml,Exchange exchange) {
//	    	String xml1 = "<tem:Add xmlns:tem=\"http://tempuri.org/\">\n"
//	    			+ "         <tem:intA>5</tem:intA>\n"
//	    			+ "         <tem:intB>6</tem:intB>\n"
//	    			+ "      </tem:Add>\n";
	    	
	    	String xml1 = xml;
	       log.info("body passed in WSDL="+xml1);
	       List<Source> elements = new ArrayList<Source>();
	       try {
			elements.add(new DOMSource(StaxUtils.read(new StringReader(xml1)).getDocumentElement()));
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
	       final CxfPayload<SoapHeader> cxfPayload = new CxfPayload<SoapHeader>(null, elements,null);
	       exchange.getIn().setBody(cxfPayload);
	       log.info("cxf payload: "+cxfPayload);
	       return cxfPayload;
	       
	    }
}
