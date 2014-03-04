package org.jboss.reddeer.swt.matcher;

import org.hamcrest.Matcher;
 
/**
 * Class for more comfortable matchers construction for further usage
 * 
 * Usage example: 
 * RegexMatchers m = new WithMnemonicMatchers("New","Project...");
 * Menu m = new ContextMenu(m.getMatchers());
 * 
 * @author Vlado Pakan
 * 
 * @deprecated in 0.5, use {@link WithMnemonicTextMatcher}
 */
public class WithMnemonicMatchers {

	private Matcher<String>[] matchers;

	public WithMnemonicMatchers(String... texts) {
	
		matchers = new WithMnemonicMatcher[texts.length];
		for (int i = 0; i < texts.length; i++) {
			matchers[i] = new WithMnemonicMatcher(texts[i]);		
		}
	
	}
	
	public Matcher<String>[] getMatchers() {
		return matchers;
	}
}
