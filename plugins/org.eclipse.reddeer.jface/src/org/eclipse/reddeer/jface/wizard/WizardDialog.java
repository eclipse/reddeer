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
package org.eclipse.reddeer.jface.wizard;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.jface.condition.WindowIsAvailable;
import org.eclipse.reddeer.jface.dialogs.TitleAreaDialog;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.impl.button.BackButton;
import org.eclipse.reddeer.swt.impl.button.CancelButton;
import org.eclipse.reddeer.swt.impl.button.FinishButton;
import org.eclipse.reddeer.swt.impl.button.NextButton;

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
	
	public WizardDialog() {
		super();
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

		Button button = new FinishButton(this);
		button.click();

		new WaitWhile(new WindowIsAvailable(this), timeout);
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

		new CancelButton(this).click();

		new WaitWhile(new WindowIsAvailable(this));
		try{
			new WaitWhile(new JobIsRunning());
		} catch (NoClassDefFoundError e) {
			// do nothing, reddeer.workbench plugin is not available
		}
	}

	/**
	 * Click the next button in wizard dialog.
	 */
	public WizardDialog next() {
		checkShell();
		log.info("Go to next wizard page");

		Button button = new NextButton(this);
		button.click();
		return this;
	}

	/**
	 * Click the back button in wizard dialog.
	 */
	public WizardDialog back() {
		checkShell();
		log.info("Go to previous wizard page");
		Button button = new BackButton(this);
		button.click();
		return this;
	}
	
	/**
	 * Returns true in case Finish button is enabled.
	 *
	 * @return true, if is finish enabled
	 */	
	public boolean isFinishEnabled() {
		checkShell();
		return new FinishButton(this).isEnabled();
	}
	
	/**
	 * Returns true in case Next button is enabled.
	 *
	 * @return true, if is next enabled
	 */
	public boolean isNextEnabled() {
		checkShell();
		return new NextButton(this).isEnabled();
	}
	
	/**
	 * Returns true in case Back button is enabled.
	 *
	 * @return true, if is back enabled
	 */
	public boolean isBackEnabled() {
		checkShell();
		return new BackButton(this).isEnabled();
	}
	
	@Override
	public Class<? extends org.eclipse.jface.window.Window> getEclipseClass(){
		return org.eclipse.jface.wizard.WizardDialog.class;
	}
}
