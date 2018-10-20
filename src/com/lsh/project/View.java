package com.lsh.project;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.lsh.project.model.Model;

/**
 * 
    * @ClassName: View
    * @Description: 给文件查找器写一个界面
    * @author lingsh
    * @date Sep 30, 2018
    * 
    * *<br>
    * 基本组件：<br>
    *	窗口*1 	位置：屏幕正中Toolkit.getDefaultToolkit().getScreenSize() <br>
    *			大小：650*350<br>
    *	面板*3	位置：上下排列 BorderLayout(North,South)	<br>
    *	输入框*2 	位置：上下排列 <br>
    *	按钮*2	位置：上下排列 与输入框面板 左右排列<br>
    *	文本*1	位置：与面板上 上下排列<br>
    *			(面板上(输入框面板(输入框)，搜索按钮) 面板下(显示文本区域))<br>
 */

public class View {

	/** 
	 * 当前屏幕高度
	 */
	public static final int WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	/** 
	 * 当前屏幕宽度
	 */
	public static final int WINDOW_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;

	/** 
	 * 定义应用初始的高度
	 */
	public static final int VIEW_HEIGHT = 300;
	/** 
	 * 定义应用初始的宽度
	 */
	public static final int VIEW_WIDTH = 600;

	/** 
	 * 定义字符大小 方便窗口和文本框的对应 宽度/字符大小
	 */
	public static final int CHAR_SIZE = 16;

	/**
	 * 定义多行文本和输入框距离
	 */
	public static final int MARGIN_SIZE = 140;

	/** 
	 * 各组件
	 */
	private JFrame jframe;
	private JButton jbtnSearch;
	private JButton jbtnOpenDir;
	private JTextField jtPath;
	private JTextField jtKeyWords;
	private JTextArea jtaResult;
	private Font font;
	private JComboBox<String> jcbList;
	private String model;

	/** 
	 * 创建一个新的实例 View.
	 */
	public View() {
		init();
		panelNorth();
		panelSouth();
		event();
	}

	/**
	 * @Title: init
	 * @Description: 窗口初始化 可见 大小 位置 布局 关闭操作
	*/
	public void init() {
		jframe = new JFrame("Files Found");
		jframe.setVisible(true);
		jframe.setSize(VIEW_WIDTH, VIEW_HEIGHT);
		jframe.setLocation((WINDOW_WIDTH - VIEW_WIDTH) / 2, (WINDOW_HEIGHT - VIEW_HEIGHT) / 2);
		jframe.setLayout(new BorderLayout());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		font = new Font(null, Font.PLAIN, 14);
		model = Model.MODEL_FOUND;
	}

	/** 
	* @Title: panelNorth
	* @Description: 面板上 两个输入框 两个按钮
	 */
	private void panelNorth() {
		jbtnSearch = new JButton("Search");
		jbtnOpenDir = new JButton("Open Dir");

		jtPath = new JTextField((VIEW_WIDTH - jbtnSearch.getWidth()) / CHAR_SIZE);
		jtPath.setFont(font);
		jtKeyWords = new JTextField(10);
		jtKeyWords.setFont(font);

		jcbList = new JComboBox<>(new String[] { Model.MODEL_FOUND, Model.MODEL_DUPLICATED });

		JPanel jpanelInput = new JPanel(new BorderLayout());
		jpanelInput.add(jtPath, BorderLayout.NORTH);
		jpanelInput.add(jtKeyWords, BorderLayout.CENTER);
		jpanelInput.add(jcbList, BorderLayout.EAST);

		JPanel jpanelBtn = new JPanel(new BorderLayout());
		jpanelBtn.add(jbtnOpenDir, BorderLayout.NORTH);
		jpanelBtn.add(jbtnSearch, BorderLayout.SOUTH);

		JPanel jpanelNorth = new JPanel(new BorderLayout());
		jpanelNorth.add(jpanelInput, BorderLayout.WEST);
		jpanelNorth.add(jpanelBtn, BorderLayout.EAST);

		jframe.add(jpanelNorth, BorderLayout.NORTH);
	}

	/**
	 * @Title: panelSouth
	 * @Description: 结果显示面板
	 */
	private void panelSouth() {
		jtaResult = new JTextArea();
		jtaResult.setFont(font);

		JPanel jpanelSouth = new JPanel(new BorderLayout());
		jpanelSouth.add(new JScrollPane(jtaResult), BorderLayout.CENTER);

		jframe.add(jpanelSouth, BorderLayout.CENTER);
	}

	/**
	 * @Title: event
	 * @Description: 组件事件
	 */
	private void event() {
		jbtnOpenDir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eventOpenDir();
			}

		});

		jbtnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eventSearch();
			}

		});

		jtKeyWords.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					eventSearch();
				}
			}
		});

		jcbList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model = (String) jcbList.getSelectedItem();
				jtKeyWords.setVisible(model.equals(Model.MODEL_FOUND));
			}
		});
	}

	/**
	 * 使用JFileChooser这个Java自带的文件选择器来打开文件选择界面
	 * @Title: eventOpenDir
	 */

	private void eventOpenDir() {
		JFileChooser jfChooser = new JFileChooser(".");
		jfChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfChooser.setDialogTitle("Please Select Expected Dir");
		String approveButtonText = "select";
		if (jfChooser.showDialog(null, approveButtonText) == JFileChooser.APPROVE_OPTION) {
			String name = jfChooser.getSelectedFile().getPath();
			System.out.println("The Selected Path: " + name);
			jtPath.setText(name);
		}
	}

	/**
	 * 调用了Model中的文件查找函数
	 * @Title: eventSearch
	 */

	private void eventSearch() {
		String path = jtPath.getText();
		String key = jtKeyWords.getText();

		if ("".equals(path)) {
			jtaResult.setText("文件路径不能为空");
			return;
		} else if (model.equals(Model.MODEL_FOUND) && "".equals(key)) {
			jtaResult.setText("关键字不能为空");
			return;
		}

		jtaResult.setText("Beginning:" + java.util.Calendar.getInstance().getTime().toString() + "\n");

		// key point!!!
		Controller c = new Controller(model);
		c.setMsg(path, key);
		jtaResult.append(c.getMsg().toString());
		jtaResult.append("Ending:" + java.util.Calendar.getInstance().getTime() + "\n");
	}
}