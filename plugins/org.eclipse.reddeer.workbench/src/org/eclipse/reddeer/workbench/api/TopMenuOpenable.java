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
package org.eclipse.reddeer.workbench.api;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.jface.api.Window;
import org.eclipse.reddeer.jface.condition.WindowIsAvailable;
import org.eclipse.reddeer.swt.impl.menu.ShellMenu;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;

/**
 * Interface for all wizards openable from Workbench shell menu
 * @author rawagner
 *
 */
public interface TopMenuOpenable extends Window{
	
	Matcher<?> getShellMatcher();
	String[] getMenuPath();
	
	/**
	 * Opens window from Workbench shell menu. If window is already open, it will be focused.
	 */
	default void open(){
		Logger.getLogger(this.getClass()).debug("Open wizard from workbench shell menu");
		if(!isOpen()){
			new ShellMenu(new WorkbenchShell(), getMenuPath()).select();
			setShell(new DefaultShell(getShellMatcher()));
		}	
	}
	
	/**
	 * Checks if window is open. If window is already open, it will be focused.
	 * @return true if window is open, false otherwise
	 */
	default boolean isOpen(){
		WindowIsAvailable cond = new WindowIsAvailable(getEclipseClass(), getShellMatcher());
		boolean open = cond.test();
		if(open){
			setShell(new DefaultShell(cond.getResult()));
		}
		return open;
	}
	

}
