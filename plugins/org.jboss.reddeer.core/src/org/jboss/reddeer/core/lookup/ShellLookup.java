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
package org.jboss.reddeer.core.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ActiveShellExists;
import org.jboss.reddeer.core.condition.ShellMatchingMatcherIsAvailable;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.util.DiagnosticTool;

/**
 * Shell Lookup provides methods for looking up various shells. 
 * Methods should be executed inside UI Thread.
 * 
 * @author Jiri Peterka, mlabuda@redhat.com
 * 
 */
public class ShellLookup {
	
	private static ShellLookup instance = null;
	
	private ShellLookup(){
		
	}

	/**
	 * Gets instance of ShellLookup.
	 * 
	 * @return ShellLookup instance
	 */
	public static ShellLookup getInstance() {
		if (instance == null)
			instance = new ShellLookup();
		return instance;
	}
	
	/**
	 * Gets active shell.
	 * If there is no active shell at the moment waits for a shell to become active.
	 * If there is no active shell even after waiting has finished then shell with focus is returned.
	 * If there still is no active shell, shell with highest index is returned.
	 * 
	 * @return active shell, or focused shell if there is no active shell 
	 * or shell with highest index if there is no active or focused shell
	 */
	public Shell getActiveShell() {
		new WaitUntil(new ActiveShellExists(), TimePeriod.SHORT, false);
		Shell activeShell = getCurrentActiveShell();
		// try to find shell with focus
		if (activeShell == null) {
			new WaitUntil(new ShellIsFocused(), TimePeriod.SHORT, false);
			activeShell = getCurrentFocusShell();
		}
		// if still no shell found, last visible shell will be returned
		if (activeShell == null) {
			activeShell = getLastVisibleShell();
		}
		return activeShell;
	}

	/**
	 * Gets currently Active Shell without waiting for shell to become active.
	 * 
	 * @return active shell or null if there is no active shell
	 */
	public Shell getCurrentActiveShell () {
		return Display.syncExec(new ResultRunnable<Shell>() {
			
			@Override
			public Shell run() {
				Shell s = Display.getDisplay().getActiveShell();
				if(s!=null && s.isVisible()){
					return s;
				}
				return null;
			}
		});
	}
	
	/**
	 * Gets currently focused and visible shell.
	 * 
	 * @return focused shell or null if there is no focused shell
	 */
	public Shell getCurrentFocusShell () {
		return Display.syncExec(new ResultRunnable<Shell>() {
			
			@Override
			public Shell run() {
				Shell[] ss = Display.getDisplay().getShells();
				for (Shell shell : ss) {
					if (shell.isFocusControl() && shell.isVisible()) {
						return shell;
					}
				}
				return null;
			}
		});
	}
	
	/**
	 * Gets all visible shells.
	 * 
	 * @return array of all visible shells
	 */
	public Shell[] getShells() {
		
		return Display.syncExec(new ResultRunnable<Shell[]>() {
			
			@Override
			public Shell[] run() {
				List<Shell> visibleShells = new ArrayList<Shell>();
				Shell[] shells = Display.getDisplay().getShells();
				for (Shell s : shells) {
					if (!s.isDisposed() && s.isVisible()) {
						visibleShells.add(s);
					}
				}
				return visibleShells.toArray(new Shell[visibleShells.size()]);
			}
			
		});
	}
	
	/**
	 * Waits for specified time period for a shell matching specified matcher.
	 * 
	 * @param matchers matchers to match shell
	 * @param timePeriod time period to wait for
	 * @return shell matching specified matcher
	 */
	public Shell getShell(TimePeriod timePeriod, Matcher<?>... matchers) {
		ShellMatchingMatcherIsAvailable shellCondition = new ShellMatchingMatcherIsAvailable(matchers);
		try{
			new WaitUntil(shellCondition, timePeriod);
		}catch (WaitTimeoutExpiredException e) {
			String exceptionText = shellCondition.errorMessageUntil();
			exceptionText += "\n" + new DiagnosticTool().getShellsDiagnosticInformation();
			throw new CoreLayerException(exceptionText);
		}
		return shellCondition.getResult();
	}
	
	/**
	 * Waits for normal time period for a shell matching specified matcher.
	 * 
	 * @param matchers matchers to match shell
	 * @return shell matching specified matcher
	 */
	public Shell getShell(Matcher<?>... matchers) {
		return getShell(TimePeriod.DEFAULT, matchers); 
	}
	
	/**
	 * Waits for specified time period for a shell with specified title.
	 * 
	 * @param title title of shell
	 * @param timePeriod time period to wait for
	 * @return shell with specified title
	 */
	public Shell getShell(String title , TimePeriod timePeriod) {
		return getShell(timePeriod, new WithTextMatcher(title));		
	}
	
	/**
	 * Waits for normal time period for a shell with specified title.
	 * 
	 * @param title title of shell
	 * @return shell with specified title
	 */
	public Shell getShell(String title) {
		return getShell(TimePeriod.DEFAULT, new WithTextMatcher(title));		
	}
	
	private Shell getLastVisibleShell() {
		return Display.syncExec(new ResultRunnable<Shell>() {
			@Override
			public Shell run() {
				Shell[] shells = Display.getDisplay().getShells();
				for (int i = shells.length - 1; i>=0; i--){
					if (shells[i].isVisible()) return shells[i];
				}
				return null;
			}
		});
	}
	
	/**
	 * Returns shell parent.
	 *
	 * @param shell the shell
	 * @return the parent shell
	 */
	public Shell getParentShell(Shell shell){
		Control parent = WidgetLookup.getInstance().getParent(shell);
		return parent == null ? null : (Shell)parent;
	}
	
	/**
	 * Return child shells.
	 *
	 * @param shell the shell
	 * @return the shells
	 */
	public Shell[] getShells(final Shell shell){
		return Display.syncExec(new ResultRunnable<Shell[]>() {

			@Override
			public Shell[] run() {
				return shell.getShells();
			}
		});
	}
	
	/**
	 * Wait condition is met when any shell is focused.
	 */
	class ShellIsFocused extends AbstractWaitCondition {
		
		/* (non-Javadoc)
		 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
		 */
		@Override
		public boolean test() {
			return getCurrentFocusShell() != null;
		}
		
		/* (non-Javadoc)
		 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
		 */
		@Override
		public String description() {
			return "shell is focused";
		}
	}
}


