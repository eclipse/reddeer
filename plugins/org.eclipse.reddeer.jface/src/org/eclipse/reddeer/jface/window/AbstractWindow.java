/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.jface.window;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.MatcherBuilder;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.handler.WidgetHandler;
import org.eclipse.reddeer.core.lookup.ShellLookup;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.jface.api.Window;
import org.eclipse.reddeer.jface.condition.WindowIsAvailable;
import org.eclipse.reddeer.jface.exception.JFaceLayerException;
import org.eclipse.reddeer.jface.matcher.WindowMatcher;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
/**
 * Represends JFace Window
 * 
 * @author rawagner
 *
 */
public abstract class AbstractWindow implements Window{
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	protected Shell shell;
	protected Matcher<?>[] windowMatchers;
	protected Openable openAction;

	/**
	 * Finds shell with given text. Found shell must be instance of Eclipse
	 * Window
	 * 
	 * @param text
	 *            shell's text
	 */
	public AbstractWindow(String text) {
		this(new WithTextMatcher(text));
	}

	/**
	 * Implementations are responsible for making sure given shell is Eclipse
	 * Window.
	 * 
	 * @param shell
	 *            instance of Eclipse Window
	 */
	public AbstractWindow(Shell shell) {
		if(shell == null){
			throw new JFaceLayerException("Shell cannot be null");
		}
		this.shell = shell;
	}
	
	public AbstractWindow(){
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
		this.windowMatchers = matchers;
		this.shell = new DefaultShell(ShellLookup.getInstance().getShell(allMatchers));
	}
	
	/**
	 * Finds shell matching given matchers and set it as the shell of this window.
	 * The matchers given as constructor parameters will be applied. If they are
	 * not set, the matchers from OpenAction will be used.
	 */
	public void activate() {
		activate(TimePeriod.DEFAULT);
	}
	
	/**
	 * Finds shell matching given matchers and set it as the shell of this window.
	 * The matchers given as constructor parameters will be applied. If they are
	 * not set, the matchers from OpenAction will be used.
	 * 
	 * @param timeout
	 *            period to wait for
	 */
	public void activate(TimePeriod timeout) {		
		WindowIsAvailable cond;
		if(getWindowMatchers() != null){
			cond = new WindowIsAvailable(getEclipseClass(), getWindowMatchers());
		} else if(getOpenAction() != null){
			cond = new WindowIsAvailable(getEclipseClass(), getOpenAction().getShellMatchers());
		} else {
			throw new JFaceLayerException("Unable to activate window. No matcher was set.");
		}
		
		new WaitUntil(cond,timeout);
		setShell(new DefaultShell(cond.getResult()));
	}

	/**
	 * Gets the control.
	 *
	 * @return the control
	 */
	public Shell getShell() {
		return shell;
	}
	
	public void open(){
		if(getOpenAction() == null){
			throw new JFaceLayerException("Unable to open window because open action is not defined");
		}
		if(!isOpen()){
			getOpenAction().run();
			setShell(new DefaultShell(getOpenAction().getShellMatchers()));
		}
	}
	
	/**
	 * Checks if window is open. If window is already open, it will be focused.
	 * @return true if window is open, false otherwise
	 */
	public boolean isOpen(){
		if(shell != null){
			if(!shell.isDisposed() && shell.isVisible()){
				shell.setFocus();
				return true;			
			}
			return false;
		}
		
		WindowIsAvailable cond;
		if(getWindowMatchers() != null){
			cond = new WindowIsAvailable(getEclipseClass(), getWindowMatchers());
		} else if(getOpenAction() != null){
			cond = new WindowIsAvailable(getEclipseClass(), getOpenAction().getShellMatchers());
		} else {
			throw new JFaceLayerException("Unable to check if window is open");
		}
		
		boolean open = cond.test();
		if(open){
			setShell(new DefaultShell(cond.getResult()));
		}
		return open;
	}
	
	protected Openable getOpenAction() {
		return openAction != null ? openAction:getDefaultOpenAction();
	}
	
	public void setOpenAction(Openable openAction) {
		this.openAction = openAction;
	}
	
	/**
	 * Set Default Openable action which will be called on open(). User can override by {@link #setOpenAction()}
	 * @return Openable action
	 */
	public abstract Openable getDefaultOpenAction();

	private boolean isWindow(Shell shell) {
		return getEclipseClass().isInstance(WidgetHandler.getInstance().getData(shell.getSWTWidget()));
	}

	public void setShell(Shell swtShell) {
		checkShell(swtShell);
		if (!isWindow(swtShell)) {
			String msg = "Provided shell type is '" + getShellType(swtShell) + "' and expected is '" + getEclipseClass() + "'";
			throw new JFaceLayerException(msg);
		}

		this.shell = swtShell;
	}

	private String getShellType(Shell swtShell) {
		Object shellData = WidgetHandler.getInstance().getData(swtShell.getSWTWidget());
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

	@Override
	public Class<? extends org.eclipse.jface.window.Window> getEclipseClass() {
		return org.eclipse.jface.window.Window.class;
	}
	
	/**
	 * Returns matchers which matches this window
	 * @return
	 */
	public Matcher<?>[] getWindowMatchers(){
		return windowMatchers;
	}

	@Override
	public Control getControl() {
		if(shell == null) {
			return null;
		}
		if(shell.isDisposed()){
			throw new JFaceLayerException("Window is disposed");
		}
		return shell.getSWTWidget();
	}

}
