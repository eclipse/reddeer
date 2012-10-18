package org.jboss.tools.reddeer.swt.test.model;

public class TestModel {

	private static boolean clicked = false;
	
	public static void setClicked() {
		clicked = true;
	}
	
	public static boolean getClickedAndReset() {		
		boolean ret = clicked;
		clicked = false;
		return ret;
	}	
}
