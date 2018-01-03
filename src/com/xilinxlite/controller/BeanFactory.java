package com.xilinxlite.controller;

import com.xilinxlite.Sample;

/**
 * Controls all beans used in project. Lazy Singleton used.
 * 
 * @author Ong Hock Leng
 *
 */
public class BeanFactory {

	private static BeanFactory instance = null;

	private BeanFactory() {
		// Exists to protect instantiation
	}

	public static BeanFactory getInstance() {
		if (instance == null) {
			instance = new BeanFactory();
		}
		return instance;
	}

	/**
	 * Gets bean for project. Remember to cast them into appropriate class.
	 * 
	 * @param classStr
	 * @return Object
	 * @throws BeanInstantiationError
	 */
	public Object getBean(String classStr) throws BeanInstantiationError {
		switch (classStr) {
		// sample
		case "sample":
			return new Sample();

		// bean IDs
		case "menuMgr":
			return new MenuMgrImp();

		// if bean id is not found, throw Exception. For easier identification of
		// problem location.
		default:
			System.err.println("class id \"" + classStr + "\" is not found.");
			throw new BeanInstantiationError(classStr);
		}
	}
}
