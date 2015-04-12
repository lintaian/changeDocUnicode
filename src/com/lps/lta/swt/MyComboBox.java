package com.lps.lta.swt;

import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class MyComboBox {
	public MyComboBox(JFrame frame) {
		Container cont = frame.getContentPane();
		String encodings[] = {"utf-8", "utf-8 with bom", "gbk"};
		JComboBox<String> jcb = new JComboBox<String>(encodings);
		jcb.setBorder(BorderFactory.createTitledBorder("选择目标编码"));
		jcb.setMaximumRowCount(3);
		cont.add(jcb);
	}
}
