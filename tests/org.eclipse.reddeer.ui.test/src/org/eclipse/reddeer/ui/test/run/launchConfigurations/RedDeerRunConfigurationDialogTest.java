/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.ui.test.run.launchConfigurations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.JUnitLaunchConfigurationTab;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.RunConfigurationsDialog;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.ui.test.wizard.RedDeerWizardTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Covers testing of RedDeer UI in Run configuration dialog
 * @author odockal
 *
 */
public class RedDeerRunConfigurationDialogTest extends RedDeerWizardTestCase {

	private RunConfigurationsDialog dialog;
	private RedDeerLaunchConfiguration config;
	private static final String DIALOG_NAME = "Run Configurations";
	private static Map<String, String> rdParametersList;
	
	private static final Logger log = Logger.getLogger(RedDeerRunConfigurationDialogTest.class);
	
	static {
		rdParametersList = new HashMap<String, String>();
		rdParametersList.put("rd.pauseFailedTest", "false");
		rdParametersList.put("rd.logMessageFilter", "ALL");
		rdParametersList.put("rd.logLevel", "ALL");
		rdParametersList.put("rd.closeWelcomeScreen", "true");
		rdParametersList.put("rd.closeShells", "true");
		rdParametersList.put("rd.disableMavenIndex", "true");
		rdParametersList.put("rd.logCollectorEnabled", "true");
		rdParametersList.put("rd.maximizeWorkbench", "true");
		rdParametersList.put("rd.defaultKey", "org.eclipse.reddeer.widget.key");
		rdParametersList.put("rd.config", "");
		rdParametersList.put("rd.captureScreenshot", "true");
		rdParametersList.put("rd.relativeScreenshotDirectory", "");
		rdParametersList.put("rd.openAssociatedPerspective", "never");
		rdParametersList.put("rd.timePeriodFactor", "1.0");
	}
	
	@Override
	public String getWizardText() {
		return "New RedDeer Test Plugin";
	}
	
	@BeforeClass
	public static void setup() {
		projectName = PLUGIN_ID;
		createRedDeerPluginProject();
	}
	
	@Before
	public void initizalizeDialog() {
		config = new RedDeerLaunchConfiguration();
		dialog = new RunConfigurationsDialog();
		dialog.open();
	}
	
	@After
	public void closeRunConfigurationDialog() {
		closeRunConfigDialog();
	}
	
	@Test
	public void testRedDeerRunConfigurationItem() {
		assertTrue(runConfigurationExists(() -> dialog.select(config)));
	}
	
	@Test
	public void testRedDeerRunConfigurationForClass() {
		addRedDeerTestClassRunConfiguration();
	}
	
	@Test
	public void testRedDeerLaunchConfigurationTab() {
		addRedDeerTestClassRunConfiguration();
		
		RedDeerLaunchConfigurationTab tab = new RedDeerLaunchConfigurationTab();
		tab.activate();
		
		assertTrue("Number of RD params does not match", tab.getTable().getItems().size() == rdParametersList.size());
		checkRedDeerConfigurationPropertiesDefaults(tab.getTable());
	}
	
	@Test
	public void testJUnitLaunchConfigurationTab() {
		addRedDeerTestClassRunConfiguration();
		JUnitLaunchConfigurationTab tab = new JUnitLaunchConfigurationTab();
		tab.activate();
		
		assertThat(tab.getProject(), is(PLUGIN_ID));
		assertThat(tab.getTestClass(), is(EXAMPLE_PACKAGE_NAME + "." + EXAMPLE_TEST_CLASS_NAME));
		assertThat(tab.getTestMethod(), is(""));
		assertTrue(tab.getRunSingleTestButton().isSelected());
		assertThat(tab.getTestRunner(), is("JUnit 4")); // covers #1375
	}

	// add new reddeer test class run config if such does not exist
	private void addRedDeerTestClassRunConfiguration() {
		if (!dialogIsOpen()) {
			getProject(PLUGIN_ID).getProjectItem(EXAMPLE_TEST_CLASS_PATH).select();
			dialog.open();
		}
		if (!runConfigurationExists(() -> dialog.select(config, EXAMPLE_TEST_CLASS_NAME))) {
			dialog.create(config, EXAMPLE_TEST_CLASS_NAME);
			config.apply();
		}
		assertTrue(runConfigurationExists(() -> dialog.select(config, EXAMPLE_TEST_CLASS_NAME)));
	}
	
	/**
	 * Tests existence of run configuration item in run configuration dialog tree
	 * @param runnable covers RunConfigurationDialog.select method
	 * @return true if given run configuration in tree of run configs exists
	 */
	private boolean runConfigurationExists(Runnable runnable) {
		try {
			runnable.run();
		} catch (CoreLayerException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void closeRunConfigDialog() {
		if (dialogIsOpen()) {
			dialog.close(true);
		}
	}
	
	public boolean dialogIsOpen() {
		try {
			new DefaultShell(dialog.getTitle());
			return true;
		} catch (CoreLayerException exc) {
			log.info("Shell " + DIALOG_NAME + " is not open");
			return false;
		}
	}
	
	private void checkRedDeerConfigurationPropertiesDefaults(DefaultTable table) {
		for (TableItem item : table.getItems()) {
			String key = item.getText(0);
			assertTrue("RedDeer parameter: " + key + " was not expected", rdParametersList.containsKey(key));
			String value = item.getText(1);
			assertThat("RedDeer parameter: " + key + " has wrong value",
					rdParametersList.get(key), is(value));
		}
	}
	
}
