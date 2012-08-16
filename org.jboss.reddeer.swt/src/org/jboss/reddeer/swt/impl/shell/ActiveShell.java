package org.jboss.reddeer.swt.impl.shell;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.jboss.reddeeer.swt.regex.Regex;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.MultipleMatchException;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.swt.util.Display;

/**
 * @deprecated consider DefaulShell or WorkbenchShell,... instead of it
 * Shell implementation of active shell
 * 
 * @author Jiri Peterka
 * 
 */
public class ActiveShell extends AbstractShell implements Shell {

	public ActiveShell(String title) {
		this();
		log.info("Looking for shell with title:" + title);
		if (!title.equals(shell.getText())) {
			String message = "Active shell title is " + shell.getText()
					+ " but expected is " + title;

			log.error(message);
			throw new WidgetNotAvailableException(message);
		}
	}

	public ActiveShell() {

		try {
			shell = Bot.get().activeShell();
			shell.activate();
			shell.setFocus();
			log.info("Active Shell found:" + shell.getText());
		} catch (WidgetNotFoundException e) {
			throw new WidgetNotAvailableException(
					"Active Shell is not available");
		}
	}

	public ActiveShell(final Regex regex) {
		log.debug("Attempt to attach Shell matching \"" + regex.toString()
				+ "\" regular expression");
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
					Pattern p = regex.getPattern();
					Matcher m = p.matcher(s.getText());
					if (m.matches()) {
						shell = new SWTBotShell(s);
						log.info("Shell with title \"" + shell.getText()
								+ "\" attached");
						counter[0]++;
					}
				}
			}
		});
		if (shell == null) {
			String message = "Shell matching \"" + regex
					+ "\" regular expression not found";
			log.error(message);
			throw new WidgetNotAvailableException(message);
		}
		if (counter[0] > 1) {
			shell = null;
			String message = "Multiple shells matching \"" + regex
					+ "\" regular expression";
			log.error(message);
			throw new MultipleMatchException(message);
		}

	}
}
