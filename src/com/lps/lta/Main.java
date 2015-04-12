package com.lps.lta;

import java.io.File;

public class Main {
	public static String in;
	public static String out;
	public static String outEn;
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("请输入参数--> method in (out)");
			return;
		}
		String method = args[0];
		in = Util.replace(args[1]);
		out = Util.replace(args.length >= 3 ? args[2] : args[1]);
		if ("gbk2utf8".equals(method)) {
			outEn = "utf-8";
			listFile(new File(in));
		} else if ("gbk2utf8bom".equals(method)) {
			outEn = "utf-8";
			listFile(new File(in));
			in = out;
			utf(new File(in), true);
		} else if ("utf82gbk".equals(method)) {
			outEn = "gbk";
			listFile(new File(in));
		} else if ("utf82utf8bom".equals(method)) {
			outEn = "utf-8";
			utf(new File(in), true);
		} else if ("utf8bom2gbk".equals(method)) {
			outEn = "gbk";
			utf(new File(in), false);
			in = out;
			listFile(new File(in));
		} else if ("utf8bom2utf8".equals(method)) {
			outEn = "utf-8";
			utf(new File(in), false);
		} 
	}
	public static void listFile(File file) {
		if(file.isFile()) {
			String path = file.getPath();
			String outPath = path.replace(in, out);
			Util.changFileCode(path, outPath, outEn);
		} else if(file.isDirectory()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				listFile(files[i]);
			}
		}
	}
	public static void utf(File file, boolean bom) {
		if(file.isFile()) {
			String path = file.getPath();
			String outPath = path.replace(in, out);
			if (bom) {
				Util.utf8WithBom(path, outPath);
			} else {
				Util.utf8WithoutBom(path, outPath);
			}
		} else if(file.isDirectory()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				listFile(files[i]);
			}
		}
	}
}

