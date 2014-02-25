package org.jboss.reddeer.swt.impl.combo;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.swt.lookup.ComboLookup;
import org.jboss.reddeer.swt.matcher.TextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Default Combo implementation. Most standard Combo implementation
 * @author Rastislav Wagner
 *
 */
public class DefaultCombo extends AbstractCombo implements Combo{
	
	/**
	 * Default constructor which looks for combo with index 0
	 */
	public DefaultCombo(){
		this(0);
	}
	
	/**
	 * Finds combo with given index
	 * @param index of combo
	 */
	public DefaultCombo(int index){
		this(null, index);
	}
	
	/**
	 * Finds combo with given text written inside
	 * @param text which is written in combo
	 */
	public DefaultCombo(String text){
		this(null,text);
	}
	
	/**
	 * Finds combo inside given referenced composite
	 * @param ref composite inside which combo should be looked for
	 */
	public DefaultCombo(ReferencedComposite ref){
		this(ref,0);
	}
	
	/**
	 * Finds combo inside given referenced composite with given index
	 * @param ref composite inside which combo should be looked for
	 * @param index of combo
	 */
	public DefaultCombo(ReferencedComposite ref, int index){
		swtCombo = ComboLookup.getInstance().getCombo(ref , index);
	}
	
	/**
	 * Finds combo inside given referenced composite with given text
	 * @param ref composite inside which combo should be looked for
	 * @param text which is written in combo
	 */
	public DefaultCombo(ReferencedComposite ref, String text){
		swtCombo = ComboLookup.getInstance().getCombo(ref , 0, new TextMatcher(text));
	}
	
	/**
	 * Finds combo matching to given matchers
	 * @param matchers to match combo
	 */
	@SuppressWarnings("rawtypes")
	public DefaultCombo(Matcher... matchers){
		this(null, matchers);
	}
	
	/**
	 * Finds combo inside given referenced composite which is matching given matchers
	 * @param ref composite inside which combo should be looked for
	 * @param matchers matchers to match combo
	 */
	@SuppressWarnings("rawtypes")
	public DefaultCombo(ReferencedComposite ref, Matcher... matchers){
		swtCombo = ComboLookup.getInstance().getCombo(ref , 0, matchers);
	}
	
	

}
