package com.lps.lta.swt;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Swing {
	public static void main(String[] args) {
		JFrame f = new JFrame("文件编码转换工具");
		f.setSize(400, 300);
		f.setBackground(Color.WHITE);
		f.setLocation(300, 200);
		Container container = f.getContentPane();
		f.setLayout(new GridLayout(3, 3));
		
		String encodings[] = {"utf-8", "utf-8 with bom", "gbk"};
		JComboBox<String> jcb = new JComboBox<String>(encodings);
		jcb.setBorder(BorderFactory.createTitledBorder("选择目标编码"));
		jcb.setMaximumRowCount(3);
		container.add(jcb);
		
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("test");
		container.add(chooser);
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
}
