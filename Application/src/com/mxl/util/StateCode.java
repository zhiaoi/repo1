package com.mxl.util;

public enum StateCode {

	OK(200, "OK"), NOT_FOUND(404, "NOT FOUND");

	private int code;
	private String descript;

	private StateCode(int code, String descript) {
		this.code = code;
		this.descript = descript;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

}
