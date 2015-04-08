package org.jboss.reddeer.swt.impl.combo;

import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.core.matcher.WithLabelMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
/**
 * Combo with label implementation
 * @author Vlado Pakan
 *
 */
public class LabeledCombo extends AbstractCombo implements Combo {
   
	/**
     * Searches for combo with label 
     * @param label
     */
	public LabeledCombo(String label) {
		this(null, label);
	}
	
	/**
     * Searches for combo with label inside given composite
	 * @param referencedComposite
     * @param label
     */
	public LabeledCombo(ReferencedComposite referencedComposite, String label) {
		super(referencedComposite, 0, new WithLabelMatcher(label));
	}
}
