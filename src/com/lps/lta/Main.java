package com.lps.lta;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Main {
	public static String in;
	public static String inEn;
	public static String out;
	public static String outEn;
	public static Set<String> codes = new HashSet<String>();
	static {
		codes.add("gbk");
		codes.add("utf8");
		codes.add("utf8bom");
	}
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("请输入参数");
			exit();
		}
		inEn = args[0];
		outEn = args[1];
		in = Util.replace(args[2]);
		out = Util.replace(args.length >= 4 ? args[3] : args[2]);
		checkEncode(inEn);
		checkEncode(outEn);
		if (inEn.equals(outEn)) {
			System.out.println("读取编码与写入编码一样,不需要转换.");
			exit();
		}
		File file = new File(in);
		listFile(file);
	}
	public static void listFile(File file) {
		if(file.isFile()) {
			String path = file.getPath();
			String outPath = path.replace(in, out);
			if ("gbk".equals(outEn)) {
				if ("utf8".equals(inEn)) {
					Util.changFileCode(path, outPath, "utf-8", "gbk");
				} else if ("utf8bom".equals(inEn)) {
					Util.utf8WithoutBom(path, outPath);
					Util.changFileCode(outPath, outPath, "utf-8", "gbk");
				}
			} else if ("utf8".equals(outEn)) {
				if ("gbk".equals(inEn)) {
					Util.changFileCode(path, outPath, "gbk", "utf-8");
				} else if ("utf8bom".equals(inEn)) {
					Util.utf8WithoutBom(path, outPath);
				}
			} else if ("utf8bom".equals(outEn)) {
				if ("gbk".equals(inEn)) {
					Util.changFileCode(path, outPath, "gbk", "utf-8");
					Util.utf8WithBom(outPath, outPath);
				} else if ("utf8".equals(inEn)) {
					Util.utf8WithBom(path, outPath);
				}
			}
		} else if(file.isDirectory()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				listFile(files[i]);
			}
		}
	}
	public static void checkEncode(String en) {
		if (!codes.contains(en)) {
			System.out.println(String.format("编码 '%s' 暂时不支持", en));
			StringBuilder sb = new StringBuilder("目前支持编码: ");
			for (String s : codes) {
				sb.append(s);
				sb.append("|");
			}
			sb.setLength(sb.length() - 1);
			System.out.println(sb.toString());
			exit();
		}
	}
	public static void exit() {
		System.out.println("参数格式为: inEn(gbk|utf8|utf8bom) outEn(gbk|utf8|utf8bom) inFile (outFile)");
		System.exit(1);
	}
}

