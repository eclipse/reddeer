package org.jboss.reddeer.eclipse.jdt.ui.junit;

import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.swt.api.ToolItem;
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
public class JUnitView extends WorkbenchView {

	/**
	 * Construct the view with JUnit.
	 */
	public JUnitView() {
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
		return new LabeledText("Runs: ").getText().trim();
	}

	/**
	 * Returns the number of errors.
	 * 
	 * @return number of errors
	 */
	public int getNumberOfErrors() {
		activate();
		String errorStatus = new LabeledText("Errors: ").getText().trim();
		return Integer.valueOf(errorStatus);
	}

	/**
	 * Returns the number of failures.
	 * 
	 * @return number of failures
	 */
	public int getNumberOfFailures() {
		activate();
		String errorStatus = new LabeledText("Failures: ").getText().trim();
		return Integer.valueOf(errorStatus);
	}

	/**
	 * Returns the view status.
	 * 
	 * @return view status
	 */
	public String getViewStatus() {
		activate();
		return new DefaultLabel().getText().trim();
	}
	
	/**
	 * Removes all test runs.
	 */
	public void removeAllRuns() {
		activate();
		new DefaultToolItem("Test Run History...").click();
		new DefaultShell("Test Runs");
		new PushButton("Remove All").click();
		new OkButton().click();
		new WaitWhile(new ShellWithTextIsAvailable("Test Runs"));
	}
}
