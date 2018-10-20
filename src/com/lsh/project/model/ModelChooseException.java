package com.lsh.project.model;

/**
 * 模式选择异常.
    * @ClassName ModelChooseException
    * @author lingsh
    * @date Oct 20, 2018
    *
 */
public class ModelChooseException extends RuntimeException {
        
	private static final long serialVersionUID = 1L;

	public ModelChooseException() {
		super();
		
	}

	public ModelChooseException(String message) {
		super(message);
		
	}
	
}
