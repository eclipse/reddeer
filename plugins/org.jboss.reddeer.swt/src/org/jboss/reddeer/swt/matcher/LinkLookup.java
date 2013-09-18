package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Link;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;

public class LinkLookup {
	
	private static LinkLookup instance = null;

	private LinkLookup() {
	}

	/**
	 * Creates and returns instance of Link Lookup
	 * 
	 * @return LinkLookup instance
	 */
	public static LinkLookup getInstance() {
		if (instance == null)
			instance = new LinkLookup();
		return instance;
	}

	/**
	 * Return Link instance
	 * 
	 * @param matcher
	 * @return Link Widget matching criteria
	 */
	@SuppressWarnings({ "rawtypes" })
	public Link getLink(int index, Matcher... matchers) {
		return (Link)WidgetLookup.getInstance().activeWidget(Link.class, index, matchers);
	}


}
