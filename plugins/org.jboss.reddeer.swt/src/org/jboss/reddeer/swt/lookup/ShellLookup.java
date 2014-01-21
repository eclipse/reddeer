package org.jboss.reddeer.swt.lookup;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.condition.ShellIsActive;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Shell Lookup, this contains routines for ToolBar implementation that have are widely used 
 * and also requires to be executed in UI Thread
 * @author Jiri Peterka
 * 
 */
public class ShellLookup {
	
	private static ShellLookup instance = null;
	
	private ShellLookup(){
		
	}

	/**
	 * Creates and returns instance of Shell Lookup
	 * 
	 * @return ButtonLookup instance
	 */
	public static ShellLookup getInstance() {
		if (instance == null)
			instance = new ShellLookup();
		return instance;
	}
	
	/**
	 * Returns active shell
	 * Waits for shell to become active in case there is no active shell at the moment
	 * If there is no active shell even after waiting has finished then shell with focus is returned
	 * If there still is no active shell, shell with highest index is returned (@link{org.eclipse.swt.widgets.Display.getShells()})
	 * @return
	 */
	public Shell getActiveShell() {
		new WaitUntil(new ShellIsActive(), TimePeriod.SHORT, false);
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
	 * Returns current Active Shell without waiting for shell to become active
	 * Can return null
	 * @return
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
	 * Returns current Focused Shell
	 * Can return null
	 * @return
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
	
	public Shell[] getShells() {
		
		return Display.syncExec(new ResultRunnable<Shell[]>() {
			
			@Override
			public Shell[] run() {
				return Display.getDisplay().getShells();
			}
			
		});
	}
	
	public Shell getShell(final Matcher<String> matcher) {
		
		return Display.syncExec(new ResultRunnable<Shell>() {
			
			@Override
			public Shell run() {
				Shell[] shell = Display.getDisplay().getShells(); 
				for (int i = 0; i < shell.length; i++) {
					if(matcher.matches(shell[i])) {
						return shell[i];
					}
				}
				return null;
			}
			
		});
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
	
	class ShellIsFocused implements WaitCondition{
		
		@Override
		public boolean test() {
			return getCurrentFocusShell() != null;
		}
		
		@Override
		public String description() {
			return "Shell is focused";
		}
	}
}


