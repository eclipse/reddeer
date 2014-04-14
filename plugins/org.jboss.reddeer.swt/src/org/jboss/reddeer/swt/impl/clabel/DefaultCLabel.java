package org.jboss.reddeer.swt.impl.clabel;

import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;

/**
 * Implements default CLabel widget
 * @author Jiri Peterka, Vlado Pakan
 *
 */
public class DefaultCLabel extends AbstractCLabel {
	/**
	 * Create DefautScale instance based on first available Scale found
	 */
	public DefaultCLabel() {
		this(0);
	}
	/**
	 * Create DefautScale instance matching given text
	 * @param text
	 */
	public DefaultCLabel(String text) {
		super(WidgetLookup.getInstance().activeWidget(null,org.eclipse.swt.custom.CLabel.class,0, new WithTextMatcher(text)));
	}
	/**
	 * Create DefaultScale instance matching given index
	 * @param index
	 */
	public DefaultCLabel(int index) {
		super(WidgetLookup.getInstance().activeWidget(null,org.eclipse.swt.custom.CLabel.class,index));
	}
}
