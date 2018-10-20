package com.lsh.project.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 抽象Model类.
 * 实现一些基本方法.
    * @ClassName AbstractModel
    * @author lingsh
    * @date Oct 20, 2018
    *
 */
public abstract class AbstractModel implements Model {
	/**
	 * 获得该路径下所有文件(不包括链接--Linux系统).
	 * 此为启动函数
	    * @param path 输入路径
	    * @return {@code ArrayList<String>} 返回该路径下所有文件字符串集合
	    * @throws FileNotFoundException
	 */
	public ArrayList<String> getAllFiles(String path) throws FileNotFoundException {
		File root = new File(path);
		if (!root.exists()) {
			throw new FileNotFoundException();
		}
		ArrayList<String> allfiles = new ArrayList<>();
		getAllFiles(allfiles, root);
		return allfiles;
	}

	/**
	 * 遍历所有文件.
	    * @param allfiles 存储文件名
	    * @param root 遍历文件
	 */
	protected void getAllFiles(ArrayList<String> allfiles, File root) {
		boolean isLink = isFileLink(root);
		if (root.isDirectory() && !isLink) {
			File[] files = root.listFiles();
			for (File subfile : files) {
				getAllFiles(allfiles, subfile);
			}
		} else if (root.isFile()) {
			String path = root.getPath();
			allfiles.add(isLink ? path + ":link" : path);
		}
	}

	/**
	 * 判断该文件是否是链接.
	    * @param root 被判断文件
	    * @return {@code true 表示是链接}
	 */
	protected boolean isFileLink(File root) {
		String abs = root.getAbsolutePath();
		String con = null;
		try {
			con = root.getCanonicalPath();
		} catch (IOException e) {

			e.printStackTrace();
		}
		if (con == null) {
			System.out.println("con is null");
		}
		return !con.equals(abs);
	}
}
