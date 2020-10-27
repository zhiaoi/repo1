package com.mxl.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Request implements Serializable {

	private static final long serialVersionUID = 1L;

	// 请求方式
	private String requestMethod;
	// 请求资源
	private String requestSource;
	// 请求头
	private Map<String, String> map = new HashMap<>();
	// 请求体
	private String requestBody;

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestSource() {
		return requestSource;
	}

	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public String getRequestBody() {
		return requestBody;
	}

	@Override
	public String toString() {
		return "Request [requestMethod=" + requestMethod + ", requestSource=" + requestSource + ", map=" + map
				+ ", requestBody=" + requestBody + "]";
	}
	

}
