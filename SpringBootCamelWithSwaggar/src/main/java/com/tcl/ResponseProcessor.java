package com.tcl;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component(value = "ResponseProcessor")
public class ResponseProcessor implements Processor {

	@Override
	public void process(Exchange ex) throws Exception {
		String json = ex.getIn().getBody(String.class);
		String rectifiedJson = json.replace("[]", "\"\"");
		rectifiedJson = rectifiedJson.replace("[ ]", "\"\"");
		ex.getIn().setBody(rectifiedJson);
	}

	public void enrichJson(Exchange ex) throws Exception {
		String json = ex.getIn().getBody(String.class);
		String rectifiedJson = json.replace("[]", "\"\"");
		ex.getIn().setBody(rectifiedJson);
	}

	public void processForAuthenticateUdyamRegistration(Exchange ex) {
		String jsonStr = ex.getIn().getBody(String.class);
		JSONObject jObj = new JSONObject(jsonStr);
		if (jObj.has("result") && jObj.get("result").equals(null)) {
			jObj.put("result", new JSONObject());
		}
		ex.getIn().setBody(jObj);
	}

	public void setStatusMessageHeader(Exchange ex) throws Exception {
		String statusmessage = ex.getProperty("Message").toString();
		statusmessage = statusmessage.replaceAll("[^a-zA-Z0-9]", "").replaceAll("\\r\\n\\s|\\r|\\n|\\s", "");

		ex.setProperty("StatusMessage", statusmessage);
	}

	public void setStatusMessageHeaderForSaveAppDetails(Exchange ex) throws Exception {
		String statusmessage = ex.getProperty("ErrorMessage").toString();
		statusmessage = statusmessage.replaceAll("[^a-zA-Z0-9]", "").replaceAll("\\r\\n\\s|\\r|\\n|\\s", "");

		ex.setProperty("StatusMessage", statusmessage);
	}

	public void setStatusMessageHeaderForAuthenticateUdyamRegistration(Exchange ex) throws Exception {
		if (ex.getProperty("ErrorMessage") != null && ex.getProperty("ErrorMessage") != "") {
			String statusmessage = ex.getProperty("ErrorMessage").toString();
			statusmessage = statusmessage.replaceAll("[^a-zA-Z0-9]", "").replaceAll("\\r\\n\\s|\\r|\\n|\\s", "");

			ex.setProperty("StatusMessage", statusmessage);
		}
	}

	public void enrichJsonLatest(Exchange ex) throws Exception {
		String json = ex.getIn().getBody(String.class);
		String rectifiedJson = json.replace("[", "").replace("]", "").replace("/", "\\/");
		// String recJson = rectifiedJson.replaceAll("/", "\\/");
		ex.getIn().setBody(rectifiedJson);
	}

	public void setRetStatusHeader(Exchange ex) throws Exception {
		String trackWizzDownloadResponse = ex.getIn().getBody().toString();
		if (trackWizzDownloadResponse.contains("<TransactionStatus>CKYCSuccess</TransactionStatus>")) {
			ex.setProperty("CKYStatus", "SUCCESS");
		}

	}
}
