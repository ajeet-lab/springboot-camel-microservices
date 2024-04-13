package com.soap.calculator.config;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.tempuri.Add;

public class GetCalculatorRequestBuilder implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Add add=new Add();
		int a=5;
		int b=7;
		   add.setIntA(a);;
		   add.setIntB(b);
		  exchange.getIn().setBody(new Object[] {a,b});
		
	}

}
