package org.jboss.reddeer.swt.impl.browser;

import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.matcher.WithLabelMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
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
	 * Finds first Browser inside given composite
	 * @param referencedComposite
	 */
	public InternalBrowser(ReferencedComposite referencedComposite) {
		this (referencedComposite, 0);
	}
	
	/**
	 * Finds Browser specified by index
	 * @param index
	 */
	public InternalBrowser(int index) {
		super(WidgetLookup.getInstance().activeWidget(null, org.eclipse.swt.browser.Browser.class,index));
	}
	
	/**
	 * Finds Browser specified by index inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public InternalBrowser(ReferencedComposite referencedComposite, int index) {
		super(WidgetLookup.getInstance().activeWidget(referencedComposite, org.eclipse.swt.browser.Browser.class,index));
	}
	
	/**
	 * Finds Browser specified by label
	 * @param label
	 */
	public InternalBrowser(String label) {
		super(WidgetLookup.getInstance().activeWidget(null, org.eclipse.swt.browser.Browser.class,
				0,
				new WithLabelMatcher(label)));
	}
	
	/**
	 * Finds Browser specified by label inside given composite
	 * @param referencedComposite
	 * @param label
	 */
	public InternalBrowser(ReferencedComposite referencedComposite, String label) {
		super(WidgetLookup.getInstance().activeWidget(referencedComposite, org.eclipse.swt.browser.Browser.class,
				0,
				new WithLabelMatcher(label)));
	}

}
