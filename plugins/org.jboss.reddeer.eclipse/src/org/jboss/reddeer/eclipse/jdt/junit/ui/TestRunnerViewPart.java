/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.jdt.junit.ui;

import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;	

/**
 * JUnit View
 * 
 * @author apodhrad
 * 
 */
public class TestRunnerViewPart extends WorkbenchView {

	/**
	 * Construct the view with JUnit.
	 */
	public TestRunnerViewPart() {
		super("JUnit");
	}

	/**
	 * Returns run status in the form x/y where x is the number of executed
	 * tests and y is the number of all tests.
	 * 
	 * @return run status
	 */
	public String getRunStatus() {
		activate();
		return new LabeledText(cTabItem, "Runs: ").getText().trim();
	}

	/**
	 * Returns the number of errors.
	 * 
	 * @return number of errors
	 */
	public int getNumberOfErrors() {
		activate();
		String errorStatus = new LabeledText(cTabItem, "Errors: ").getText().trim();
		return Integer.valueOf(errorStatus);
	}

	/**
	 * Returns the number of failures.
	 * 
	 * @return number of failures
	 */
	public int getNumberOfFailures() {
		activate();
		String errorStatus = new LabeledText(cTabItem, "Failures: ").getText().trim();
		return Integer.valueOf(errorStatus);
	}

	/**
	 * Returns the view status.
	 * 
	 * @return view status
	 */
	public String getViewStatus() {
		activate();
		return new DefaultLabel(cTabItem).getText().trim();
	}
	
	/**
	 * Removes all test runs.
	 */
	public void removeAllRuns() {
		activate();
		new DefaultToolItem(cTabItem.getFolder(), "Test Run History...").click();
		new DefaultShell("Test Runs");
		new PushButton("Remove All").click();
		new OkButton().click();
		new WaitWhile(new ShellIsAvailable("Test Runs"));
	}
}
