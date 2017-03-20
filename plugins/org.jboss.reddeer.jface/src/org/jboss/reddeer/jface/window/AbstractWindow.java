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
package org.jboss.reddeer.jface.window;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.matcher.MatcherBuilder;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.jface.api.Window;
import org.jboss.reddeer.jface.exception.JFaceLayerException;
import org.jboss.reddeer.jface.matcher.WindowMatcher;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
/**
 * Represends JFace Window
 * 
 * @author rawagner
 *
 */
public abstract class AbstractWindow implements Window{
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	private Shell shell;

	/**
	 * Finds shell with given text. Found shell must be instance of Eclipse
	 * Window
	 * 
	 * @param text
	 *            shell's text
	 */
	public AbstractWindow(String text) {
		WindowMatcher wm = new WindowMatcher(getEclipseClass());
		shell = new DefaultShell(ShellLookup.getInstance().getShell(new WithTextMatcher(text), wm));
	}

	/**
	 * Implementations are responsible for making sure given shell is Eclipse
	 * Window.
	 * 
	 * @param shell
	 *            instance of Eclipse Window
	 */
	public AbstractWindow(Shell shell) {
		this.shell = shell;
	}

	/**
	 * Finds shell matching given matcher. Found shell must be instance of
	 * Eclipse Window
	 * 
	 * @param matchers
	 *            to match shell
	 */
	public AbstractWindow(Matcher<?>...matchers) {
		WindowMatcher wm = new WindowMatcher(getEclipseClass());
		Matcher<?>[] allMatchers = MatcherBuilder.getInstance().addMatcher(matchers, wm);
		shell = new DefaultShell(ShellLookup.getInstance().getShell(allMatchers));
	}

	/**
	 * Gets the control.
	 *
	 * @return the control
	 */
	public Shell getShell() {
		return shell;
	}

	private boolean isWindow(Shell shell) {
		return getEclipseClass().isInstance(WidgetHandler.getInstance().getData(shell.getSWTWidget()));
	}

	public void setShell(Shell swtShell) {
		checkShell(swtShell);
		if (!isWindow(swtShell)) {
			String msg = "Provided shell type is '" + getShellType() + "' and expected is '" + getEclipseClass() + "'";
			throw new JFaceLayerException(msg);
		}

		this.shell = swtShell;
	}

	private String getShellType() {
		Object shellData = WidgetHandler.getInstance().getData(shell.getSWTWidget());
		String shellType;
		if (shellData == null) {
			shellType = Shell.class.toString();
		} else {
			shellType = shellData.getClass().toString();
		}
		return shellType;
	}

	private void checkShell(Shell shell) {
		if (shell == null) {
			throw new JFaceLayerException("Provided shell is null");
		}
		if (shell.isDisposed()) {
			throw new JFaceLayerException("Provided shell is disposed");
		}
		log.trace("Shell "+shell.getText()+" is not null and is not disposed");
	}
	
	protected void checkShell() {
		if (shell == null) {
			throw new JFaceLayerException("Window was not initialized");
		}
		if (shell.isDisposed()) {
			throw new JFaceLayerException("Window shell is disposed");
		}
		log.trace("Shell "+shell.getText()+" is not null and is not disposed");
	}

}
