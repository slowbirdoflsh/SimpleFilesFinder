package com.lsh.filesfound;

import java.io.File;
import java.util.TreeMap;

/**
 * 	���������壬���ֲ��ҹ���
    * @ClassName: Model
    * @Description: ���������壬���ֲ��ҹ���
    * @author lingsh
    * @date Oct 1, 2018
    *
 */

public class Model {
	/**
	 * ʹ��TreeMap�洢���ҽ�� ��ʹ�ñȽ���
	 */
	public TreeMap<Integer, String> tmis = new TreeMap<>();
	/**
	 * ��ʱ�Ĳ��ҽ����λ
	 */
	public int count = 0;
	
	/**
     * ����һ���µ�ʵ�� Model.
	 */
	public Model() {
	}
	
	/**
	 * ʹ�õ����ķ��������ļ�
     * @Title: filesFound
     * @Description: ʹ�õ����ķ��������ļ�
     * @param path ����·��
     * @param key	�ؼ���
     * @return TreeMap&lt;Integer,String&gt;
     * 			��������
	 */
	public TreeMap<Integer, String> filesFound(String path, String key) {
		File pathname = new File(path);
		if (!pathname.exists()) {
			// ���ܻ���ΪȨ�޻����ļ���ʽ(����Ink)������������
			String error = pathname.getPath() + " �ļ�·��������";
			System.out.println(error);
			tmis.put(count++, error);
		} else {
			if (pathname.isDirectory()) {
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
		
		return tmis;
	}

	
	    /**
	     * ͨ���ļ����ļ���������ؼ���ƥ�䣬���ϵ���ӽ������
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
}
