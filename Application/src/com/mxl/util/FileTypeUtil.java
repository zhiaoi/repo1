package com.mxl.util;

import java.util.HashMap;
import java.util.Map;

public class FileTypeUtil {
	
	public static Map<String, String> map = null;
	static {
		map = new HashMap<>();
		map.put("text", "text/html;charset=utf-8");
		map.put("html", "text/html;charset=utf-8");
		map.put("jpg", "image/jpg;charset=utf-8");
		map.put("jpeg", "images/jpeg;charset=utf-8");
		map.put("png", "image/pnj;charset=utf-8");
		map.put("pdf", "application/pdf;charset=utf-8");
	}
}
