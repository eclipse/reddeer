package org.jboss.reddeer.swt.impl.spinner;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Spinner;
import org.jboss.reddeer.swt.lookup.SpinnerLookup;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Default Spinner implementation. Most standard Spinner implementation
 * 
 * @author Andrej Podhradsky
 * 
 */
public class DefaultSpinner extends AbstractSpinner implements Spinner {

	/**
	 * Spinner with index 0
	 */
	public DefaultSpinner() {
		this(0);
	}

	/**
	 * Spinner with given index
	 * 
	 * @param index
	 */
	public DefaultSpinner(int index) {
		swtSpinner = SpinnerLookup.getInstance().getSpinner(null, index);
	}

	/**
	 * Spinner with given index inside given composite
	 * 
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultSpinner(ReferencedComposite referencedComposite, int index) {
		swtSpinner = SpinnerLookup.getInstance().getSpinner(referencedComposite, index);
	}

	@SuppressWarnings("rawtypes")
	/**
	 * Spinner with given matchers
	 * @param matchers
	 */
	public DefaultSpinner(Matcher... matchers) {
		swtSpinner = SpinnerLookup.getInstance().getSpinner(null, 0, matchers);
	}

	@SuppressWarnings("rawtypes")
	/**
	 * Spinner with given matchers inside given composite
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultSpinner(ReferencedComposite referencedComposite, Matcher... matchers) {
		swtSpinner = SpinnerLookup.getInstance().getSpinner(referencedComposite, 0, matchers);
	}
}
