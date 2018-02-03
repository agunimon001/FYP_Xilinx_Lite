package com.xilinxlite.communication;

public enum XilinxAttribute {
	FAMILY("Device Family"),
	DEVICE("Device"),
	PACKAGE("Package"),
	SPEED("Speed Grade"),
	TOP_SOURCE_TYPE("Top-Level Source Type"),
	SYNTHESIS("Synthesis Tool"),
	SIMULATOR("Simulator"),
	LANGUAGE("Preferred Language"),
	MESSAGE("Enable Message Filtering");
	
	private String name;
	
	XilinxAttribute(String name){
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
