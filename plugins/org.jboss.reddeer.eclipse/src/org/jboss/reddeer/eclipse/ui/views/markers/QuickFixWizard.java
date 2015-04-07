package org.jboss.reddeer.eclipse.ui.views.markers;

import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.swt.impl.button.CancelButton;
import org.jboss.reddeer.swt.impl.button.FinishButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

/**
 * Represents QuickFixWizard 
 * @author rawagner
 *
 */
public class QuickFixWizard {
	
	/**
	 * Default constructor
	 */
	public QuickFixWizard(){
		new DefaultShell("Quick Fix");
	}
	
	/**
	 * Click cancel button
	 */
	public void cancel(){
		new CancelButton().click();
		new WaitWhile(new ShellWithTextIsAvailable("Quick Fix"));
	}
	
	/**
	 * Click finish button
	 */
	public void finish(){
		new FinishButton().click();
		new WaitWhile(new ShellWithTextIsAvailable("Quick Fix"));
	}

}
