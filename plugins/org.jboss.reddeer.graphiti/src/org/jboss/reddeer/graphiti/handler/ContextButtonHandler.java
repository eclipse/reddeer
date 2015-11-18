package org.jboss.reddeer.graphiti.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.graphiti.tb.ContextButtonEntry;
import org.eclipse.graphiti.tb.IContextButtonEntry;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.graphiti.api.ContextButton;
import org.jboss.reddeer.graphiti.impl.contextbutton.internal.BasicContextButton;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

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

	/**
	 * Gets the single instance of ContextButtonHandler.
	 *
	 * @return single instance of ContextButtonHandler
	 */
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

	/**
	 * Returns a list of context buttons from sub menu of a given context button entry.
	 * 
	 * @param contextButtonEntry
	 *            Context button entry
	 * @return list of context buttons from sub menu
	 */
	public List<ContextButton> getContextButtons(final IContextButtonEntry contextButtonEntry) {
		List<ContextButton> entries = new ArrayList<ContextButton>();
		if (contextButtonEntry instanceof ContextButtonEntry) {
			ContextButtonEntry entry = (ContextButtonEntry) contextButtonEntry;
			List<IContextButtonEntry> menuEntries = entry.getContextButtonMenuEntries();
			for (IContextButtonEntry menuEntry : menuEntries) {
				entries.add(new BasicContextButton(menuEntry));
			}
		}
		return entries;
	}
}
