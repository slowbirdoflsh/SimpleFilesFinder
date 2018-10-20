package com.lsh.project.model;

/**
 * 输入文件路径没有找到
    * @ClassName NoSuchFilepathExpcetion
    * @author lingsh
    * @date Oct 20, 2018
    *
 */
public class NoSuchFilepathExpcetion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoSuchFilepathExpcetion() {
		super();
	}

	public NoSuchFilepathExpcetion(String message) {
		super(message);
	}

}
