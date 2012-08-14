package org.jboss.reddeer.workbench.test.view;

import org.jboss.reddeer.workbench.view.View;
import org.jboss.reddeer.workbench.view.WorkbenchView;
import org.junit.Test;

public class ViewTest {

	@Test
	public void testRegisteredView() {
		
		View customView = new WorkbenchView("Label View");
		customView.open();
		
	}
	
	@Test(expected=org.jboss.reddeer.workbench.exception.ViewNotFoundException.class)
	public void testNotRegisteredView() {
		
		View customView = new WorkbenchView("Nonexist View");
		customView.open();
		
	}
	
}
