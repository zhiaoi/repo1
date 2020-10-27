package com.mxl.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mxl.pojo.Request;
import com.mxl.pojo.Response;

public class ThreadPoolSocket {

	private static final ExecutorService THREADPOOL;
	static {
		THREADPOOL = Executors.newCachedThreadPool();
	}

	public static void service(Request request, Response response, Socket socket) {

			if ("GET".equals(request.getRequestMethod())) {
				doGet(request, response, socket);
			} else if ("POST".equals(request.getRequestMethod())) {
				doPost(request, response, socket);
			}

	}

	private static void doGet(Request request, Response response, Socket socket) {
		//GET的请求参数在请求行       localhost:80/index.jpg?username=123&password=123
		
		String body = request.getRequestBody();
		if (body != null && !"".equals(body)) {
			//不为空，切割
			String[] split = body.split("&");
			
			if ("username=123".equals(split[0]) && "password=123".equals(split[1])) {
				//密码和用户名相同，跳转到success.html
				response.setStateCode(StateCode.OK);
				response.setResponseBody(new File("source","success.html"));
			}else {
				//密码和用户名不相同，跳转到fail.html
				response.setStateCode(StateCode.NOT_FOUND);
				response.setResponseBody(new File("source","fail.html"));
			}
		}
		writeToBrower(socket, response);
	}

	private static void doPost(Request request, Response response, Socket socket) {
		doGet(request, response, socket);
	}

	public static void writeToBrower(Socket socket, Response response) {

		try {
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
//			BufferedInputStream bis = new BufferedInputStream(new FileInputStream((File) response.getRequestBody()));

			StringBuilder sb = new StringBuilder();
			// 响应行
			sb.append(response.getAgreement()).append(" ").append(response.getStateCode().getCode()).append(" ")
					.append(response.getStateCode().getDescript()).append("\r\n");
			bos.write(sb.toString().getBytes());
			// 清空缓冲区
			sb.delete(0, sb.length());
			// 响应头

			Set<Entry<String, String>> set = response.getMap().entrySet();
			for (Entry<String, String> entry : set) {

				sb.append(entry.getKey()).append("").append(entry.getValue()).append("\r\n");
			}

			bos.write(sb.toString().getBytes());
			// 空行
			bos.write("\n".getBytes());
//			// 读取服务端的数据，传给客户端
//			byte[] bytes = new byte[1024];
//			int len = 0;
//			while ((len = bis.read(bytes)) != -1) {
//				bos.write(bytes, 0, len);
//			}
//			bos.flush();
//			socket.shutdownOutput();
//			bos.close();
//			bis.close();
//			socket.close();
			
			//响应体
			// 如果是文件就写回文件，如果是文文
			if(response.getResponseBody() instanceof File) {
				// 将资源从本地磁盘读取
				BufferedInputStream  bis = 
						new BufferedInputStream(
								new FileInputStream(
										(File)response.getResponseBody()));
				int count = -1;
				byte[] bytes = new byte[1024];
				while((count = bis.read(bytes)) != -1) {
					bos.write(bytes, 0, count);
				}
				bos.flush();
				bis.close();
			}
			socket.shutdownOutput();
			bos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
