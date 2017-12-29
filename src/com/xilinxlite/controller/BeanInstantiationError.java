package com.xilinxlite.controller;

public class BeanInstantiationError extends Exception {

	private String classStr;

	public BeanInstantiationError(String classStr) {
		this.classStr = classStr;
	}

	public String getClassStr() {
		return classStr;
	}

}
