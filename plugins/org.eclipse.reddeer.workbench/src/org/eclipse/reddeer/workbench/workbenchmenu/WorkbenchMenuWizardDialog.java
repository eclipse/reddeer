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
package org.eclipse.reddeer.workbench.workbenchmenu;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.swt.api.Shell;

/**
 * Represents all WizardDialog dialogs openable from workbench shell menu
 * @author rawagner
 *
 */
public abstract class WorkbenchMenuWizardDialog extends WizardDialog{
	
	protected Matcher<String> matcher;
	protected String[] path;
	
	/**
	 * 
	 * @param text of WizardDialog
	 * @param path workbench shell menu path to open dialog
	 */
	public WorkbenchMenuWizardDialog(String text, String... path){
		this(new WithTextMatcher(text),path);
	}
	
	/**
	 * Implementations are responsible for making sure given shell is Eclipse WizardDialog.
	 * @param shell instance of Eclipse WizardDialog
	 */
	public WorkbenchMenuWizardDialog(Shell shell){
		super(shell);
	}
	
	public WorkbenchMenuWizardDialog() {
		super();
	}
	
	/**
	 * 
	 * @param matcher to match WizardDialog
	 * @param path workbench shell menu path to open dialog
	 */
	public WorkbenchMenuWizardDialog(Matcher<String> matcher, String... path){
		super();
		this.matcher = matcher;
		this.path = path;
		isOpen();
	}
	
	@Override
	protected Openable getOpenAction() {
		return new WorkbenchMenuOpenable(matcher, path);
	}
	
}
