package org.jboss.reddeer.workbench.test.view.impl;

import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.workbench.view.View;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;
import org.junit.Test;

public class WorkbenchViewTest extends RedDeerTest {

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
