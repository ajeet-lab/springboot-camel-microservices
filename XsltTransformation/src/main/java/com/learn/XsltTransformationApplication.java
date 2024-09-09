package com.learn;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.camel.component.jacksonxml.JacksonXMLDataFormat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class XsltTransformationApplication {

	public static void main(String[] args) {
		SpringApplication.run(XsltTransformationApplication.class, args);
	}


	@Bean("jsontoxmlRootNameApi")
	public JacksonXMLDataFormat getCustomJacksonDataFormatwithRootnameApi() {
		JacksonXMLDataFormat jd=new JacksonXMLDataFormat();
		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.writer().withRootName("API");
		xmlMapper.setConfig(xmlMapper.getSerializationConfig().withRootName("Certificate"));
		jd.setXmlMapper(xmlMapper);
		return jd;
	}

	@Bean("jsontoxmlRootNameCertificate")
	public JacksonXMLDataFormat getCustomJacksonDataFormatwithRootnameCertificate() {
		JacksonXMLDataFormat jd=new JacksonXMLDataFormat();
		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.writer().withRootName("Certificate");
		xmlMapper.setConfig(xmlMapper.getSerializationConfig().withRootName("Certificate"));
		jd.setXmlMapper(xmlMapper);
		return jd;
	}
}
