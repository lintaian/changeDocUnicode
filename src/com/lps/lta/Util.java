package com.lps.lta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.regex.Matcher;

public class Util {
	/**
	 * 改变文件编码
	 * @param in
	 * @param out
	 * @param outEn
	 * @throws Exception 
	 */
	public static boolean changFileCode(String in, String out, String outEn) {
		try {
			byte[] bs = read(in);
			String inEn = isUtf8(bs) ? "utf-8" : "gbk";
			if ("gbk".equals(outEn)) {
				if ("utf-8".equals(inEn)) {
					bs = utf8(bs, false);
				}
				write(out, "gbk", new String(bs, inEn));
			} else if ("utf8".equals(outEn)) {
				if ("utf-8".equals(inEn)) {
					bs = utf8(bs, false);
				}
				write(out, "utf-8", new String(bs, inEn));
			} else if ("utf8bom".equals(outEn)) {
				if ("gbk".equals(inEn)) {
					bs = new String(bs, "gbk").getBytes("utf-8");
				}
				bs = utf8(bs, true);
				write(out, "utf-8", new String(bs, "utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
    }
	/**
	 * 读文件
	 * @param fileName
	 * @param encoding
	 * @throws Exception 
	 */
	public static byte[] read(String fileName) throws Exception {
		File file = new File(fileName);
		InputStream in = new FileInputStream(file);
		byte[] bs = new byte[in.available()];
		in.read(bs);
		in.close();
		return bs;
	}

	/**
	 * 写文件
	 * @param fileName 新的文件名
	 * @param encoding 写出的文件的编码方式
	 * @param str
	 */
	public static void write(String fileName, String encoding, String str) {
		try {
			File f = new File(fileName);
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), encoding));
			out.write(str);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将路径中的"\\"和"/"等替换为系统的分隔符
	 * @param str
	 */
	public static String replace(String str) {
		str = str.replaceAll("\\\\+", Matcher.quoteReplacement(File.separator));
		str = str.replaceAll("/+", Matcher.quoteReplacement(File.separator));
		return str;
	}
	/**
	 * utf8 与 utf8 bom 互相转换
	 * @param bs
	 * @param useBom
	 */
	public static byte[] utf8(byte[] bs, boolean useBom) {
		byte[] bom = {-17,-69,-65};
		boolean hasBom = false;
		if (bs.length > 3) {
			if (bs[0] == bom[0] && bs[1] == bom[1] && bs[2] == bom[2]) {
				hasBom = true;
			}
		}
		if (useBom) {
			if (hasBom) {
				return bs;
			} else {
				byte[] rs = new byte[bs.length + bom.length];
				System.arraycopy(bom, 0, rs, 0, bom.length);
				System.arraycopy(bs, 0, rs, bom.length, bs.length);
				return rs;
			}
		} else {
			if (hasBom) {
				byte[] rs = new byte[bs.length - bom.length];
				System.arraycopy(bs, bom.length, rs, 0, bs.length - bom.length);
				return rs;
			} else {
				return bs;
			}
		}
	}
	/**
	 * 判断文件是否是utf8
	 * @param bs
	 */
	public static boolean isUtf8(byte[] bs) {
		try {
			String str = new String(bs, "utf-8");
			byte[] data = str.getBytes("utf-8");
			if (Arrays.equals(bs, data)) {
				return true;
			} else {
				return false;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 判断文件是否是utf8
	 * @param bs
	 */
	public static boolean isUtf82(byte[] bs) {
		int ascN = 0;
		for (int i = 0; i < bs.length; ) {
			if (bs[i] > 0 && bs[i] < 128) {
				i += 1;
				ascN += 1;
			} else if (i < bs.length - 1 && (bs[i] & 0xE0) == 0xC0 && (bs[i + 1] & 0xC0) == 0x80) {
				i += 2;
			} else if (i < bs.length - 2 && (bs[i] & 0xF0) == 0xE0 && (bs[i + 1] & 0xC0) == 0x80 && (bs[i + 2] & 0xC0) == 0x80) {
				i += 3;
			} else {
				return false;
			}
		}
		if (ascN == bs.length) {
			return false;
		}
		return true;
	}
}