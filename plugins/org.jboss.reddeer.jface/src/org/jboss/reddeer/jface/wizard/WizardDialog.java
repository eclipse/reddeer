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
package org.jboss.reddeer.jface.wizard;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.DefaultReferencedComposite;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.button.BackButton;
import org.jboss.reddeer.swt.impl.button.CancelButton;
import org.jboss.reddeer.swt.impl.button.FinishButton;
import org.jboss.reddeer.swt.impl.button.NextButton;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;

/**
 * A dialog where are wizard pages displayed. It can operate Next, Back, Cancel
 * and Finish buttons.
 * 
 * @author Lucia Jelinkova
 * @author apodhrad
 * @author vlado pakan
 * @since 0.6
 * 
 */
public class WizardDialog {

	protected final Logger log = Logger.getLogger(this.getClass());

	/**
	 * Instantiates a new wizard dialog.
	 */
	public WizardDialog() {	}

	/**
	 * Click the finish button in wizard dialog.
	 */
	public void finish() {
		finish(TimePeriod.LONG);
	}

	/**
	 * Click the finish button in wizard dialog.
	 * @param timeout to wait for wizard shell to close.
	 */
	public void finish(TimePeriod timeout) {
		log.info("Finish wizard");

		String shellText = new DefaultShell().getText();
		Button button = new FinishButton();
		button.click();

		new WaitWhile(new ShellWithTextIsActive(shellText), timeout);
		new WaitWhile(new JobIsRunning(), timeout);
	}
	
	/**
	 * Click the cancel button in wizard dialog.
	 */
	public void cancel() {
		log.info("Cancel wizard");

		String shellText = new DefaultShell().getText();
		new WaitWhile(new JobIsRunning());
		new CancelButton().click();

		new WaitWhile(new ShellWithTextIsActive(shellText));
		new WaitWhile(new JobIsRunning());
	}

	/**
	 * Click the next button in wizard dialog.
	 */
	public void next() {
		log.info("Go to next wizard page");

		Button button = new NextButton();
		button.click();
	}

	/**
	 * Click the back button in wizard dialog.
	 */
	public void back() {
		log.info("Go to previous wizard page");
		Button button = new BackButton();
		button.click();
	}

	/**
	 * Returns current dialog title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return new DefaultShell().getText();
	}
	
	/**
	 * Returns current dialog page title.
	 *
	 * @return the page title
	 */
	public String getPageTitle() {
		Shell shell = new DefaultShell();
		// Page Title is 3rd Label within first Composite of wizard dialog Shell and is inactive
		Control labelControl = WidgetHandler.getInstance().getChildren(
				((Composite)WidgetHandler.getInstance().getChildren(shell.getSWTWidget())[0]))[2];
		
		return new DefaultLabel(new DefaultReferencedComposite(labelControl)).getText();
	}
	
	/**
	 * Returns current dialog page description.
	 *
	 * @return the page description
	 */
	public String getPageDescription() {
		Shell shell = new DefaultShell();
		// Page Description is 5th Text within first Composite of wizard dialog Shell and is inactive 
		Control labelControl = WidgetHandler.getInstance().getChildren(
				((Composite)WidgetHandler.getInstance().getChildren(shell.getSWTWidget())[0]))[4];
		
		return new DefaultText(new DefaultReferencedComposite(labelControl)).getText();
	}
	
	/**
	 * Returns true in case Finish button is enabled.
	 *
	 * @return true, if is finish enabled
	 */	
	public boolean isFinishEnabled() {
		return new FinishButton().isEnabled();
	}
	
	/**
	 * Returns true in case Next button is enabled.
	 *
	 * @return true, if is next enabled
	 */
	public boolean isNextEnabled() {
		return new NextButton().isEnabled();
	}
	
	/**
	 * Returns true in case Back button is enabled.
	 *
	 * @return true, if is back enabled
	 */
	public boolean isBackEnabled() {
		return new BackButton().isEnabled();
	}
}
