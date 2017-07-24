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
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;

/**
 * Interface for all wizards openable from Workbench shell menu
 * @author rawagner
 *
 */
public class WorkbenchMenuOpenable extends Openable{
	
	private String[] menuPath; 
	
	public WorkbenchMenuOpenable(String shellTitle, String[] menuPath) {
		super(new WithTextMatcher(shellTitle));
		this.menuPath = menuPath;
	}
	
	public WorkbenchMenuOpenable(Matcher<?> shellMatcher, String[] menuPath) {
		super(shellMatcher);
		this.menuPath = menuPath;
	}

	@Override
	public void run() {
		new ShellMenuItem(new WorkbenchShell(), menuPath).select();
	}
	
	public String[] getMenuPath(){
		return menuPath;
	}
	

}
