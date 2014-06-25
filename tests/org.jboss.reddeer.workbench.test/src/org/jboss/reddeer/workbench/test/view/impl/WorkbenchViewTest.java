package org.jboss.reddeer.workbench.test.view.impl;

import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.ui.IViewReference;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.lookup.WorkbenchLookup;
import org.jboss.reddeer.workbench.api.View;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class WorkbenchViewTest  {

	private final static String MARKERS_VIEW_TITLE = "Markers";
	private final static String PROJECT_EXPLORER_VIEW_TITLE = "Project Explorer";

	private View markersView;
	private View projectExplorerView;

	@Before
	public void setUp() {
		markersView = new WorkbenchView(MARKERS_VIEW_TITLE);
		try {
			markersView.close();
		} catch (UnsupportedOperationException uoe) {
			// Markes view is not opened, do nothing here
		}

		projectExplorerView = new WorkbenchView(PROJECT_EXPLORER_VIEW_TITLE);
		try {
			projectExplorerView.close();
		} catch (UnsupportedOperationException uoe) {
			// Project Explorer view is not opened, do nothing here
		}
	}

	@Test
	public void testNotActiveView() {

		markersView.open();
		markersView.close();
		
	}

	@Test
	public void testActiveView() {

		projectExplorerView.open();
		projectExplorerView.close();
		
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testCloseNonInstantiatedView() {
		
		markersView.close();
		
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testCloseClosedView() {
		
		markersView.open();
		markersView.close();
		markersView.close();
		
	}

	@Test
	public void testFocusOnInstantiatedView() {

		markersView = new WorkbenchView(MARKERS_VIEW_TITLE);
		assertThatActiveViewIsNot(MARKERS_VIEW_TITLE, PROJECT_EXPLORER_VIEW_TITLE);
		
		projectExplorerView = new WorkbenchView(PROJECT_EXPLORER_VIEW_TITLE);
		assertThatActiveViewIsNot(MARKERS_VIEW_TITLE, PROJECT_EXPLORER_VIEW_TITLE);
		
		markersView.open();
		assertThat(WorkbenchLookup.findActiveView().getPartName(),
				Is.is(MARKERS_VIEW_TITLE));

		projectExplorerView.open();
		assertThat(WorkbenchLookup.findActiveView().getPartName(),
				Is.is(PROJECT_EXPLORER_VIEW_TITLE));

		new WorkbenchView(MARKERS_VIEW_TITLE);
		assertThat(WorkbenchLookup.findActiveView().getPartName(),
				Is.is(MARKERS_VIEW_TITLE));

		new WorkbenchView(PROJECT_EXPLORER_VIEW_TITLE);
		assertThat(WorkbenchLookup.findActiveView().getPartName(),
				Is.is(PROJECT_EXPLORER_VIEW_TITLE));
		
	}
	
	private void assertThatActiveViewIsNot(String... viewTitles) {
		IViewReference activeView = WorkbenchLookup.findActiveView();
		if (activeView == null) {
			return; 
		}
		for (String viewTitle : viewTitles) {
			assertThat(activeView.getPartName(),IsNot.not(viewTitle));
		}
	}
}
