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
package org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations;

import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Represents Test tab in JUnit launch configuration
 * 
 * @author Lucia Jelinkova
 *
 */
public class JUnitLaunchConfigurationTab extends LaunchConfigurationTab {

	/**
	 * Constructs Junit "Test" tab.
	 */
	public JUnitLaunchConfigurationTab() {
		super("Test");
	}

	/**
	 * Return the eclipse project associated with the configuration. 
	 * @return Eclipse project
	 */
	public String getProject(){
		return new LabeledText("Project:").getText();
	}
	
	/**
	 * Set the Eclipse project associated with the configuration.
	 *
	 * @param text Eclipse project
	 */
	public void setProject(String text){
		new LabeledText("Project:").setText(text);
	}
	
	/**
	 * Return the test class that should run.
	 *
	 * @return test class
	 */
	public String getTestClass(){
		return new LabeledText("Test class:").getText();
	}
	
	/**
	 * Set the test class name that should run.
	 *
	 * @param text the new test class
	 */
	public void setTestClass(String text){
		new LabeledText("Test class:").setText(text);
	}
	
	/**
	 * Return the test method that should run.
	 *
	 * @return test method
	 */
	public String getTestMethod(){
		return new LabeledText("Test method:").getText();
	}
	
	/**
	 * Set the test method name that should run.
	 *
	 * @param text the new test method
	 */
	public void setTestMethod(String text){
		new LabeledText("Test method:").setText(text);
	}
	
	public String getTestRunner() {
		return new LabeledCombo("Test runner:").getText();
	}
	
	public void setTestRunner(String runner) {
		new LabeledText("Test runner:").setText(runner);
	}
	
	public void toggleKeepJUnitRunningAfterTestRunWhenDebugging(boolean checked) {
		new CheckBox("Keep JUnit running after a test run when debugging").toggle(checked);;
	}
	
	public void toggleRunInUIThread(boolean checked) {
		new CheckBox("Run in UI thread").toggle(checked);
	}
	
	public RadioButton getRunSingleTestButton() {
		return new RadioButton("Run a single test");
	}
	
	public RadioButton getRunAllTestsInSelectedPackage() {
		return new RadioButton("Run all tests in the selected project, package or source folder:");
	}
}
