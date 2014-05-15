package org.jboss.reddeer.graphiti.handler;

import org.eclipse.graphiti.tb.IContextButtonEntry;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Handler for ContextButton operations.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class ContextButtonHandler {

	protected final Logger log = Logger.getLogger(this.getClass());

	private static ContextButtonHandler instance;

	private ContextButtonHandler() {

	}

	public static ContextButtonHandler getInstance() {
		if (instance == null) {
			instance = new ContextButtonHandler();
		}
		return instance;
	}

	/**
	 * Clicks on a given context button entry.
	 * 
	 * @param contextButtonEntry
	 *            Context button entry
	 */
	public void click(final IContextButtonEntry contextButtonEntry) {
		Display.asyncExec(new Runnable() {
			@Override
			public void run() {
				contextButtonEntry.execute();
			}
		});
	}

	/**
	 * Returns a text from a given context button entry.
	 * 
	 * @param contextButtonEntry
	 *            Context button entry
	 * @return text
	 */
	public String getText(final IContextButtonEntry contextButtonEntry) {
		return Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return contextButtonEntry.getText();
			}
		});
	}
}
