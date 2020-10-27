package com.mxl.util;

import java.io.File;

public class test {
	
	public static void main(String[] args) {
		File file = new File("source", "/2.jpg");
		System.out.println(file.exists());
		System.out.println(file);
	}
}
