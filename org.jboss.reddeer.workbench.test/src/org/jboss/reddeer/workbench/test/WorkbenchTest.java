package org.jboss.reddeer.workbench.test;

import org.jboss.reddeer.workbench.view.View;
import org.jboss.reddeer.workbench.view.WorkbenchView;
import org.junit.Test;

public class WorkbenchTest {

	@Test
	public void testNotActiveView() {
		
		View markersView = new WorkbenchView("Markers");
		markersView.open();
		
	}
	
	@Test
	public void testActiveView() {
		
		View projectExplorerView = new WorkbenchView("Project Explorer");
		projectExplorerView.open();
		
	}
}
