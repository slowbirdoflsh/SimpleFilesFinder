package com.lsh.project.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * 查找文件夹下重复的文件(文件名,大小,修改时间相同)
    * @ClassName FilesDuplicatedModel
    * @author lingsh
    * @date Oct 18, 2018
    * 
    * <p>1, 收集该文件夹下所有文件路径	{@code ArrayList<File> allfiles}
    * <p>2, 双重嵌套for循环遍历重复文件 存储到 {@code ArrayList<String> refiles}
    * 	<a> 重复文件名另外存储 {@code ArrayList<String> refilesname}
    *
 */

public class FilesDuplicatedModel extends AbstractModel {
	/**
	 * 路径下所有文件对象
	 */
	private ArrayList<File> allfiles;
	/**
	 * 重复文件集合{@code <重复文件路径, 文件名>}
	 */
	private TreeMap<String, String> refiles;
	/**
	 * 重复文件名集合
	 */
	private HashSet<String> refilesname;

	/**
	 * 创建一个新的实例 FilesDuplicatedModel.
	 */
	private FilesDuplicatedModel() {
	}

	/**
	 * 获得一个新的实例 FilesFoundModel.
	    * @return Model 实例
	 */
	public static Model getIntance() {
		return new FilesDuplicatedModel();
	}

	/**
	 * 初始化所有容器, 并予其赋值.
	    * @param path 文件路径
	 */
	private void init(String path) {
		File root = new File(path);

		allfiles = new ArrayList<>();
		refiles = new TreeMap<>();
		refilesname = new HashSet<>();

		getAllFiles(root);
		getRepeatedFiles(allfiles);
	}

	/**
	 * 获得所有重复文件.
	    * @param allfiles 该路径下所有文件
	 */
	private void getRepeatedFiles(ArrayList<File> allfiles) {
		LinkedList<File> linkedfiles = new LinkedList<>(allfiles);
		for (int i = 0; i < linkedfiles.size(); i++) {
			for (int j = i + 1; j < linkedfiles.size(); j++) {
				File one = linkedfiles.get(i);
				File other = linkedfiles.get(j);
				if (isRepeated(one, other)) {
					refiles.put(one.getPath(), one.getName());
					refiles.put(other.getPath(), other.getName());
					refilesname.add(one.getName());
					linkedfiles.remove(j);
				}
			}
		}
	}

	/**
	 * 判断两文件是否相等.
	 * 文件名/文件大小/文件修改时间
	    * @param one 文件1
	    * @param other 文件2
	    * @return {@code true 表示两文件相等}
	 */
	private boolean isRepeated(File one, File other) {
		if (one.getName().equals(other.getName()) && one.length() == other.length()
				&& one.lastModified() == other.lastModified()) {
			return true;
		}
		return false;
	}

	/**
	 * 迭代获得所有文件对象.
	    * @param root 输入文件对象
	 */
	private void getAllFiles(File root) {
		if (root.isDirectory() && !isFileLink(root)) {
			File[] subfiles = root.listFiles();
			for (File file : subfiles) {
				getAllFiles(file);
			}
		} else if (root.isFile()) {
			allfiles.add(root);
		}
	}

	@Override
	public void setPoint(String[] parameters) {
		String path = parameters[0];
		init(path);
	}

	@Override
	public StringBuffer getFormatMsg() {
		StringBuffer sb = new StringBuffer();

		java.util.Set<java.util.Map.Entry<String, String>> entry = refiles.entrySet();
		for (String name : refilesname) {
			sb.append(name + ":\n");
			for (java.util.Map.Entry<String, String> e : entry) {
				if (e.getValue().equals(name)) {
					sb.append("\t" + e.getKey() + "\n");
				}
			}
			sb.append("\n");
		}

		return sb;
	}
}