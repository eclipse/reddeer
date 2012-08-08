package org.jboss.reddeer.workbench.view;

import org.jboss.reddeer.swt.util.Bot;

/**
 * Represents view which is currently active
 * 
 * @author jjankovi
 *
 */
public class ActiveView extends AbstractView {

	public ActiveView() {
		viewObject = Bot.get().activeView();
	}
	
}
