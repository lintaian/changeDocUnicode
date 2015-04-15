package com.lps.lta;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {
	public static String in;
	public static String out;
	public static String outEn;
	public static Set<String> codes = new HashSet<String>();
	static {
		codes.add("gbk");
		codes.add("utf8");
		codes.add("utf8bom");
	}
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("请输入参数");
			exit();
		}
		if (args.length == 2) {
			System.out.println("你没有设定文件输出路径,将覆盖原文件.是否继续?(yes)");
			byte[] input = new byte[3];
			try {
				System.in.read(input, 0, 3);
				if (!"yes".equals(new String(input))) {
					exit();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		outEn = args[0];
		in = Util.replace(args[1]);
		out = Util.replace(args.length >= 3 ? args[2] : args[1]);
		checkEncode(outEn);
		File file = new File(in);
		listFile(file);
		System.out.println("转换结束");
	}
	public static void listFile(File file) {
		if(file.isFile()) {
			String path = file.getPath();
			String outPath = path.replace(in, out);
			if (!Util.changFileCode(path, outPath, outEn)) {
				System.out.println(String.format("文件\"%s\"转换失败", path));
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