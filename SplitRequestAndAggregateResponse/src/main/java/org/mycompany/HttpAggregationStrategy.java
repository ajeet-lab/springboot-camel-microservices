package org.mycompany;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpAggregationStrategy implements AggregationStrategy {
	Logger log = LoggerFactory.getLogger(AggregationStrategy.class);
	List<Object> list = new ArrayList<>();

	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		Object map = newExchange.getIn().getBody();
		ArrayList<Object> list = null;

		// the first time we only have the new exchange
		if (oldExchange == null) {
			list = new ArrayList<Object>();
			list.add(map);
			newExchange.getIn().setBody(list);
			return newExchange;
		} else {
			// Integer newValue = newExchange.getIn().getBody(Integer.class);
			list = (ArrayList<Object>) oldExchange.getIn().getBody();
			list.add(map);
			return oldExchange;
		}
	}
}
