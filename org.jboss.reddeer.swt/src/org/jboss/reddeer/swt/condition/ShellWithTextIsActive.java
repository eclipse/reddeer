package org.jboss.reddeer.swt.condition;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
/**
 * Condition is fulfilled when shell with text is active
 * @author Vlado Pakan
 *
 */
public class ShellWithTextIsActive implements WaitCondition{
	
	private String text;
    
    public ShellWithTextIsActive(String text){
    	this.text = text;
    }
	@Override
	public boolean test() {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				Shell activeShell = Display.getDisplay().getActiveShell();
				return activeShell != null 
						&& activeShell.getText() != null
						&& activeShell.getText().equals(text);
			}
		});
	}

	@Override
	public String description() {
		return "Shell with text " + text + " is active";
	}
}
