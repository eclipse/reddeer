/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.workbench.api;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.jface.api.Window;
import org.jboss.reddeer.jface.condition.WindowIsAvailable;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.impl.shell.WorkbenchShell;

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
