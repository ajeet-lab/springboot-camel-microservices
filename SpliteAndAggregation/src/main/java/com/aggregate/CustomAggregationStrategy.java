package com.aggregate;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CustomAggregationStrategy implements AggregationStrategy {
    Logger log = LoggerFactory.getLogger(AggregationStrategy.class);
    @Override
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
            list =  (ArrayList<Object>) oldExchange.getIn().getBody();
            list.add(map);
            return oldExchange;
        }
    }
}
