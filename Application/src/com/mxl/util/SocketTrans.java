package com.mxl.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import com.mxl.pojo.Request;
import com.mxl.pojo.Response;

public class SocketTrans {
	
	
	public static Request socketToRequest(Socket socket) throws IOException {
			if(socket == null) {
				throw new RuntimeException("socket异常");
			}
			Request request = new Request();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = null;
			//读取请求行
			line = br.readLine();
			if (line != null) {
				String[] split = line.split(" ");
				request.setRequestMethod(split[0]);
				request.setRequestSource(split[1]);
				
				String[] split2 = split[1].split("\\?");//java特有，须转义
				//如果有参数，写回请求体
				if (split2.length > 1) {
					
					request.setRequestBody(split2[1]);
				}
				
			}
			//读取请求头
			while((line = br.readLine()) !=null && !"".equals(line)) {
				String[] split2 = line.split(" ");
				request.getMap().put(split2[0], split2[1].trim());
			}
			//如果有请求体，则读取
			if(request.getMap().containsKey("Content-Length:")) {
				
				String lenString = request.getMap().get("Content-Length:");
				int len = Integer.parseInt(lenString);
				char[] bytes = new char[len];
				br.read(bytes);
				request.setRequestBody(new String(bytes,0,len));
			}
			socket.shutdownInput();
		
		return request;
	}
	
	public static Response getResponse(Request request) {
		
		if (request == null) {
			throw new RuntimeException("参数为空");
		}
		Response response =new Response();
		response.setAgreement("HTTP/1.1");
		String filename = request.getRequestSource();
//		if (filename == null) {
//			throw new RuntimeException("文件不存在");
//		}
		File file = new File("source/"+filename);
		
		//判断文件存不存在
		if (file.exists()) {
			//文件存在
			response.setStateCode(StateCode.OK);
			String suffix = filename.substring(filename.lastIndexOf(".")+1);
			response.getMap().put("Content-Type:", FileTypeUtil.map.get(suffix));
			response.setResponseBody(file);
		}else {
			//文件不存在
			response.setStateCode(StateCode.NOT_FOUND);
			response.getMap().put("Content-Type: ", FileTypeUtil.map.get("html"));
			response.setResponseBody(new File("source","404.html"));
		}
		return response;
	}
}
