package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * PushButton is simple button implementation that can be pushed
 * @author Jiri Peterka
 *
 */
public class PushButton extends AbstractButton implements Button {

	/**
	 * Push button with index 0
	 */
	public PushButton(){
		this((ReferencedComposite) null);
	}
	
	/**
	 * Push button with index 0 inside given composite
	 * @param referencedComposite
	 */
	public PushButton(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * Push button with given text
	 * @param text
	 */
	public PushButton(String text) {
		this(null, text);
	}
	
	/**
	 * Push button with given text inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public PushButton(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new WithMnemonicTextMatcher(text));
	}
	
	/**
	 * Push button that matches given matchers
	 * @param matchers
	 */
	public PushButton(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Push button that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public PushButton(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Push button with given index that matches given matchers
	 * @param index
	 * @param matchers
	 */
	public PushButton(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Push button with given index inside given composite that matches given matchers
	 * @param referencedComposite
	 * @param index
	 * @param matchers
	 */
	public PushButton(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, SWT.PUSH, matchers);
	}
	
	/**
	 * Push Button with given index and text
	 * @param index
	 * @param text
	 * @deprecated Since 1.0.0. This is not a standard widget constructor.
	 */
	public PushButton (int index , String text){
		super(null, index,text,SWT.PUSH);
	}
	
	/**
	 * Push Button with given index and text inside given composite
	 * @param referencedComposite
	 * @param index
	 * @param text
	 * @deprecated Since 1.0.0. This is not a standard widget constructor.
	 */
	public PushButton (ReferencedComposite referencedComposite, int index , String text){
		super(referencedComposite, index,text,SWT.PUSH);
	}
}
