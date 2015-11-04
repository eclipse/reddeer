package org.jboss.reddeer.swt.impl.ccombo;

import org.jboss.reddeer.core.matcher.WithLabelMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.api.CCombo;

/**
 * CCombo with label implementation
 * 
 * @author Andrej Podhradsky
 *
 */
public class LabeledCCombo extends AbstractCCombo implements CCombo {

	/**
	 * Searches for custom combo with label
	 * 
	 * @param label
	 *            label of the custom combo
	 */
	public LabeledCCombo(String label) {
		this(null, label);
	}

	/**
	 * Searches for custom combo with label inside given composite
	 * 
	 * @param referencedComposite
	 *            composite inside which custom combo should be looked for
	 * @param label
	 *            label of the custom combo
	 */
	public LabeledCCombo(ReferencedComposite referencedComposite, String label) {
		super(referencedComposite, 0, new WithLabelMatcher(label));
	}
}
