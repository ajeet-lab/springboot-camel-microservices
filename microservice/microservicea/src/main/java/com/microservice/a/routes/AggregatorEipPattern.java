package com.microservice.a.routes;

import java.util.ArrayList;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.microservice.a.entities.CurrencyExchange;
import com.microservice.a.routes.AggregatorEipPattern.ArrayListAggregationStretagy;

@Component
public class AggregatorEipPattern extends RouteBuilder {

	public class ArrayListAggregationStretagy implements AggregationStrategy {

		@SuppressWarnings("unchecked")
		@Override
		public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
			Object newBody = newExchange.getIn().getBody();
	        ArrayList<Object> list = null;
	        if (oldExchange == null) {
	            list = new ArrayList<Object>();
	            list.add(newBody);
	            newExchange.getIn().setBody(list);
	            return newExchange;
	        } else {
	            list = oldExchange.getIn().getBody(ArrayList.class);
	            list.add(newBody);
	            return oldExchange;
	        }
		}

	}

	@Override
	public void configure() throws Exception {
		
		from("file:files/aggretion-json")
		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
		//.log(LoggingLevel.INFO, "Aggretion-json : ${body}")
		.aggregate(simple("${body.to}"), new ArrayListAggregationStretagy())
		.completionSize(2)
		//.completionTimeout(HIGHEST)
		.to("log:aggregation-json-after");
		
	}

}
