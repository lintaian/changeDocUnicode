package com.lps.lta;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Test {
	//-17,-69,-65
	public static void main(String[] args) throws Exception {
		File file = new File("C:/Users/lta/Desktop/gbk.txt");
		InputStream in = new FileInputStream(file);
		byte[] bs = new byte[in.available()];
		in.read(bs);
		for (byte b : bs) {
			System.out.print(b + ",");
		}
		in.close();
	}
}