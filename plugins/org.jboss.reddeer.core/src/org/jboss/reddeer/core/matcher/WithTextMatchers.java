package org.jboss.reddeer.core.matcher;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;


/**
 * Class for more comfortable work with multiple {@link WithTextMatcher} matchers.<br/>
 * 
 * Usage example:
 * <code> 
 * WithTextMatchers matchers = new WithTextMatchers("New","Project...");
 * Menu m = new ContextMenu(matchers.getMatchers());
 * </code>
 * 
 * @author Jiri Peterka
 * @author Radoslav Rabara
 */
public class WithTextMatchers {

	private Matcher<String>[] matchers;
	
	/**
	 * Constructs new WithTextMatchers encapsulating more {@link WithTextMatcher} matchers
	 * constructed from texts from specified array of text.
	 * 
	 * @param texts texts for construction multiple {@link WithTextMatcher} matchers. 
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
	 * Constructs new WithTextMatchers encapsulating more {@link WithTextMatcher} matchers
	 * constructed from text matchers from specified array of text matchers.
	 * 
	 * @param m text matchers for construction multiple {@link WithTextMatcher} matchers. 
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
	 * Gets all {@link WithTextMatcher} matchers.
	 * 
	 * @return array of {@link WithTextMatcher} matchers
	 */
	public Matcher<String>[] getMatchers() {
		return matchers;
	}
}
