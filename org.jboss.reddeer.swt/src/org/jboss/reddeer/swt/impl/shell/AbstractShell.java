package org.jboss.reddeer.swt.impl.shell;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.jboss.reddeer.swt.api.Shell;

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
		shell.close();		
	}

}
