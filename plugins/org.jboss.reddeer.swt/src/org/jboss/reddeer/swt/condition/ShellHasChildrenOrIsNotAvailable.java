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
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.swt.api.Shell;

/**
 * Condition is met when shell has children or is not available anymore
 * @author rawagner
 *
 */
public class ShellHasChildrenOrIsNotAvailable extends AbstractWaitCondition {
	
	private Shell shell;
	private static final Logger log = Logger.getLogger(ShellHasChildrenOrIsNotAvailable.class);

	/**
	 * Default constructor.
	 * @param deleteShell instance of shell to test
	 */
	public ShellHasChildrenOrIsNotAvailable(Shell deleteShell) {
		this.shell = deleteShell;
	}

	@Override
	public boolean test() {
		int childShells = 0;
		org.eclipse.swt.widgets.Shell swtShell = shell.getSWTWidget();
		ShellHandler handler = ShellHandler.getInstance();
		try {
			childShells = handler.getShells(swtShell).length;
		} catch (CoreLayerException e) {
			if(swtShell != null){
				return handler.isDisposed(swtShell);
			}
			return true;
		}
		log.debug("number of child shells: " + childShells);
		return childShells > 0;
	}

	@Override
	public String description() {
		return "Shell has children or is not available.";
	}

}
