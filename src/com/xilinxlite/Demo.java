package com.xilinxlite;

import com.xilinxlite.controller.BeanInstantiationError;
import com.xilinxlite.controller.BeanFactory;

public class Demo {

	public static void main(String[] args) throws BeanInstantiationError {
		BeanFactory beanFactory = BeanFactory.getInstance();
		
		Sample sample = (Sample) beanFactory.getBean("sample");
		System.out.println(sample.getMessage());
	}
}
