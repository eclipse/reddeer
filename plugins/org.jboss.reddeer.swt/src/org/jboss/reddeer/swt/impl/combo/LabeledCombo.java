package org.jboss.reddeer.swt.impl.combo;

import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.swt.lookup.ComboLookup;
import org.jboss.reddeer.swt.matcher.WithLabelMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
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
	  swtCombo = ComboLookup.getInstance().getCombo(null, 0, new WithLabelMatcher(label));
	}
	
	/**
     * Searches for combo with label inside given composite
	 * @param referencedComposite
     * @param label
     */
	public LabeledCombo(ReferencedComposite referencedComposite, String label) {
	  swtCombo = ComboLookup.getInstance().getCombo(referencedComposite, 0, new WithLabelMatcher(label));
	}
}
