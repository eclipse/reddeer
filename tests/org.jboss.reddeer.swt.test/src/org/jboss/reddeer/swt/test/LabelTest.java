package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Label;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class LabelTest {

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
