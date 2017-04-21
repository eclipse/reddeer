/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.jdt.internal.ui;

import static org.junit.Assert.assertTrue;

import org.eclipse.ui.PlatformUI;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.eclipse.ui.perspectives.AbstractPerspective;
import org.eclipse.reddeer.eclipse.ui.perspectives.DebugPerspective;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaBrowsingPerspective;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaTypeHierarchyPerspective;
import org.eclipse.reddeer.eclipse.ui.perspectives.ResourcePerspective;
import org.eclipse.reddeer.eclipse.ui.perspectives.TeamSynchronizingPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
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
