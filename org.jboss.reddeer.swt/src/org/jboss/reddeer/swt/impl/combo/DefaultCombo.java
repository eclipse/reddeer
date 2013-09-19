package org.jboss.reddeer.swt.impl.combo;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.swt.lookup.ComboLookup;
import org.jboss.reddeer.swt.matcher.LabelMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
/**
 * Combo with label implementation
 * @author Vlado Pakan
 *
 */
public class DefaultCombo extends AbstractCombo implements Combo {
   
	/**
     * Searches for combo with label 
     * @param label
     */
	public DefaultCombo(String label) {
	  swtCombo = ComboLookup.getInstance().getCombo(null, 0, new LabelMatcher(label));
	}
	
	/**
     * Searches for combo with label inside given composite
	 * @param referencedComposite
     * @param label
     */
	public DefaultCombo(ReferencedComposite referencedComposite, String label) {
	  swtCombo = ComboLookup.getInstance().getCombo(referencedComposite, 0, new LabelMatcher(label));
	}
	
	/**
     * Searches for combo with index 
     * @param index
     */
	public DefaultCombo(int index) {
	  swtCombo = ComboLookup.getInstance().getCombo(null, index);
	}
	
	/**
     * Searches for combo with index inside given composite
	 * @param referencedComposite
     * @param index
     */
	public DefaultCombo(ReferencedComposite referencedComposite, int index) {
	  swtCombo = ComboLookup.getInstance().getCombo(referencedComposite, index);
	}
	
	/**
     * Searches for combo matching all matchers  
     * @param matchers
     */
	@SuppressWarnings("rawtypes")
	public DefaultCombo(Matcher... matchers) {
	  swtCombo = ComboLookup.getInstance().getCombo(null, 0, matchers);
	}
	
	/**
     * Searches for combo matching all matchers inside given composite
	 * @param referencedComposite
     * @param matchers
     */
	@SuppressWarnings("rawtypes")
	public DefaultCombo(ReferencedComposite referencedComposite, Matcher... matchers) {
	  swtCombo = ComboLookup.getInstance().getCombo(referencedComposite, 0, matchers);
	}
}
