package org.jboss.reddeer.swt.impl.combo;

import org.hamcrest.Matcher;
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
	
	/**
     * Searches for combo with index 
     * @param index
     * @deprecated use DefaultCombo(int index) instead
     */
	public LabeledCombo(int index) {
	  swtCombo = ComboLookup.getInstance().getCombo(null, index);
	}
	
	/**
     * Searches for combo with index inside given composite
	 * @param referencedComposite
     * @param index
     * @deprecated use DefaultCombo(ReferencedComposite referencedComposite, int index) instead
     */
	public LabeledCombo(ReferencedComposite referencedComposite, int index) {
	  swtCombo = ComboLookup.getInstance().getCombo(referencedComposite, index);
	}
	
	/**
     * Searches for combo matching all matchers  
     * @param matchers
     * @deprecated use DefaultCombo(Matcher... matchers) instead
     */
	@SuppressWarnings("rawtypes")
	public LabeledCombo(Matcher... matchers) {
	  swtCombo = ComboLookup.getInstance().getCombo(null, 0, matchers);
	}
	
	/**
     * Searches for combo matching all matchers inside given composite
	 * @param referencedComposite
     * @param matchers
     * @deprecated use DefaultCombo(ReferencedComposite referencedComposite, Matcher... matchers) instead
     */
	@SuppressWarnings("rawtypes")
	public LabeledCombo(ReferencedComposite referencedComposite, Matcher... matchers) {
	  swtCombo = ComboLookup.getInstance().getCombo(referencedComposite, 0, matchers);
	}
}
