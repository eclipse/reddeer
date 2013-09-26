package org.jboss.reddeer.swt.impl.spinner;

import org.jboss.reddeer.swt.api.Spinner;
import org.jboss.reddeer.swt.lookup.SpinnerLookup;
import org.jboss.reddeer.swt.matcher.LabelMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Spinner with label implementation
 * 
 * @author Andrej Podhradsky
 * 
 */
public class LabeledSpinner extends AbstractSpinner implements Spinner {

	/**
	 * Default spinner with a label
	 * 
	 * @param label
	 */
	public LabeledSpinner(String label) {
		LabelMatcher lm = new LabelMatcher(label);
		swtSpinner = SpinnerLookup.getInstance().getSpinner(null, 0, lm);
	}

	/**
	 * Default spinner with a label inside given composite
	 * 
	 * @param referencedComposite
	 * @param label
	 */
	public LabeledSpinner(ReferencedComposite referencedComposite, String label) {
		LabelMatcher lm = new LabelMatcher(label);
		swtSpinner = SpinnerLookup.getInstance().getSpinner(referencedComposite, 0, lm);
	}

}
