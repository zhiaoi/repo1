package com.mxl.application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.mxl.pojo.Request;
import com.mxl.pojo.Response;
import com.mxl.util.SocketTrans;
import com.mxl.util.ThreadPoolSocket;

public class Application {
	
	public static void main(String[] args) {

		try {
			ServerSocket ss = new ServerSocket(8888);
			System.out.println("服务器启动成功");
				while(true) {
					Socket socket = ss.accept();
					Request request = SocketTrans.socketToRequest(socket);
					Response response = SocketTrans.getResponse(request);
					ThreadPoolSocket.service(request, response, socket);
					
				}
		} catch (IOException e) {
			System.out.println("服务器内部错误:"+e.getMessage());
		}
	}
}
