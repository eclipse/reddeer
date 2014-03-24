package org.jboss.reddeer.eclipse.ui.ide;

import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.swt.wait.TimePeriod;

/**
 * Represents Build Server dialog
 * 
 * @author ldimaggi
 *
 */
public class BuildServerDialog extends DefaultShell {

	public static final String TITLE = "Build Server Properties";
	
	/**
	 * Open QuickFix dialog and set focus on it
	 */
	public BuildServerDialog() {
		super(TITLE);
	}
	
	/**
	 * Press Select All button
	 */
	public void selectAll() {
		new PushButton("Select All").click();
	}
	
	/**
	 * Press Deselect All button
	 */
	public void deselectAll() {
		new PushButton("Deselect All").click();
	}
	
        /**
	 * Press Refresh button
        */
        public void refresh() {
                new PushButton("Refresh").click();
        }

	/**
	 * Press Cancel button
	 */
	public void cancel() {
		new PushButton("Cancel").click();
		new WaitWhile(new ShellWithTextIsActive(TITLE));
	}
	
	/**
	 * Press Finish button
	 */
	public void finish() {
		new PushButton("Finish").click();
		new WaitWhile(new ShellWithTextIsActive(TITLE));
	}
	
	/**
	 * Press Validate button
	 */
	public void validateSettings() {
		PushButton validate = new PushButton("Validate");
		validate.click();
		while (!validate.isEnabled()) {
			AbstractWait.sleep(TimePeriod.NORMAL.getSeconds());
		}
	}
	
}
