package com.spring.camel.processor;

import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import com.spring.camel.entities.Todos;

@Component
public class CamelProcessor {
	public JacksonDataFormat getJacksonDataFormat(Class<Todos> todos) {
        JacksonDataFormat format = new JacksonDataFormat();
        format.setUnmarshalType(todos);
        return format;
    }
}
