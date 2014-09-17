package org.jboss.reddeer.eclipse.ui.ide;

import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * Represents Quick Fix dialog
 * 
 * @author ldimaggi
 *
 */
public class RepoConnectionDialog extends DefaultShell {

	public static final String TITLE = "Properties for Task Repository";
	
	/**
	 * Open QuickFix dialog and set focus on it
	 */
	public RepoConnectionDialog() {
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
		PushButton validate = new PushButton("Validate Settings");
		validate.click();
		while (!validate.isEnabled()) {
			AbstractWait.sleep(TimePeriod.NORMAL);
		}
	}
	
}
