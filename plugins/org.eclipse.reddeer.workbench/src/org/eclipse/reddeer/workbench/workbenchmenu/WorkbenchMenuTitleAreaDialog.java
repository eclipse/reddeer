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
import org.eclipse.reddeer.jface.dialogs.TitleAreaDialog;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.swt.api.Shell;

/**
 * Represents all TitleAreaDialog dialogs openable from workbench shell menu
 * @author rawagner
 *
 */
public abstract class WorkbenchMenuTitleAreaDialog extends TitleAreaDialog {
	
	protected Matcher<String> matcher;
	protected String[] path;
	
	/**
	 * 
	 * @param text of TitleAreaDialog
	 * @param path workbench shell menu path to open dialog
	 */
	public WorkbenchMenuTitleAreaDialog(String text, String... path){
		this(new WithTextMatcher(text),path);
	}
	
	/**
	 * Implementations are responsible for making sure given shell is Eclipse TitleAreaDialog.
	 * @param shell instance of Eclipse TitleAreaDialog
	 */
	public WorkbenchMenuTitleAreaDialog(Shell shell){
		super(shell);
	}
	
	/**
	 * 
	 * @param matcher to match TitleAreaDialog
	 * @param path workbench shell menu path to open dialog
	 */
	public WorkbenchMenuTitleAreaDialog(Matcher<String> matcher, String... path){
		super();
		this.matcher = matcher;
		this.path = path;
		isOpen();
	}
	
	@Override
	public Openable getDefaultOpenAction() {
		return new WorkbenchMenuOpenable(matcher, path);
	}

}
