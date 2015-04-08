package org.jboss.reddeer.core.matcher;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;


/**
 * Class for more comfortable matchers construction for further usage.<br/>
 * 
 * Usage example:
 * <code> 
 * RegexMatchers m = new TextMatchers("New","Project...");
 * Menu m = new ContextMenu(m.getMatchers());
 * </code>
 * 
 * @author Jiri Peterka
 * @author Radoslav Rabara
 */
public class WithTextMatchers {

	private Matcher<String>[] matchers;
	
	/**
	 * Constructs holder for array of {@link WithTextMatcher} created from
	 * strings <var>texts</var>.
	 * 
	 * @param texts
	 *            texts that will be passed to
	 *            {@link WithTextMatcher#WithTextMatcher(String)}
	 */
	public WithTextMatchers(String... texts) {
		if(texts == null)
			throw new NullPointerException("texts");
		matchers = new WithTextMatcher[texts.length];
		for (int i = 0; i < texts.length; i++) {
			matchers[i] = new WithTextMatcher(texts[i]);		
		}
	
	}
	
	/**
	 * Constructs holder for array of {@link WithTextMatcher} created from
	 * {@link Matcher<String>} <var>m</var>.
	 * 
	 * @param m
	 *            matchers that will be passed to
	 *            {@link WithTextMatcher#WithTextMatcher(Matcher)}
	 */
	@SuppressWarnings("unchecked")
	public WithTextMatchers(Matcher<String>... m) {
		if(m == null)
			throw new NullPointerException("m");
		matchers = new WithTextMatcher[m.length];
		for (int i = 0; i < m.length; i++) {
			matchers[i] = new WithTextMatcher(m[i]);		
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
