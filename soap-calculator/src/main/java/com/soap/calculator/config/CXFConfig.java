package com.soap.calculator.config;

import org.apache.camel.CamelContext;
import org.apache.camel.component.cxf.CxfComponent;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.DataFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CXFConfig {
	public static final String SERVICE_ADDRESS = "http://www.dneonline.com/calculator.asmx";
    private CamelContext context;

	@Bean(name = {"cxfEndpoint"}) 
	public CxfEndpoint myEndpoint() {
		CxfComponent cxfComponent = new CxfComponent(context);
	    CxfEndpoint cxfEndpoint = new CxfEndpoint(SERVICE_ADDRESS,cxfComponent);
	    cxfEndpoint.setWsdlURL("/calculator.wsdl");
	    cxfEndpoint.setLoggingFeatureEnabled(true);
	    cxfEndpoint.setServiceName("{http://tempuri.org/}Calculator");
	    cxfEndpoint.setPortName("{http://tempuri.org/}CalculatorSoap");
	    cxfEndpoint.setDataFormat(DataFormat.PAYLOAD);
	    //cxfEndpoint.setDataFormat(DataFormat.CXF_MESSAGE);
	    return cxfEndpoint;
	}
}
