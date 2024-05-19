package com.datasplit.collect;


import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component(value = "SplitAndCollectData")
public class SplitAndCollectData {
    private Logger log = LoggerFactory.getLogger(SplitAndCollectData.class);

    public void splitListOfString(Exchange ex){
        List<String> lists = ex.getIn().getBody(List.class);
        int listOfStringPartitionSize = (int) ex.getIn().getHeader("partitionSize");

        Collection<List<String>> partitionedListOfString = IntStream.range(0, lists.size()).boxed()
                .collect(Collectors.groupingBy(partition -> (partition / listOfStringPartitionSize), Collectors.mapping(elementIndex -> lists.get(elementIndex), Collectors.toList()))).values();
        log.info("splitListOfString :: ", partitionedListOfString);
        ex.getIn().setBody(partitionedListOfString);
    }


    public void splitListOfMap(Exchange ex){
        List<Map<String, Object>> listOfMap = (List<Map<String, Object>>) ex.getIn().getBody();
        int listOfMapPartitionSize = (int) ex.getIn().getHeader("partitionSize");

        Collection<List<Map<String, Object>>> partitionedListOfMap = IntStream.range(0, listOfMap.size()).boxed().collect(Collectors.groupingBy(partition -> (partition / listOfMapPartitionSize), Collectors.mapping(elementIndex -> listOfMap.get(elementIndex), Collectors.toList()))).values();

        log.info("splitListOfMap :: ", partitionedListOfMap);
        ex.getIn().setBody(partitionedListOfMap);
    }
}
