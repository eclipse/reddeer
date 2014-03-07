package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.Link;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Implements Link UI methods. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class LinkHandler {

	private static LinkHandler instance;

	private LinkHandler() {

	}

	/**
	 * Creates and returns instance of TreeHandler class
	 * 
	 * @return
	 */
	public static LinkHandler getInstance() {
		if (instance == null) {
			instance = new LinkHandler();
		}
		return instance;
	}
	
	public String getText(final Link l){
		
		String linkText = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return l.getText();
			}
		});
		
		String[] split1 = linkText.split(".*<[aA]>");
		String[] split2 = split1[split1.length-1].split("</[aA]>.*");
		return split2[0];
	}
}
