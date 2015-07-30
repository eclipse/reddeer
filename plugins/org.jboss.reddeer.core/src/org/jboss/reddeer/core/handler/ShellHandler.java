package org.jboss.reddeer.core.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.platform.RunningPlatform;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.core.lookup.WidgetLookup;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.core.matcher.WithStyleMatcher;

/**
 * Contains methods for handling UI operations on {@link Shell} widgets.
 * 
 * @author Jiri Peterka
 *
 */
public class ShellHandler {

	private static ShellHandler instance;
	private static Shell workbenchShell = ShellLookup.getInstance().getWorkbenchShell();
	
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
				shell.forceActive();
				shell.forceFocus();
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
			shells = filterDisposedShells(shells);
			// if no active shell present close first one
			if (s == null && shells.size() > 0){
				s = shells.get(0);
			}
			if (s != null && !s.isDisposed()) {
				if (beforeShellIsClosed != null){
					beforeShellIsClosed.runBeforeShellIsClosed(s);
				}
				closeShellSafely(s);
			}
			// reload current shells list
			shells = getNonWorbenchShellsToClose();
		} while ((shells.size() > 0) && (System.currentTimeMillis() < timeOut));
	}
	
	
	public void closeShellSafely(Shell swtShell) {
		String text = WidgetHandler.getInstance().getText(swtShell);
		log.info("Close shell " + text);
		try {
			clickCancelButton();
		} catch (Exception e) {
			WidgetHandler.getInstance().notify(SWT.Close, swtShell);
			ShellHandler.getInstance().closeShell(swtShell);
		}
		new WaitWhile(new ShellWithTextIsAvailable(text));
	}
	
	/**
	 * Closes all opened {@link Shell}s except the workbench shell.
	 */
	public void closeAllNonWorbenchShells(){
		this.closeAllNonWorbenchShells(null);
	}
	
	private void clickCancelButton() {
		Button button = WidgetLookup.getInstance().activeWidget(null, Button.class, 0, createMatchers(SWT.PUSH, new WithMnemonicTextMatcher("Cancel")));
        if (RunningPlatform.isWindows() &&
                ((WidgetHandler.getInstance().getStyle(button) & SWT.RADIO) != 0)){
                // do not set focus because it also select radio button on Windows
        } else{
        	WidgetHandler.getInstance().setFocus(button);    
        }
        ButtonHandler.getInstance().click(button);
	}
	
	private static Matcher<?>[] createMatchers(int style, Matcher<?>... matchers) {
		List<Matcher<?>> list= new ArrayList<Matcher<?>>();

		list.add(new WithStyleMatcher(style));			
		list.addAll(Arrays.asList(matchers));
		return list.toArray(new Matcher[list.size()]);
	}
	
	private List<Shell> getNonWorbenchShellsToClose() {
		List<Shell> shellsToClose = new ArrayList<Shell>();
		Shell[] currentShells = ShellLookup.getInstance().getShells();
		for (int i = 0; i < currentShells.length; i++) {
			if (currentShells[i] != ShellHandler.workbenchShell && !currentShells[i].isDisposed()) {
				shellsToClose.add(currentShells[i]);
			}
		}
		return shellsToClose;
	}
	
	private List<Shell> filterDisposedShells(List<Shell> shells) {
		Iterator<Shell> itShell = shells.iterator();
		while (itShell.hasNext()){
			if (itShell.next().isDisposed()){
				itShell.remove();
			}
		}
		return shells;
	}
	
	private Shell getFilteredActiveShell(List<Shell> shells){
		Shell result = null;
		
		if (shells != null){
			Shell activeShell = ShellLookup.getInstance().getActiveShell();
			if (activeShell != null){
				Iterator<Shell> itShell = shells.iterator();
				while (itShell.hasNext() && result == null){
					Shell shell = itShell.next();
					if (!shell.isDisposed() && WidgetHandler.getInstance().getText(shell).equals(
							WidgetHandler.getInstance().getText(activeShell))){
						result = shell;
					}
				}
			}
		}
		
		return result;	
	}
	/**
	 * Returns shell parent
	 * @param shell
	 * @return
	 */
	public Shell getParentShell(Shell shell){
		Control parent = WidgetHandler.getInstance().getParent(shell);
		return parent == null ? null : (Shell)parent;
	}
	
	/**
	 * Return child shells
	 * @param shell
	 * @return
	 */
	public Shell[] getShells(final Shell shell){
		return Display.syncExec(new ResultRunnable<Shell[]>() {

			@Override
			public Shell[] run() {
				return shell.getShells();
			}
		});
	}
	
	
}
