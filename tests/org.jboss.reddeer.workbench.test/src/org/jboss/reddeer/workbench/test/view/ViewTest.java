package org.jboss.reddeer.workbench.test.view;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.workbench.api.View;
import org.jboss.reddeer.workbench.exception.WorkbenchLayerException;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ViewTest {

	@Test
	public void testInitializeRegisteredView() {
		new WorkbenchView("Workbench Test");
	}

	@Test(expected=WorkbenchPartNotFound.class)
	public void testInitializeNonregisteredView() {
		new WorkbenchView("Nonexist View");
	}

	@Test
	public void testOpenAndCloseView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		customView.close();
	}

	@Test
	public void testOpenAndCloseViewFullPath() {
		View customView = new WorkbenchView("Red Deer Test Workbench", "Workbench Test");
		customView.open();
		customView.close();
	}

	@Test
	public void testOpenClosedView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		customView.close();

		customView.open();
		customView.close();
	}

	@Test
	public void testCloseNonFocusedView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		View markersView = new WorkbenchView("Markers");
		markersView.open();

		customView.close();
		markersView.close();
	}

	@Test(expected=WorkbenchLayerException.class)
	public void testCloseNoninitializedView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.close();
	}

	@Test(expected=WorkbenchLayerException.class)
	public void testActivateNoninitializedView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.activate();
	}

	@Test
	public void testActivateNonFocusedView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		
		View markersView = new WorkbenchView("Markers");
		markersView.open();

		customView.activate();

		customView.close();
		markersView.close();
	}

	@Test
	public void testMaximizeMinimalizedView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		customView.minimize();
		customView.maximize();
		customView.restore();
		customView.close();
	}
}