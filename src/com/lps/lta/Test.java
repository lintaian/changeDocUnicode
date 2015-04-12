package com.lps.lta;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Test {
	//-17,-69,-65,-27,-109,-120,10,
	public static void main(String[] args) throws Exception {
//		Util.utf8WithBom("f:/u.txt", "f:/u.txt");
//		Util.utf8WithoutBom("f:/u.txt", "f:/u.txt");
		System.out.println(Util.getFileCode("f:/aaa.txt"));
		File file = new File("f:/aaa.txt");
		InputStream in = new FileInputStream(file);
		byte[] bs = new byte[in.available()];
		in.read(bs);
		for (byte b : bs) {
			System.out.print(b + ",");
		}
		in.close();
	}
}