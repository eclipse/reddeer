package org.jboss.reddeer.swt.impl.progressbar;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithLabelMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

public class DefaultProgressBar extends AbstractProgressBar {

	/**
	 * ProgressBar with index 0.
	 */
	public DefaultProgressBar(){
		this((ReferencedComposite) null);
	}
	
	/**
	 * ProgressBar with index 0 inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultProgressBar(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * ProgressBar with given label.
	 *
	 * @param label of list
	 */
	public DefaultProgressBar(String label){
		this(null, label);
	}
	
	/**
	 * ProgressBar with given label inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param label of list
	 */
	public DefaultProgressBar(ReferencedComposite referencedComposite, String label){
		this(referencedComposite, 0, new WithLabelMatcher(label));
	}
	
	/**
	 * ProgressBar that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultProgressBar(Matcher<?>... matchers){
		this(null, matchers);
	}
	
	/**
	 * ProgressBar that matches given matchers inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultProgressBar(ReferencedComposite referencedComposite, Matcher<?>... matchers){
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * ProgressBar with given index that matches given matchers.
	 *
	 * @param index of list
	 * @param matchers the matchers
	 */
	public DefaultProgressBar(int index, Matcher<?>... matchers){
		this(null, index, matchers);
	}
	
	/**
	 * ProgressBar with given index that matches given matchers inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index of list
	 * @param matchers the matchers
	 */
	public DefaultProgressBar(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers){
		super(referencedComposite, index, matchers);
	}
}
