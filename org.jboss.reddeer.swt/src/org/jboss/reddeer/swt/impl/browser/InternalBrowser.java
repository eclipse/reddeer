package org.jboss.reddeer.swt.impl.browser;

import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.matcher.LabelMatcher;
/**
 * Implements SWT Browser manipulations
 * @author Jiri Peterka, Vlado Pakan
 *
 */
public class InternalBrowser extends AbstractBrowser{
	/**
	 * Finds first Browser
	 */
	public InternalBrowser() {
		this (0);
	}
	/**
	 * Finds Browser specified by index
	 * @param index
	 */
	public InternalBrowser(int index) {
		super(WidgetLookup.getInstance().activeWidget(org.eclipse.swt.browser.Browser.class,index));
		setReady();
		}
	/**
	 * Finds Browser specified by label
	 * @param label
	 */
	public InternalBrowser(String label) {
		super(WidgetLookup.getInstance().activeWidget(org.eclipse.swt.browser.Browser.class,
				0,
				new LabelMatcher(label)));
		setReady();
	}

}
