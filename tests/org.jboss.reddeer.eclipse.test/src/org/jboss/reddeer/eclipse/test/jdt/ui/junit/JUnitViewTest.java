package org.jboss.reddeer.eclipse.test.jdt.ui.junit;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.eclipse.jdt.ui.junit.JUnitView;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.junit.Test;

/**
 * 
 * @author apodhrad
 *
 */
public class JUnitViewTest extends RedDeerTest {

	@Test
	public void getRunsTest() {
		JUnitView jUnitView = new JUnitView();
		jUnitView.open();
		assertEquals("0/0", jUnitView.getRunStatus());
	}

	@Test
	public void getErrorsTest() {
		JUnitView jUnitView = new JUnitView();
		jUnitView.open();
		assertEquals(0, jUnitView.getNumberOfErrors());
	}

	@Test
	public void getFailuresTest() {
		JUnitView jUnitView = new JUnitView();
		jUnitView.open();
		assertEquals(0, jUnitView.getNumberOfFailures());
	}
	
	@Test
	public void getViewStatusTest() {
		JUnitView jUnitView = new JUnitView();
		jUnitView.open();
		assertEquals("", jUnitView.getViewStatus());
	}
}
