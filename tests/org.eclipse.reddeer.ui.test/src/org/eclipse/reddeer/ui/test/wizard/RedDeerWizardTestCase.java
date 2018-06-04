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
package org.eclipse.reddeer.ui.test.wizard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.osgi.internal.framework.EquinoxBundle;
import org.eclipse.osgi.storage.BundleInfo.Generation;
import org.eclipse.pde.core.target.ITargetDefinition;
import org.eclipse.pde.core.target.ITargetLocation;
import org.eclipse.pde.core.target.ITargetPlatformService;
import org.eclipse.pde.core.target.LoadTargetDefinitionJob;
import org.eclipse.pde.internal.core.target.TargetPlatformService;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.FinishButton;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.ui.test.wizard.impl.RedDeerTestPluginWizard;
import org.eclipse.reddeer.ui.test.wizard.impl.RedDeerTestPluginWizardPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;

@SuppressWarnings({ "restriction"})
@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public abstract class RedDeerWizardTestCase {

	protected static final String PLUGIN_NAME = "reddeer.test.plugin";
	protected static final String PLUGIN_ID = "test.plugin";
	protected static final String PLUGIN_PROVIDER = "Eclipse.org - RedDeer";
	protected static final String APPLICATION = "org.eclipse.ui.ide.workbench";
	protected static final String PRODUCT = "org.eclipse.platform.ide";
	protected static final String VERSION = "8.2.9.qualifier";
	protected static final String SOURCE = "src";
	protected static final String EXAMPLE_TEST_CLASS_NAME = "RedDeerTest";
	protected static final String EXAMPLE_TEST_CLASS_JAVA_NAME = "RedDeerTest.java";
	protected static final String EXAMPLE_PACKAGE_NAME = "org.reddeer.test";
	protected static final String [] EXAMPLE_TEST_CLASS_PATH = {SOURCE, EXAMPLE_PACKAGE_NAME, EXAMPLE_TEST_CLASS_JAVA_NAME};
	
	private static final Logger LOG = Logger.getLogger(RedDeerWizardTestCase.class.getName());
	protected static String projectName;
	
	public abstract String getWizardText();
	
	@BeforeClass
	public static void setUpTargetPlatform() {
		try {
			setTargetPlatform();
		} catch (Exception e) {
			fail("Failed to load target platform:" + e.getMessage());
		}
	}
	
	@AfterClass
	public static void deleteProject() {
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		DeleteUtils.forceProjectDeletion(pe.getProject(projectName),true);
	}
	
	public static void createRedDeerPluginProject() {
		NewMenuWizard wizard = new RedDeerTestPluginWizard();
		wizard.open();
		fillInWizard(wizard);
		wizard.finish();
	}
	
	protected void closeOpenedWizard(NewMenuWizard wizard) {
		try {
			new DefaultShell(getWizardText());
			wizard.cancel();
		} catch (CoreLayerException ex){
			LOG.info("Shell " + getWizardText() + " is not open");
		}
	}
	
	protected static void fillInWizard(NewMenuWizard wizard) {
		RedDeerTestPluginWizardPage page = new RedDeerTestPluginWizardPage(wizard);
		page.setPluginName(PLUGIN_NAME);
		page.setPluginId(PLUGIN_ID);
		page.setVersion(VERSION);
		page.setProvider(PLUGIN_PROVIDER);
		
		page.setApplication(true);
		assertTrue("Application combo was not enabled", new DefaultCombo(1).isEnabled());
		page.selectApplication(APPLICATION);
		
		page.toggleExampleTest(true);
		assertTrue("'Example test' checkbox was not checked", new CheckBox().isChecked());
		
		page.setProduct(true);
		assertFalse("Application combo was not disabled", new DefaultCombo(1).isEnabled());
		assertTrue("Product combo was not enabled", new DefaultCombo().isEnabled());
		page.selectProduct(PRODUCT);
		
		assertTrue("Finish button is not enabled", new FinishButton().isEnabled());
	}
	
	protected DefaultProject getProject(String name) {
		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();
		DefaultProject project = null;
		try {
			project = explorer.getProject(name);
		} catch (RedDeerException ex) {
			fail("Cannot retrieve created plug-in project");
		}
		return project;
	}
	
	/**
	 * See: https://bugs.eclipse.org/bugs/show_bug.cgi?id=343156
	 * 
	 * Sets a target platform in the test platform to get workspace builds OK
	 * with PDE. 
	 * 
	 * This code comes from https://bugs.eclipse.org/bugs/show_bug.cgi?id=343156#c11
	 * 
	 * @throws Exception
	 */
	public static void setTargetPlatform() throws Exception {
		ITargetPlatformService tpService = TargetPlatformService.getDefault();
		ITargetDefinition targetDef = tpService.newTarget();
		targetDef.setName("Tycho platform");
		Bundle[] bundles = Platform.getBundle("org.eclipse.core.runtime").getBundleContext().getBundles();
		List<ITargetLocation> bundleContainers = new ArrayList<ITargetLocation>();
		Set<File> dirs = new HashSet<File>();
		for (Bundle bundle : bundles) {
			EquinoxBundle bundleImpl = (EquinoxBundle) bundle;
			Generation generation = (Generation) bundleImpl.getModule().getCurrentRevision().getRevisionInfo();
			File file = generation.getBundleFile().getBaseFile();
			File folder = file.getParentFile();
			if (!dirs.contains(folder)) {
				dirs.add(folder);
				bundleContainers.add(tpService.newDirectoryLocation(folder.getAbsolutePath()));
			}
		}
		targetDef.setTargetLocations(bundleContainers.toArray(new ITargetLocation[bundleContainers.size()]));
		targetDef.setArch(Platform.getOSArch());
		targetDef.setOS(Platform.getOS());
		targetDef.setWS(Platform.getWS());
		targetDef.setNL(Platform.getNL());
		// targetDef.setJREContainer()
		tpService.saveTargetDefinition(targetDef);

		Job job = new LoadTargetDefinitionJob(targetDef);
		job.schedule();
		job.join();
	}
}
