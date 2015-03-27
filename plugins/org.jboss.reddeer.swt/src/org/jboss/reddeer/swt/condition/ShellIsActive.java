package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.swt.util.internal.InstanceValidator;

/**
 * Wait condition for shells checking whether some shell is active (empty
 * constructor) or using Shell.equals (parameterized constructor).
 * 
 * @author mlabuda
 * @deprecated since 0.8, use {@link #org.jboss.reddeer.core.condition.ShellIsActive}
 */
@Deprecated
public class ShellIsActive implements WaitCondition {
	
	private Shell shell;
	private static final Logger log = Logger.getLogger(ShellIsActive.class);
	
	
	/**
	 * Fulfilled, when there is some shell active.
	 * 
	 */
	public ShellIsActive() {
		this.shell=null;
	}
	
	
	/**
	 * Fulfilled, when active shell is equal to given shell.
	 * 
	 * @param shell Shell to compare to.
	 */
	public ShellIsActive(Shell shell){
		InstanceValidator.checkNotNull(shell, "shell");
		this.shell = shell;
	}
	
	@Override
	public boolean test() {
		if (shell == null){
			return ShellLookup.getInstance().getCurrentActiveShell() != null;
		}else{
			org.eclipse.swt.widgets.Shell currentActiveShell = ShellLookup.getInstance()
					.getCurrentActiveShell();
			if (currentActiveShell == null) {
				log.debug("Current active shell is null");
				return false;
			}
			return currentActiveShell.equals(shell.getSWTWidget());
		}
	}

	@Override
	public String description() {
		return "shell is active";
	}
}
