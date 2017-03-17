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
package org.eclipse.reddeer.eclipse.test.epp.logging.aeri.ide.dialogs;

import static org.junit.Assert.*;

import java.util.List;
import org.eclipse.reddeer.eclipse.epp.logging.aeri.ide.dialogs.ConfigureServerDialog;
import org.eclipse.reddeer.eclipse.epp.logging.aeri.ide.dialogs.PreferencePage;
import org.eclipse.reddeer.eclipse.epp.logging.aeri.ide.dialogs.ReportingProject;
import org.eclipse.reddeer.eclipse.epp.logging.aeri.ide.dialogs.SetupWizard;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.workbench.handler.WorkbenchShellHandler;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class AeriTest {

	@After
	public void closeShells() {
		WorkbenchShellHandler.getInstance().closeAllNonWorbenchShells();
	}

	@Test
	public void testAeriServers() {
		WorkbenchPreferenceDialog wp = new WorkbenchPreferenceDialog();
		wp.open();
		PreferencePage aeriPage = new PreferencePage();
		wp.select(aeriPage);
		SetupWizard sWizard = aeriPage.openConfigureProjects();
		List<ReportingProject> reportingProjects = sWizard.getProjects();
		assertNotNull(reportingProjects);
		assertTrue(reportingProjects.size() >= 2);

		ReportingProject rp = sWizard.getProject("Eclipse RedDeer Test Connector");
		assertEquals("Eclipse RedDeer Test Connector", rp.getProjectName());
		assertEquals("Eclipse RedDeer connector description", rp.getProjectDescription());
		rp.toggleEnable(true);
		assertTrue(rp.isEnabled());
		rp.toggleEnable(false);
		assertFalse(rp.isEnabled());

		ConfigureServerDialog csDialog = rp.configure();
		csDialog.setName("name");
		assertEquals("name", csDialog.getName());
		csDialog.setEmail("name@name.com");
		assertEquals("name@name.com", csDialog.getEmail());
		csDialog.anonymizeErrorLogMessages(true);
		assertTrue(csDialog.isAnonymizeErrorLogMessages());
		csDialog.anonymizePackageClassMethodNames(true);
		assertTrue(csDialog.isAnonymizePackageClassMethodNames());
		csDialog.enable();

		ReportingProject rp1 = sWizard.getProject("Eclipse RedDeer Test Connector1");
		assertEquals("Eclipse RedDeer Test Connector1", rp1.getProjectName());
		assertEquals("Eclipse RedDeer connector description1", rp1.getProjectDescription());
		assertNotNull(rp.getProjectImage());
		rp1.toggleEnable(true);
		assertTrue(rp1.isEnabled());
		rp1.toggleEnable(false);
		assertFalse(rp1.isEnabled());
		ConfigureServerDialog csDialog1 = rp1.configure();
		csDialog1.disable();

		sWizard.getShell().close();
	}

	@Test
	public void testSetupWizard() {
		WorkbenchPreferenceDialog wp = new WorkbenchPreferenceDialog();
		wp.open();
		PreferencePage aeriPage = new PreferencePage();
		wp.select(aeriPage);
		SetupWizard sWizard = aeriPage.openConfigureProjects();
		ReportingProject rp = sWizard.getProject("Eclipse RedDeer Test Connector");
		rp.toggleEnable(true); // enable external reporting server
		sWizard.enable();

		sWizard = aeriPage.openConfigureProjects();
		rp = sWizard.getProject("Eclipse RedDeer Test Connector");
		rp.toggleEnable(false);
		sWizard.enable();

		sWizard = aeriPage.openConfigureProjects();
		rp = sWizard.getProject("Eclipse RedDeer Test Connector");
		sWizard.disable();

		sWizard = aeriPage.openConfigureProjects();
		rp = sWizard.getProject("Eclipse RedDeer Test Connector");
		rp.toggleEnable(true);
		sWizard.disable();
	}

	@Test
	public void testAeriPrefPage() {
		WorkbenchPreferenceDialog wp = new WorkbenchPreferenceDialog();
		wp.open();
		PreferencePage aeriPage = new PreferencePage();
		wp.select(aeriPage);

		aeriPage.anonymizeErrorLogMessages(true);
		assertTrue(aeriPage.isAnonymizeErrorLogMessages());
		aeriPage.anonymizeErrorLogMessages(false);
		assertFalse(aeriPage.isAnonymizeErrorLogMessages());

		aeriPage.anonymizePackageClassMethodNames(true);
		assertTrue(aeriPage.isAnonymizePackageClassMethodNames());
		aeriPage.anonymizePackageClassMethodNames(false);
		assertFalse(aeriPage.isAnonymizePackageClassMethodNames());

		aeriPage.disableExtendedClasspathAnalysis(true);
		assertTrue(aeriPage.isDisableExtendedClasspathAnalysis());
		aeriPage.disableExtendedClasspathAnalysis(false);
		assertFalse(aeriPage.isDisableExtendedClasspathAnalysis());

		aeriPage.enableDebugMode(true);
		assertTrue(aeriPage.isEnableDebugMode());
		aeriPage.enableDebugMode(false);
		assertFalse(aeriPage.isEnableDebugMode());

		aeriPage.setName("name");
		assertEquals("name", aeriPage.getName());

		aeriPage.setEmail("name@name.com");
		assertEquals("name@name.com", aeriPage.getEmail());

		List<String> sendModes = aeriPage.getAvailableSendModes();
		assertNotNull(sendModes);
		assertTrue(sendModes.size() > 0);

		aeriPage.setSendMode(sendModes.get(0));
		assertEquals(sendModes.get(0), aeriPage.getSendMode());
		wp.cancel();
	}

}
