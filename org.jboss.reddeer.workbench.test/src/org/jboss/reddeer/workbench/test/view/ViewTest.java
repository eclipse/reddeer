package org.jboss.reddeer.workbench.test.view;

import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.workbench.view.View;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;
import org.junit.Test;

public class ViewTest extends RedDeerTest{
	
	@Test
	public void testInitializeRegisteredView() {
		
		new WorkbenchView("Workbench Test");
		
	}
	
	@Test(expected=org.jboss.reddeer.workbench.exception.ViewNotFoundException.class)
	public void testInitializeNonregisteredView() {
		
		new WorkbenchView("Nonexist View");
		
	}
	
	@Test
	public void testOpenView() {
		
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		
	}
	
	@Test
	public void testOpenViewFullPath() {
		
		View customView = new WorkbenchView("Red Deer Test Workbench", "Workbench Test");
		customView.open();
		
	}
	
	@Test
	public void testCloseView() {
		
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		customView.close();
		
	}
	
	@Test
	public void testOpenClosedView() {
		
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		customView.close();
		
		customView.open();
		
	}
	
	@Test
	public void testCloseNonFocusedView() {
		
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		View markersView = new WorkbenchView("Markers");
		markersView.open();
		
		customView.close();
	}
	
	@Test(expected=java.lang.UnsupportedOperationException.class)
	public void testCloseNoninitializedView() {
		
		View customView = new WorkbenchView("Workbench Test");
		customView.close();
		
	}
	
}