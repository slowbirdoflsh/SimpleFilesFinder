package com.lsh.project.model;

/**
 * 模式的顶级接口
 * <p>抽象的定义Model的两个主要方法:设置条件,获得数据
 * <p>当前有两个属性,分别代表两个模式的标识
    * @ClassName Model
    * @author lingsh
    * @date Oct 20, 2018
    *
 */
public interface Model {
	/**
	 * 指定文件查找模式
	 */
	static String MODEL_FOUND = "found";
	/**
	 * 重复文件查找模式
	 */
	static String MODEL_DUPLICATED = "duplicated";
	
	/**
	 * 设置必要条件,比如:路径,关键字etc.
	    * @param parameters 查找的必要条件
	 */
	void setPoint(String[] parameters);
	/**
	 * 获得结果数据,该数据尽量排版好.
	    * @return StringBuffer 排版好的结果数据
	 */
	StringBuffer getFormatMsg();
}
