package org.jboss.reddeer.swt.test;

import static org.junit.Assert.*;

import org.jboss.reddeer.swt.api.Label;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;
import org.junit.BeforeClass;
import org.junit.Test;


public class LabelTest extends RedDeerTest {

	@BeforeClass
	public static void openExplorer() {
		new WorkbenchView("RedDeer SWT Controls").open();
	}

	@Test
	public void defaultLabelTest() {
		Label defaultLabel = new DefaultLabel("Name:");
		assertTrue("Widget must be visible:", defaultLabel.isVisible());
	}
}
