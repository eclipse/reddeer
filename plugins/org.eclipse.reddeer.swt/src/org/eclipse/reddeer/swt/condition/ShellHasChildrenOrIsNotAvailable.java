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
package org.eclipse.reddeer.swt.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.handler.WidgetHandler;
import org.eclipse.reddeer.core.lookup.ShellLookup;
import org.eclipse.reddeer.swt.api.Shell;

/**
 * Condition is met when shell has children or is not available anymore
 * @author rawagner
 *
 */
public class ShellHasChildrenOrIsNotAvailable extends AbstractWaitCondition {
	
	private Shell shell;
	private static final Logger log = Logger.getLogger(ShellHasChildrenOrIsNotAvailable.class);
	private List<org.eclipse.swt.widgets.Shell> resultChildren;

	/**
	 * Default constructor.
	 * @param deleteShell instance of shell to test
	 */
	public ShellHasChildrenOrIsNotAvailable(Shell deleteShell) {
		this.shell = deleteShell;
		this.resultChildren = new ArrayList<>();
	}

	@Override
	public boolean test() {
		org.eclipse.swt.widgets.Shell swtShell = shell.getSWTWidget();
		try {
			this.resultChildren = Arrays.asList(ShellLookup.getInstance().getShells(swtShell));
		} catch (RedDeerException e) {
			if(swtShell != null){
				return WidgetHandler.getInstance().isDisposed(swtShell);
			}
			return true;
		}
		log.debug("number of child shells: " + this.resultChildren.size());
		if (this.resultChildren.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public String description() {
		return "Shell has children or is not available.";
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public List<org.eclipse.swt.widgets.Shell> getResult() {
		return this.resultChildren.isEmpty() ? null : this.resultChildren;
	}

}
