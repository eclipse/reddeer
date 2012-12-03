package org.jboss.reddeer.eclipse.test.jdt.internal.ui;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.internal.ui.JavaPerspective;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Bot;
import org.junit.Test;

public class JavaPerspectiveTest extends RedDeerTest{

	@Test
	public void open() {
		JavaPerspective javaPerspective = new JavaPerspective();
		javaPerspective.open();
		assertTrue("Active Perspective has to be Java Perspective but is " + Bot.get().activePerspective().getLabel(),
		    Bot.get().activePerspective().getLabel().equals(javaPerspective.getPerspectiveLabel()));
		
	}
}
