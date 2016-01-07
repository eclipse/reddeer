/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.core.matcher;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
 
/**
 * Class for more comfortable work with multiple {@link WithMnemonicTextMatcher} matchers.<br/>
 * 
 * Usage example:
 * <code> 
 * WithMnemonicMatchers matchers = new WithMnemonicMatchers("New","Project...");
 * Menu m = new ContextMenu(matchers.getMatchers());
 * </code>
 * 
 * @author Vlado Pakan
 * @author Radoslav Rabara
 */
public class WithMnemonicTextMatchers {
	
	private Matcher<String>[] matchers;

	/**
	 * Constructs new WithMnemonicTextMatchers encapsulating more {@link WithMnemonicTextMatcher} matchers
	 * constructed from texts from specified array of text.
	 * 
	 * @param texts array of text for construction multiple WithMnemonicTextMatcher matchers
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
	 * Constructs new WithMnemonicTextMatchers encapsulating more {@link WithMnemonicTextMatcher} matchers
	 * constructed from text matchers from specified array of text matchers.
	 * 
	 * @param m array of text matchers for construction multiple WithMnemonicTextMatcher matchers
	 */
	@SuppressWarnings("unchecked")
	public WithMnemonicTextMatchers(Matcher<String>... m) {
		if(m == null)
			throw new NullPointerException("m");
		matchers = new WithMnemonicTextMatcher[m.length];
		for (int i = 0; i < m.length; i++) {
			matchers[i] = new WithMnemonicTextMatcher(m[i]);		
		}
	
	}
	
	/**
	 * Gets all {@link WithMnemonicTextMatcher} matchers.
	 * 
	 * @return array of {@link WithMnemonicTextMatcher} matchers
	 */
	public Matcher<String>[] getMatchers() {
		return matchers;
	}
}
