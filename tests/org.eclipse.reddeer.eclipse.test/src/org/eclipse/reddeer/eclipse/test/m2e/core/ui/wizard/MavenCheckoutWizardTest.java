/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.m2e.core.ui.wizard;


import static org.junit.Assert.*;

import org.eclipse.reddeer.eclipse.m2e.scm.wizards.MavenCheckoutLocationPage;
import org.eclipse.reddeer.eclipse.m2e.scm.wizards.MavenCheckoutWizard;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class MavenCheckoutWizardTest {
	
	@Test
	public void openMavenSCMWizard(){
		MavenCheckoutWizard mc = new MavenCheckoutWizard();
		mc.open();
		MavenCheckoutLocationPage ml = new MavenCheckoutLocationPage(mc);
		assertTrue(ml.isCheckoutAllProjects());
		assertTrue(ml.isCheckoutHeadRevision());
		mc.cancel();
		
	}

}
