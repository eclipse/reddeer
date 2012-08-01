package org.jboss.reddeer.swt.impl.shell;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.util.Display;

/**
 * Abstract class for all Shells
 * @author Jiri Peterka
 *
 */
public abstract class BasicShell implements Shell {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	Display display;
	SWTBotShell shell;

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
