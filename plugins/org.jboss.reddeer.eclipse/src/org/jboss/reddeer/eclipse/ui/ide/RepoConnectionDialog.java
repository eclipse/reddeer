package org.jboss.reddeer.eclipse.ui.ide;

import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.condition.WidgetIsEnabled;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

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
	 * Press Validate button - Check for the Validate button before and after it is clicked
	 * as validation can be slow
	 */
	public void validateSettings() {
		new WaitUntil(new WidgetIsEnabled(new PushButton("Validate Settings")));
		new PushButton("Validate Settings").click();
		new WaitUntil(new WidgetIsEnabled(new PushButton("Validate Settings")));
	}	
	
}
