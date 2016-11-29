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
package org.jboss.reddeer.core.handler;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.handler.ShellHandler;

/**
 * Contains methods for handling UI operations on {@link Shell} widgets.
 * 
 * @author Jiri Peterka
 *
 */
public class ShellHandler {

	private static ShellHandler instance;
	
	private static final Logger log = Logger.getLogger(ShellHandler.class);

	private ShellHandler() {

	}

	/**
	 * Gets instance of ShellHandler.
	 * 
	 * @return instance of ShellHandler
	 */
	public static ShellHandler getInstance() {
		if (instance == null) {
			instance = new ShellHandler();
		}
		return instance;
	}


	/**
	 * Closes specified {@link Shell}.
	 * 
	 * @param swtShell shell to close
	 */
	public void closeShell(final Shell swtShell) {

		log.trace("Closing shell");
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (swtShell != null && !swtShell.isDisposed()) {
					swtShell.close();
				}
			}
		});
	}

	/**
	 * Focuses on specified {@link Shell}.
	 * 
	 * @param shell shell to focus
	 */
	public void setFocus(final Shell shell) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					shell.forceActive();
					boolean isFocus = shell.forceFocus();
					if (!isFocus) log.warn("Shell is not focused");
				} catch (SWTException e) {
					throw new CoreLayerException("Exception thrown during forcing focus",e);
				}
			}
		});
	}
	
	/**
	 * Finds out whether specified {@link Shell} is visible or not.
	 * 
	 * @param shell shell to handle
	 * @return true if shell is visible, false otherwise
	 */
	public boolean isVisible(final Shell shell) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return shell.isVisible();
			}
		});
	}
	
	/**
	 * Finds out whether specified {@link Shell} is focused or not.
	 * 
	 * @param shell shell to handle
	 * @return true if shell is focused, false otherwise
	 */
	public boolean isFocused(final Shell shell) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return shell.isFocusControl();
			}
		});
	}
	
	/**
	 * Find out whether specified {@link Shell} is disposed or not.
	 * 
	 * @param shell shell to handle
	 * @return true if shell is disposed, false otherwise
	 */
	public boolean isDisposed(final Shell shell) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return shell.isDisposed();
			}
		});
	}
	
	
}
