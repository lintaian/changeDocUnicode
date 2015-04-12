package com.lps.lta;
import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;

public class Util {
	/**
	 * 改变文件编码
	 * @param in
	 * @param out
	 * @param outEn
	 */
	public static void changFileCode(String in, String out, String outEn) {
		String inEn = getFileCode(in);
        String str = read(in, inEn);
        write(out, outEn, str);
    }   
	/**
	 * 读文件
	 * 
	 * @param fileName
	 * @param encoding
	 */
	private static String read(String fileName, String encoding) {
		String str = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), encoding));
			String temp = "";
			while ((temp = in.readLine()) != null) {
				str += temp + "\n";
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 写文件
	 * 
	 * @param fileName
	 *            新的文件名
	 * @param encoding
	 *            写出的文件的编码方式
	 * @param str
	 */
	private static void write(String fileName, String encoding, String str) {
		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), encoding));
			out.write(str);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将路径中的"\\"和"/"等替换为系统的分隔符
	 * @param str
	 * @return
	 */
	public static String replace(String str) {
		str = str.replaceAll("\\\\+", Matcher.quoteReplacement(File.separator));
		str = str.replaceAll("/+", Matcher.quoteReplacement(File.separator));
		return str;
	}
	/**
	 * 获取文件的编码
	 * @param filePath
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getFileCode(String filePath) {
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		Charset charset = null;
		File f = new File(filePath);
		try {
			charset = detector.detectCodepage(f.toURL());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (charset != null) {
			return charset.name();
		}
		return "";
	}
	/**
	 * 判断文件是否为制定编码
	 * @param filePath
	 * @param en
	 * @return
	 */
	public static boolean judgeFileCode(String filePath, String en) {
		Charset charset = Charset.forName(getFileCode(filePath));
		Charset charset2 = Charset.forName(en);
		return charset.name().equalsIgnoreCase(charset2.name());
	}
	/**
	 * utf8 转 utf8 bom
	 * @param in
	 * @param out
	 * @throws Exception
	 */
	public static void utf8WithBom(String in, String out) {
		try {
			byte[] bom = {-17,-69,-65};
			File file = new File(in);
			InputStream is = new FileInputStream(file);
			byte[] bs = new byte[is.available()];
			is.read(bs);
			is.close();
			boolean flag = true;
			if (bs.length > 3) {
				if (bs[0] == bom[0] && bs[1] == bom[1] && bs[2] == bom[2]) {
					flag = false;
				}
			}
			String content = "";
			if (flag) {
				content = new String(bom, "utf-8");
			}
			content += new String(bs, "utf-8");
			write(out, "utf-8", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * utf8 bom 转 utf8
	 * @param in
	 * @param out
	 * @throws Exception
	 */
	public static void utf8WithoutBom(String in, String out) {
		try {
			byte[] bom = {-17,-69,-65};
			File file = new File(in);
			InputStream is = new FileInputStream(file);
			byte[] bs = new byte[is.available()];
			is.read(bs);
			is.close();
			boolean flag = false;
			if (bs.length > 3) {
				if (bs[0] == bom[0] && bs[1] == bom[1] && bs[2] == bom[2]) {
					flag = true;
				}
			}
			String content = "";
			if (flag) {
				byte[] temp = new byte[bs.length - 3];
				for (int i = 3; i < bs.length; i++) {
					temp[i-3] = bs[i];
				}
				content = new String(temp, "utf-8");
			} else {
				content = new String(bs, "utf-8");
			}
			write(out, "utf-8", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
