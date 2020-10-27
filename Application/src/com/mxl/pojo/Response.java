package com.mxl.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.mxl.util.StateCode;

public class Response implements Serializable {

	private static final long serialVersionUID = 1L;
	// 响应行
	private String agreement = "HTTP/1.1 ";
	// 响应状态码
	private StateCode stateCode;
	// 响应头
	private Map<String, String> map = new HashMap<>();
	//响应体
	private Object responseBody;
	

	public Object getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(Object responseBody) {
		this.responseBody = responseBody;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public StateCode getStateCode() {
		return stateCode;
	}

	public void setStateCode(StateCode stateCode) {
		this.stateCode = stateCode;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "Response [agreement=" + agreement + ", stateCode=" + stateCode + ", map=" + map + ", requestBody="
				+ responseBody + "]";
	}
	
	

}
