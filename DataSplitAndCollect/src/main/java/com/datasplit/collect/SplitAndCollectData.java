package com.datasplit.collect;


import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component(value = "SplitAndCollectData")
public class SplitAndCollectData {
    private Logger log = LoggerFactory.getLogger(SplitAndCollectData.class);

    @Value("${partitionSize}")
    public int partitionSize;


    public void splitListOfMap(Exchange ex){
        List<Map<String, Object>> input = (List<Map<String, Object>>) ex.getIn().getBody();
        if(input.size() > partitionSize){
            Collection<List<Map<String, Object>>> partitionedList = IntStream.range(0, input.size())
                    .boxed()
                    .collect(Collectors.groupingBy(partition -> (partition / partitionSize), Collectors.mapping(elementIndex -> input.get(elementIndex), Collectors.toList())))
                    .values();
            ex.getIn().setBody(partitionedList);
        }else{
            List<List<Map<String, Object>>> partitionedList = new ArrayList<>();
            partitionedList.add(input);
            ex.getIn().setBody(partitionedList);
        }
    }
}
