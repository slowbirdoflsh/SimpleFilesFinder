package com.lsh.project;

import java.io.File;

import com.lsh.project.model.FilesDuplicatedModel;
import com.lsh.project.model.FilesFoundModel;
import com.lsh.project.model.Model;
import com.lsh.project.model.ModelChooseException;
import com.lsh.project.model.NoSuchFilepathExpcetion;

/**
 * 中转器 .
 * 当前用于隔离View和Model,降低耦合
    * @ClassName Controller
    * @author lingsh
    * @date Oct 19, 2018
    *
 */
public class Controller {
	/**
	 * 查找模式
	 */
	private String model;
	/**
	 * 模式对象
	 */
	private Model m;
	
	/**
	 * 创建一个新的实例 Controller.
	 */
	public Controller(String model) {
		this.model = model;
		if(Model.MODEL_FOUND.equals(model)) {
			m = FilesFoundModel.getIntance();
		} else if(Model.MODEL_DUPLICATED.equals(model)) {
			m = FilesDuplicatedModel.getIntance();
		} else {
			String exceptionMsg = "Sorry, please check the model true?\t" + model;
			System.out.println(exceptionMsg);
			throw new ModelChooseException(exceptionMsg);
		}
	}
	
	/**
	 * 统一输入条件接口.
	    * @param path 输入条件_文件路径
	    * @param key 输入条件_关键字 
	 */
	public void setMsg(String path, String key) {
		isValid(path);
		if(model.equals(Model.MODEL_FOUND)) {
			setMessage(path, key);
		} else if(model.equals(Model.MODEL_DUPLICATED)) {
			setMessage(path);
		}
	}
	
	/**
	 * 
	    * @param @param root
	    * @param @return 
	    * @return boolean 
	    * @throws
	 */
	private boolean isValid(String path) {
		File root = new File(path);
		if (!root.exists()) {
			throw new NoSuchFilepathExpcetion("The path is not found!");
		} else if (root.isFile()) {
			throw new NoSuchFilepathExpcetion(root.getPath() + ": this is a file!");
		}
		return true;
	}
	
	/**
	 * 重复文件查找输入.
	    * @param path 输入条件_文件路径
	 */
	private void setMessage(String path) {
		m.setPoint(new String[] {path});
	}

	/**
	 * 指定文件查找输入.
	    * @param path 输入条件_文件路径
	    * @param key 输入条件_关键字
	 */
	private void setMessage(String path, String key) {
		m.setPoint(new String[] {path, key});
	}
	
	/**
	 * 统一数据输出接口.
	    * @return StringBuffer 排版好的结果数据
	 */
	public StringBuffer getMsg() {
		return m.getFormatMsg();
	}
}
