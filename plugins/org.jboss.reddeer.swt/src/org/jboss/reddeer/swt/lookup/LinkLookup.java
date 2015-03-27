package org.jboss.reddeer.swt.lookup;

import org.eclipse.swt.widgets.Link;
import org.hamcrest.Matcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * @deprecated Since 1.0.0 use {@link AbstractWidget
 * @author Lucia Jelinkova
 *
 */
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
	public Link getLink(ReferencedComposite referencedComposite, int index, Matcher... matchers) {
		return (Link)WidgetLookup.getInstance().activeWidget(referencedComposite, Link.class, index, matchers);
	}


}
