package com.lsh.project.model;

import java.io.File;
import java.util.TreeMap;

/**
 * 	查找器主体，各种查找工具
    * @ClassName: FilesFoundModel
    * @Description: 查找器主体，各种查找工具
    * @author lingsh
    * @date Oct 1, 2018
    *
 */
public class FilesFoundModel extends AbstractModel {

	/**
	 * 使用TreeMap存储查找结果 可使用比较器
	 */
	private TreeMap<Integer, String> tmis = new TreeMap<>();
	/**
	 * 暂时的查找结果定位
	 */
	private int count = 0;

	/**
	 * 创建一个新的实例 Model.
	 */
	private FilesFoundModel() {
	}

	/**
	 * 获得一个新的实例 FilesFoundModel.
	    * @return Model 实例
	 */
	public static Model getIntance() {
		return new FilesFoundModel();
	}

	/**
	 * 使用迭代的方法查找文件
	 * @Title: filesFound
	 * @Description: 使用迭代的方法查找文件
	 * @param path 查找路径
	 * @param key	关键词
	 */
	public void filesFound(String path, String key) {
		File pathname = new File(path);
		if (!pathname.exists()) {
			// 可能会因为权限或者文件格式(比如Ink)而产生了误判
			String error = pathname.getPath() + " 文件路径不存在";
			System.out.println(error);
			tmis.put(count++, error);
		} else {
			if (pathname.isDirectory() && !isFileLink(pathname)) {
				check(key, pathname);

				String[] list = pathname.list();
				String parentPath = pathname.getPath();
				for (String subFile : list) {
					filesFound(parentPath + "/" + subFile, key);
				}
			} else if (pathname.isFile()) {
				check(key, pathname);
			} else {
				String warning = "See what's this:" + pathname.getPath();
				System.out.println(warning);
			}
		}
	}

	/**
	 * 通过文件或文件夹名字与关键字匹配，符合的添加进结果集
	* @Title: check
	* @param key String
	* @param pathname File
	*/

	private void check(String key, File pathname) {
		if (pathname.getName().indexOf(key) > -1) {
			String result = pathname.getPath();
			System.out.println(result + (pathname.isDirectory() ? "--dir" : ""));
			tmis.put(count++, result);
		}
	}

	@Override
	public void setPoint(String[] parameters) {
		String path = parameters[0];
		String key = parameters[1];
s
		filesFound(path, key);
	}

	@Override
	public StringBuffer getFormatMsg() {
		StringBuffer sb = new StringBuffer();

		// format
		java.util.Iterator<Integer> it = tmis.keySet().iterator();
		while (it.hasNext()) {
			sb.append(tmis.get(it.next()) + "\n");
		}

		return sb;
	}
}