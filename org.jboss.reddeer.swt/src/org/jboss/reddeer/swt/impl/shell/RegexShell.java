package org.jboss.reddeer.swt.impl.shell;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.MultipleMatchException;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.util.Display;

/**
 * Shell implementation matching shell with title given by a regular expression
 * 
 * @author Jiri Peterka
 * 
 */
public class RegexShell extends AbstractShell implements Shell {

	public RegexShell(final String regex) {

		log.debug("Attempt to attach Shell matching \"" + regex + "\" regular expression");
		shell = null;
		final int[] counter = new int[1];
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				org.eclipse.swt.widgets.Shell[] shells = Display.getDisplay()
						.getShells();
				for (org.eclipse.swt.widgets.Shell s : shells) {
					String title = s.getText();
					log.debug("Shell with title \"" + title + "\" found");
					if (title.matches(regex)) {
						shell = new SWTBotShell(s);
						log.info("Shell with title \"" + shell.getText() + "\" attached");
						counter[0]++;
					}
				}
			}
		});
		if (shell == null) {			
			String message = "Shell matching \"" + regex + "\" regular expression not found";
			log.error(message);
			throw new WidgetNotAvailableException(message);
		}
		if (counter[0] > 1) {
			shell = null;
			String message = "Multiple shells matching \"" + regex + "\" regular expression";
			log.error(message);
			throw new MultipleMatchException(message);			
		}
	}
}