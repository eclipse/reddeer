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

	public DefaultCombo(String label) {
		w = ComboLookup.getInstance().getCombo(0, new LabelMatcher(label));
	}
	
	public DefaultCombo(int index) {
		w = ComboLookup.getInstance().getCombo(index);
	}
	
	@SuppressWarnings("rawtypes")
	public DefaultCombo(Matcher... matchers) {
		w = ComboLookup.getInstance().getCombo(0, matchers);
	}

}
