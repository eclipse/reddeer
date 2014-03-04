package org.jboss.reddeer.swt.matcher;

import org.hamcrest.Matcher;
 
/**
 * Class for more comfortable matchers construction for further usage<br/>
 * 
 * Usage example:
 * <code> 
 * RegexMatchers m = new WithMnemonicMatchers("New","Project...");
 * Menu m = new ContextMenu(m.getMatchers());
 * </code>
 * 
 * @author Vlado Pakan
 * @author Radoslav Rabara
 */
public class WithMnemonicTextMatchers {

	private Matcher<String>[] matchers;

	/**
	 * Constructs holder for array of {@link WithMnemonicTextMatcher}
	 * created from strings <var>texts</var>
	 * 
	 * @param texts
	 *            texts that will be passed to
	 *            {@link WithMnemonicTextMatcher#WithMnemonicTextMatcher(String)}
	 */
	public WithMnemonicTextMatchers(String... texts) {
		if(texts == null)
			throw new NullPointerException("texts");
		matchers = new WithMnemonicTextMatcher[texts.length];
		for (int i = 0; i < texts.length; i++) {
			matchers[i] = new WithMnemonicTextMatcher(texts[i]);		
		}
	
	}
	
	/**
	 * Constructs holder for array of {@link WithMnemonicTextMatcher}
	 * created from {@link Matcher<String>} <var>m</var>
	 * 
	 * @param m
	 *            texts that will be passed to
	 *            {@link WithMnemonicTextMatcher#WithMnemonicTextMatcher(String)}
	 */
	public WithMnemonicTextMatchers(Matcher<String>... m) {
		if(m == null)
			throw new NullPointerException("m");
		matchers = new WithMnemonicTextMatcher[m.length];
		for (int i = 0; i < m.length; i++) {
			matchers[i] = new WithMnemonicTextMatcher(m[i]);		
		}
	
	}
	
	/**
	 * Get all matchers
	 * @return returns all matchers
	 */
	public Matcher<String>[] getMatchers() {
		return matchers;
	}
}
