package org.jboss.reddeer.eclipse.test.jdt.ui.junit;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.eclipse.jdt.ui.junit.JUnitView;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author apodhrad
 *
 */
@RunWith(RedDeerSuite.class)
public class JUnitViewTest {

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
