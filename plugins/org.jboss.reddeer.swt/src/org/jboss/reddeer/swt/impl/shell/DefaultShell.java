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
package org.jboss.reddeer.swt.impl.shell;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.swt.exception.SWTLayerException;

/**
 * Default shell returns active shell if available if not it returns first
 * available shell
 * 
 * @author Jiri Peterka, Andrej Podhradsky, mlabuda@redhat.com
 * 
 */
public class DefaultShell extends AbstractShell {
	private static final Logger log = Logger.getLogger(DefaultShell.class);
	
	/**
	 * Instantiates a new default shell.
	 *
	 * @param title the title
	 */
	public DefaultShell(String title) {
		super(ShellLookup.getInstance().getShell(title));
		try {
			setFocus();
			log.debug("Shell with title '" + title + "' found");
		} catch (Exception e) {
			throw new SWTLayerException("No shell with title '" + title + "' is available", e);
		}
	}
	
	public DefaultShell(Shell widget){
		super(widget);
	}

	/**
	 * 
	 * Creates a new DefaultShell matching specified matcher. First found shell with 
	 * specified matcher is created. Beware, there is no strict (deterministic) order of shells.
	 * 
	 * @param matcher matcher to match title of a shell
	 */
	public DefaultShell(Matcher<String> matcher) {
		super(ShellLookup.getInstance().getShell(matcher));
		try {
			setFocus();
			log.debug("Shell matching specified matcher is found and focused");
		} catch (Exception e) {
			throw new SWTLayerException("Shell matching specified matcher was not focused successfully.", e);
		}
	}
	
	/**
	 * Instantiates a new default shell.
	 */
	public DefaultShell() {
		super(ShellLookup.getInstance().getActiveShell());
		try {
			setFocus();
			log.debug("Active shell with title '" + getText() + "' found");
		} catch (Exception e) {
			throw new SWTLayerException("No active shell is available at the moment", e);
		}
	}

}
