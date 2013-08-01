package org.jboss.reddeer.swt.impl.combo;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.swt.matcher.ComboLookup;
import org.jboss.reddeer.swt.matcher.LabelMatcher;
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
	  w = ComboLookup.getInstance().getCombo(0, new LabelMatcher(label));
	}
	/**
     * Searches for combo with index 
     * @param index
     */
	public DefaultCombo(int index) {
	  w = ComboLookup.getInstance().getCombo(index);
	}
	/**
     * Searches for combo matching all matchers  
     * @param matchers
     */
	@SuppressWarnings("rawtypes")
	public DefaultCombo(Matcher... matchers) {
	  w = ComboLookup.getInstance().getCombo(0, matchers);
	}
}
