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
package org.eclipse.reddeer.workbench.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.handler.ButtonHandler;
import org.eclipse.reddeer.core.handler.IBeforeShellIsClosed;
import org.eclipse.reddeer.core.handler.ShellHandler;
import org.eclipse.reddeer.core.lookup.ShellLookup;
import org.eclipse.reddeer.core.lookup.WidgetLookup;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.core.matcher.WithStyleMatcher;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.core.lookup.WorkbenchShellLookup;

public class WorkbenchShellHandler {
	
	private final Logger log = Logger.getLogger(this.getClass());
	private static WorkbenchShellHandler instance;
	
	/**
	 * Gets instance of WorkbenchShellHandler.
	 * 
	 * @return instance of WorkbenchShellHandler
	 */
	public static WorkbenchShellHandler getInstance(){
		if(instance == null){
			instance = new WorkbenchShellHandler();
		}
		return instance;
	}
	
	/**
	 * Closes all opened {@link Shell}s except the workbench shell.
	 */
	public void closeAllNonWorbenchShells(){
		this.closeAllNonWorbenchShells(null);
	}
	
	/**
	 * Closes all opened {@link Shell}s except the workbench shell.
	 * There can be executed action before closing shells.
	 * 
	 * @param beforeShellIsClosed callback method is 
	 * {@link IBeforeShellIsClosed#runBeforeShellIsClosed(Shell)} called before 
	 * shells are closed
	 */
	public void closeAllNonWorbenchShells(IBeforeShellIsClosed beforeShellIsClosed) {
		log.info("Closing all shells...");
		List<Shell> shells = getNonWorbenchShellsToClose();
		long timeOut = System.currentTimeMillis() + (TimePeriod.VERY_LONG.getSeconds() * 1000);
		do {
			// first try to close active shell and reload shells list
			Shell s = getFilteredActiveShell(shells);
			// if no active shell present close first one
			try{
				if (s == null && shells.size() > 0){
					s = shells.get(0);
				}
				if (s != null && !s.isDisposed()) {
					if (beforeShellIsClosed != null){
						beforeShellIsClosed.runBeforeShellIsClosed(s);
					}
					closeShellSafely(s);
				}
			} catch (CoreLayerException ex){
				if(!ShellHandler.getInstance().isDisposed(s)){
					throw ex;
				}
			}
			// reload current shells list
			shells = getNonWorbenchShellsToClose();
		} while ((shells.size() > 0) && (System.currentTimeMillis() < timeOut));
	}
	
	private List<Shell> getNonWorbenchShellsToClose() {
		List<Shell> shellsToClose = new ArrayList<Shell>();
		Shell[] currentShells = ShellLookup.getInstance().getShells();
		Shell workbenchShell = WorkbenchShellLookup.getInstance().getWorkbenchShell();
		for (int i = 0; i < currentShells.length; i++) {
			if (currentShells[i] != workbenchShell && !currentShells[i].isDisposed()) {
				shellsToClose.add(currentShells[i]);
			}
		}
		return shellsToClose;
	}
	
	private Shell getFilteredActiveShell(List<Shell> shells) {
		Shell result = null;

		if (shells != null) {
			Shell activeShell = ShellLookup.getInstance().getActiveShell();
			if (activeShell != null) {
				Iterator<Shell> itShell = shells.iterator();
				while (itShell.hasNext() && result == null) {
					Shell shell = itShell.next();
					if (!shell.isDisposed() && ShellHandler.getInstance().getText(shell)
							.equals(ShellHandler.getInstance().getText(activeShell))) {
						result = shell;
					}
				}
			}
		}

		return result;
	}
	
	/**
	 * Close shell safely.
	 *
	 * @param swtShell
	 *            the swt shell
	 */
	private void closeShellSafely(Shell swtShell) {
		try{
			String text = ShellHandler.getInstance().getText(swtShell);
			log.info("Close shell " + text);
			try {
				clickCancelButton(swtShell);
			} catch (Exception e) {
				ShellHandler.getInstance().notifyWidget(SWT.Close, swtShell);
				ShellHandler.getInstance().closeShell(swtShell);
			}
			new WaitWhile(new ShellIsAvailable(new DefaultShell(swtShell)));
		} catch (Exception e) {
			if(!swtShell.isDisposed()){
				throw e;
			}
		}
	}

	private void clickCancelButton(Shell shell) {
		Button button = WidgetLookup.getInstance().activeWidget(new DefaultShell(shell), Button.class, 0,
				createMatchers(SWT.PUSH, new WithMnemonicTextMatcher("Cancel")));
		ButtonHandler.getInstance().click(button);
	}

	private static Matcher<?>[] createMatchers(int style, Matcher<?>... matchers) {
		List<Matcher<?>> list = new ArrayList<Matcher<?>>();

		list.add(new WithStyleMatcher(style));
		list.addAll(Arrays.asList(matchers));
		return list.toArray(new Matcher[list.size()]);
	}

}
