==================
=== Demo Files ===
==================

src/com/xilinxlite
	- Demo.java
	- Sample.java

	
== Demo.java ==

Description:
For testing BeanFactory.java only

Methods:
1. main


== Sample.java ==

Description:
Sample POJO class for testing only

Methods: (none)


=========================
=== Application Files ===
=========================

src/com/xilinxlite
	- MainApplication.java
	
	/controller
		- BeanFactory.java
		- BeanInstantiationError.java
		- MenuMgr.java (interface)
		- MenuMgrImp.java
		
		
== MainApplication.java ==

Description:
Starting point of application. Builds base GUI.

Methods:
1. main
2. start
	- Required for using JavaFX. Called through Application.launch().
3. setupMenuBar
	- Sets up menubar for the main GUI.

	
== BeanFactory.java ==

Description:
Based on SpringFramework, controls which class to pass out based on id provided.
Uses Lazy Singleton to prevent multiple instantiations.

Methods:
1. getInstance
2. getBean
	- Returns class as per provided id.
	- Casting required as return type is Object class.
	- Prints information if id is not implemented, then throws BeanInstantiationError
		for stacktrace.
		

=================================
== BeanInstantiationError.java ==
=================================

Description:
Custom Exception for stacktrace.

Methods: (none)


==================
== MenuMgr.java ==
==================

Description:
Interface for menubar implementation (main GUI only).

Methods:
Based on the menubar requirements.
Please check MainApplication.java for implementation requirements.


=====================
== MenuMgrImp.java ==
=====================

Description:
Implemented class for MenuMgr.java.
Unimplemented methods are to have information printed for tracing.

Methods:
Refer to MenuMgr.java for implementation requirments.


