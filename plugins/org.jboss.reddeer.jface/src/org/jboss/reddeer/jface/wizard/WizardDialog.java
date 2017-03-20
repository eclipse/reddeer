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

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.jface.dialogs.TitleAreaDialog;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.BackButton;
import org.jboss.reddeer.swt.impl.button.CancelButton;
import org.jboss.reddeer.swt.impl.button.FinishButton;
import org.jboss.reddeer.swt.impl.button.NextButton;

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
public class WizardDialog extends TitleAreaDialog{

	protected final Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * Finds WizardDialog with given text. Found shell must be instance of Eclipse WizardDialog
	 * @param text WizardDialog text
	 */
	public WizardDialog(String text) {
		super(text);
	}
	
	/**
	 * Implementations are responsible for making sure given shell is Eclipse WizardDialog.
	 * @param shell instance of Eclipse WizardDialog
	 */
	public WizardDialog(Shell shell){
		super(shell);
	}
	
	/**
	 * Finds WizardDialog matching given matchers. Found shell must be instance of Eclipse WizardDialog
	 * @param matchers to match WizardDialog
	 */
	public WizardDialog(Matcher<?>... matchers) {
		super(matchers);
	}

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
		checkShell();
		log.info("Finish wizard");

		Button button = new FinishButton();
		button.click();

		new WaitWhile(new ShellIsAvailable(getShell()), timeout);
		try{
			new WaitWhile(new JobIsRunning(), timeout);
		} catch (NoClassDefFoundError e) {
			// do nothing, reddeer.workbench plugin is not available
		}
	}
	
	/**
	 * Click the cancel button in wizard dialog.
	 */
	public void cancel() {
		checkShell();
		log.info("Cancel wizard");

		new CancelButton().click();

		new WaitWhile(new ShellIsAvailable(getShell()));
		try{
			new WaitWhile(new JobIsRunning());
		} catch (NoClassDefFoundError e) {
			// do nothing, reddeer.workbench plugin is not available
		}
	}

	/**
	 * Click the next button in wizard dialog.
	 */
	public void next() {
		checkShell();
		log.info("Go to next wizard page");

		Button button = new NextButton();
		button.click();
	}

	/**
	 * Click the back button in wizard dialog.
	 */
	public void back() {
		checkShell();
		log.info("Go to previous wizard page");
		Button button = new BackButton();
		button.click();
	}
	
	/**
	 * Returns true in case Finish button is enabled.
	 *
	 * @return true, if is finish enabled
	 */	
	public boolean isFinishEnabled() {
		checkShell();
		return new FinishButton().isEnabled();
	}
	
	/**
	 * Returns true in case Next button is enabled.
	 *
	 * @return true, if is next enabled
	 */
	public boolean isNextEnabled() {
		checkShell();
		return new NextButton().isEnabled();
	}
	
	/**
	 * Returns true in case Back button is enabled.
	 *
	 * @return true, if is back enabled
	 */
	public boolean isBackEnabled() {
		checkShell();
		return new BackButton().isEnabled();
	}
	
	@Override
	public Class<?> getEclipseClass(){
		return org.eclipse.jface.wizard.WizardDialog.class;
	}
}
