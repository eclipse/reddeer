package org.jboss.reddeer.swt.impl.shell;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Abstract class for all Shells
 * @author Jiri Peterka
 *
 */
public abstract class AbstractShell implements Shell {

	protected final Logger log = Logger.getLogger(this.getClass());
		
	protected SWTBotShell shell;

	@Override
	public String getText() {
		String text = shell.getText();
		return text;
	}
	
	@Override
	public void close() {
		log.info("Closing shell " + shell.getText());
		shell.close();		
	}
	
	public void activate(){
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				shell.widget.setActive();
			}
		});
	}
	
	public boolean isActive(){
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return (shell.widget == PlatformUI.getWorkbench().getDisplay().getActiveShell());
			}
			
		});
	}
}
