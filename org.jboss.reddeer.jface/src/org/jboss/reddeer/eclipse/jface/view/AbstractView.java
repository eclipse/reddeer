package org.jboss.reddeer.eclipse.jface.view;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.DefaultMenu;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;

public abstract class AbstractView {

	protected final Logger log = Logger.getLogger(this.getClass());

	public static final String SHOW_VIEW = "Show View";
	
	public void open() {

		String viewName = path()[path().length-1];
		log.info("Open " + viewName + " view");
		if (menuIsOpened()) {
			log.debug(viewName + " view was already opened.");
			// set focus on already opened view 
		}else {
			log.debug(viewName + " view was not already opened. Opening via menu.");
			Menu menu = new DefaultMenu();
			menu.select("Window", "Show View", "Other..."); 
			new ActiveShell(SHOW_VIEW);
			Tree t = new DefaultTree();
			t.select(path());
			new PushButton("OK").click();
		}
		
		
	}

	private boolean menuIsOpened() {
		//TODO 
		return false;
	}

	public abstract String[] path();
	
}
