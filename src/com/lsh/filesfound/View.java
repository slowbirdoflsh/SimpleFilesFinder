package com.lsh.filesfound;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
    * @ClassName: View
    * @Description: ���ļ�������дһ������
    * @author lingsh
    * @date Sep 30, 2018
    * 
    * *<br>
    * ���������<br>
    *	����*1 	λ�ã���Ļ����Toolkit.getDefaultToolkit().getScreenSize() <br>
    *			��С��650*350<br>
    *	���*3	λ�ã��������� BorderLayout(North,South)	<br>
    *	�����*2 	λ�ã��������� <br>
    *	��ť*2	λ�ã��������� ���������� ��������<br>
    *	�ı�*1	λ�ã�������� ��������<br>
    *			(�����(��������(�����)��������ť) �����(��ʾ�ı�����))<br>
 */

public class View {

	/** 
	 * ��ǰ��Ļ�߶�
	 */
	public static final int WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	/** 
	 * ��ǰ��Ļ���
	 */
	public static final int WINDOW_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;

	/** 
	 * ����Ӧ�ó�ʼ�ĸ߶�
	 */
	public static final int VIEW_HEIGHT = 300;
	/** 
	 * ����Ӧ�ó�ʼ�Ŀ��
	 */
	public static final int VIEW_WIDTH = 600;

	/** 
	 * �����ַ���С ���㴰�ں��ı���Ķ�Ӧ ���/�ַ���С
	 */
	public static final int CHAR_SIZE = 16;

	/**
	 * ��������ı�����������
	 */
	public static final int MARGIN_SIZE = 140;

	/** 
	 * �����
	 */
	private JFrame jframe;
	private JButton jbtnSearch;
	private JButton jbtnOpenDir;
	private JTextField jtPath;
	private JTextField jtKeyWords;
	private JTextArea jtaResult;
	private Font font;

	/** 
	 * ����һ���µ�ʵ�� View.
	 */
	public View() {
		init();
		panelNorth();
		panelSouth();
		event();
	}

	/**
	 * @Title: init
	 * @Description: ���ڳ�ʼ�� �ɼ� ��С λ�� ���� �رղ���
	*/
	public void init() {
		jframe = new JFrame("Files Found");
		jframe.setVisible(true);
		jframe.setSize(VIEW_WIDTH, VIEW_HEIGHT);
		jframe.setLocation((WINDOW_WIDTH - VIEW_WIDTH) / 2, (WINDOW_HEIGHT - VIEW_HEIGHT) / 2);
		jframe.setLayout(new BorderLayout());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		font = new Font(null, Font.PLAIN, 14);
	}

	/** 
	* @Title: panelNorth
	* @Description: ����� ��������� ������ť
	 */
	private void panelNorth() {
		jbtnSearch = new JButton("Search");
		jbtnOpenDir = new JButton("Open Dir");

		jtPath = new JTextField((VIEW_WIDTH - jbtnSearch.getWidth()) / CHAR_SIZE);
		jtPath.setFont(font);
		jtKeyWords = new JTextField(10);
		jtKeyWords.setFont(font);

		JPanel jpanelInput = new JPanel(new BorderLayout());
		jpanelInput.add(jtPath, BorderLayout.NORTH);
		jpanelInput.add(jtKeyWords, BorderLayout.SOUTH);

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
	 * @Description: �����ʾ���
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
	 * @Description: ����¼�
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
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					eventSearch();
				}
			}
		});
	}

	/**
	 * ʹ��JFileChooser���Java�Դ����ļ�ѡ���������ļ�ѡ�����
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
	 * ������Model�е��ļ����Һ���
	 * @Title: eventSearch
	 */

	private void eventSearch() {
		String path = jtPath.getText();
		String key = jtKeyWords.getText();

		if ("".equals(path)) {
			jtaResult.setText("�ļ�·������Ϊ��");
			return;
		} else if ("".equals(key)) {
			jtaResult.setText("�ؼ��ֲ���Ϊ��");
			return;
		}

		jtaResult.setText("Beginning:" + Calendar.getInstance().getTime().toString() + "\n");
		
		TreeMap<Integer, String> tmis = new Model().filesFound(path, key);
		Iterator<Integer> it = tmis.keySet().iterator();
		while (it.hasNext()) {
			jtaResult.append(tmis.get(it.next()) + " \n");
		}
		jtaResult.append("Ending:" + Calendar.getInstance().getTime() + "\n");
	}
}
