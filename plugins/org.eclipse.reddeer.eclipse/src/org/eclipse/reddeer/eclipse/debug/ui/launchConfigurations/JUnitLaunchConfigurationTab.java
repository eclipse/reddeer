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
package org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations;

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
}
