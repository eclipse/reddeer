package org.jboss.reddeer.swt.impl.combo;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Default Combo implementation. Most standard Combo implementation
 * @author Rastislav Wagner, Lucia Jelinkova
 *
 */
public class DefaultCombo extends AbstractCombo implements Combo{
	
	/**
	 * Default constructor which looks for combo with index 0
	 */
	public DefaultCombo(){
		this((ReferencedComposite) null);
	}
	
	/**
	 * Finds combo inside given referenced composite
	 * @param ref composite inside which combo should be looked for
	 */
	public DefaultCombo(ReferencedComposite ref){
		this(ref,0);
	}
	
	/**
	 * Finds combo with given text written inside
	 * @param text which is written in combo
	 */
	public DefaultCombo(String text){
		this(null,text);
	}
	
	/**
	 * Finds combo inside given referenced composite with given text
	 * @param ref composite inside which combo should be looked for
	 * @param text which is written in combo
	 */
	public DefaultCombo(ReferencedComposite ref, String text){
		this(ref, 0, new WithTextMatcher(text));
	}

	/**
	 * Finds combo matching to given matchers
	 * @param matchers to match combo
	 */
	public DefaultCombo(Matcher<?>... matchers){
		this(null, matchers);
	}
	
	/**
	 * Finds combo inside given referenced composite which is matching given matchers
	 * @param ref composite inside which combo should be looked for
	 * @param matchers matchers to match combo
	 */
	public DefaultCombo(ReferencedComposite ref, Matcher<?>... matchers){
		this(ref, 0, matchers);
	}
	
	/**
	 * Finds combo with given index
	 * @param index of combo
	 * @param matchers
	 */
	public DefaultCombo(int index, Matcher<?>... matchers){
		this(null, index, matchers);
	}
	
	/**
	 * Finds combo inside given referenced composite with given index
	 * @param ref composite inside which combo should be looked for
	 * @param index of combo
	 * @param matchers
	 */
	public DefaultCombo(ReferencedComposite ref, int index, Matcher<?>... matchers){
		super(ref, index, matchers);
	}
}
