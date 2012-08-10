package org.jboss.reddeer.workbench.view;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.ToolBar;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.DefaultMenu;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Represents general view with ability to be opened. Subclasses should represent the concrete views 
 * 
 * @author jjankovi
 *
 */
public abstract class AbstractView {

	protected final Logger log = Logger.getLogger(this.getClass());
	protected SWTBotView viewObject;
	
	private static final String SHOW_VIEW = "Show View";
	private String[] path;
	

	public AbstractView(String... path) {
		this.path = path;
	}
	
	public void open() {
		
		log.info("Open " + getText() + " view");
		try {
			viewObject = Bot.get().viewByTitle(getText());
			log.debug(getText() + " view was already opened.");
		} catch (WidgetNotFoundException ex) {
			log.debug(getText() + " view was not already opened. Opening via menu.");
			Menu menu = new DefaultMenu("Window", "Show View", "Other...");
			menu.select();
			new ActiveShell(SHOW_VIEW);
			new DefaultTreeItem(path).select();
			new PushButton("OK").click();
			viewObject = Bot.get().activeView();
		}
		viewObject.setFocus();
		viewObject.show();
	}
	
	public String getText() {
		if (viewObject == null) {
			return path[path.length-1];
		}
		return viewObject.getTitle();
	}
	
	public ToolBar getToolbar() {
		throw new UnsupportedOperationException("not implemented yet");
	}
	
}
