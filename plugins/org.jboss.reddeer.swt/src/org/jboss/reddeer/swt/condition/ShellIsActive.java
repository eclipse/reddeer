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
package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.core.util.InstanceValidator;
import org.jboss.reddeer.swt.api.Shell;

/**
 * Wait condition for shells checking whether some shell is active (empty
 * constructor) or using Shell.equals (parameterized constructor).
 * 
 * @author rhopp, mlabuda@redhat.com
 */
public class ShellIsActive extends AbstractWaitCondition {
	
	private Shell shell;
	private static final Logger log = Logger.getLogger(ShellIsActive.class);
	
	/**
	 * Fulfilled, when active shell is equal to given shell.
	 * 
	 * @param shell Shell to compare to.
	 */
	public ShellIsActive(Shell shell){
		InstanceValidator.checkNotNull(shell, "shell");
		this.shell = shell;
	}
	
	@Override
	public boolean test() {
		if (shell == null){
			return ShellLookup.getInstance().getCurrentActiveShell() != null;
		}else{
			org.eclipse.swt.widgets.Shell currentActiveShell = ShellLookup.getInstance()
					.getCurrentActiveShell();
			if (currentActiveShell == null) {
				log.debug("Current active shell is null");
				return false;
			}
			return currentActiveShell.equals(shell.getSWTWidget());
		}
	}

	@Override
	public String description() {
		return "shell is active";
	}
}
