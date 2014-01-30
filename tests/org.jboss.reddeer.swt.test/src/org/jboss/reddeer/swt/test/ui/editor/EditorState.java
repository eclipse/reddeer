package org.jboss.reddeer.swt.test.ui.editor;

public class EditorState {

	public static boolean executed = false;
	
	public static void execute() {
		executed = true;
	}
	
	public static boolean isExecuted() {
		return executed;
	}
	
}
