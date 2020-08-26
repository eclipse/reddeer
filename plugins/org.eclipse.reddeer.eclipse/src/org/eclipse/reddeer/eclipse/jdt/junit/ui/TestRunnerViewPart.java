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
package org.eclipse.reddeer.eclipse.jdt.junit.ui;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.matcher.WithLabelMatcher;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.label.DefaultLabel;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;	

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
		return new LabeledText(cTabItem, new WithLabelMatcher(new RegexMatcher("Runs:.*"))).getText().trim();
	}

	/**
	 * Returns the number of errors.
	 * 
	 * @return number of errors
	 */
	public int getNumberOfErrors() {
		activate();
		String errorStatus = new LabeledText(cTabItem, new WithLabelMatcher(new RegexMatcher("Errors:.*"))).getText().trim();
		return Integer.valueOf(errorStatus);
	}

	/**
	 * Returns the number of failures.
	 * 
	 * @return number of failures
	 */
	public int getNumberOfFailures() {
		activate();
		String errorStatus = new LabeledText(cTabItem, new WithLabelMatcher(new RegexMatcher("Failures:.*"))).getText().trim();
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
