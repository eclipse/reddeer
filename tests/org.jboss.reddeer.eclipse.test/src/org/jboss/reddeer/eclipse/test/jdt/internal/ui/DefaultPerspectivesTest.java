package org.jboss.reddeer.eclipse.test.jdt.internal.ui;

import static org.junit.Assert.assertTrue;

import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.eclipse.ui.perspectives.AbstractPerspective;
import org.jboss.reddeer.eclipse.ui.perspectives.DebugPerspective;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaBrowsingPerspective;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaTypeHierarchyPerspective;
import org.jboss.reddeer.eclipse.ui.perspectives.ResourcePerspective;
import org.jboss.reddeer.eclipse.ui.perspectives.TeamSynchronizingPerspective;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class DefaultPerspectivesTest {
	
	
	public void openPerspective(AbstractPerspective perspective) {
		perspective.open();
		String activePerspectiveLabel = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective().getLabel();
			}
		});
		assertTrue("Active Perspective has to be Java Perspective but is " + activePerspectiveLabel,
		    activePerspectiveLabel.equals(perspective.getPerspectiveLabel()));
	}
	
	@Test
	public void testPerspectives(){
		openPerspective(new DebugPerspective());
		openPerspective(new JavaPerspective());
		openPerspective(new JavaBrowsingPerspective());
		openPerspective(new JavaTypeHierarchyPerspective());
		openPerspective(new ResourcePerspective());
		openPerspective(new TeamSynchronizingPerspective());
	}
}
