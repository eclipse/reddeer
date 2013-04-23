package org.jboss.reddeer.swt.impl.shell;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.reference.ReferenceComposite;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Default shell returns active shell if available
 * if not it returns first available shell
 *  
 * @author Jiri Peterka
 *
 */
public class DefaultShell extends AbstractShell implements Shell, ReferencedComposite {

	
	public DefaultShell(String title) {
		try {
			waitForShell(title);
			shell = Bot.get().shell(title);
			shell.setFocus();
			log.info("Shell with title '" + title + "' found");
		}
		catch (Exception e) {
			throw new SWTLayerException("No shell is available at the moment");
		}
	}

	public DefaultShell() {
		try {
			shell = Bot.get().activeShell();
			shell.setFocus();
			log.info("Active shell with title '" + shell.getText() + "' found");
		}
		catch (WidgetNotFoundException e) {
			throw new SWTLayerException("No active shell is available at the moment");
		}
	}

	@Override
	public void setAsReference() {
		ReferenceComposite.setComposite(null);
	}
	
	private void waitForShell(String title) {
		try {
			new WaitUntil(new ShellWithTextIsActive(title));
		}catch(TimeoutException te) {
			throw new SWTLayerException(te.getLocalizedMessage());
		}

	}
}	
	

