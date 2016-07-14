package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.core.util.InstanceValidator;
import org.jboss.reddeer.swt.api.Shell;

/**
 * Wait condition for shells checking whether specified shell is available
 * 
 * @author rawagner
 */
public class ShellIsAvailable extends AbstractWaitCondition {
	
	private Shell shell;
	
	/**
	 * Fulfilled, when available shell is equal to given shell.
	 * 
	 * @param shell Shell to compare to.
	 */
	public ShellIsAvailable(Shell shell){
		InstanceValidator.checkNotNull(shell, "shell");
		this.shell = shell;
	}

	@Override
	public boolean test() {
		for(org.eclipse.swt.widgets.Shell s: ShellLookup.getInstance().getShells()){
			if(shell.getSWTWidget().equals(s)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String description() {
		return "shell is available";
	}

}
