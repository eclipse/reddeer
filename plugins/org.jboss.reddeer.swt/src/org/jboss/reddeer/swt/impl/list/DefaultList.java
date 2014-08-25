package org.jboss.reddeer.swt.impl.list;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.List;
import org.jboss.reddeer.swt.matcher.WithLabelMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;


/**
 * Default Label implementation.
 * @author Rastislav Wagner
 *
 */
public class DefaultList extends AbstractList implements List{
	
	/**
	 * List with index 0
	 */
	public DefaultList(){
		this((ReferencedComposite) null);
	}
	
	/**
	 * List with index 0 inside given composite
	 * @param referencedComposite
	 */
	public DefaultList(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * List with given label
	 * @param label of list
	 */
	public DefaultList(String label){
		this(null, label);
	}
	
	/**
	 * List with given label inside given composite
	 * @param referencedComposite
	 * @param label of list
	 */
	public DefaultList(ReferencedComposite referencedComposite, String label){
		this(referencedComposite, 0, new WithLabelMatcher(label));
	}
	
	/**
	 * List that matches given matchers
	 * @param matchers
	 */
	public DefaultList(Matcher<?>... matchers){
		this(null, matchers);
	}
	
	/**
	 * List that matches given matchers inside given composite
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultList(ReferencedComposite referencedComposite, Matcher<?>... matchers){
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * List with given index that matches given matchers
	 * @param index of list
	 * @param matchers
	 */
	public DefaultList(int index, Matcher<?>... matchers){
		this(null, index, matchers);
	}
	
	/**
	 * List with given index that matches given matchers inside given composite
	 * @param referencedComposite
	 * @param index of list
	 * @param matchers
	 */
	public DefaultList(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers){
		super(referencedComposite, index, matchers);
	}
	
	/**
	 * List with given index and label
	 * @param index of text
	 * @deprecated Since 1.0.0. This is not a standard widget constructor.
	 */
	public DefaultList(String label, int index){
		this(null, index,new WithLabelMatcher(label));
	}
	
	/**
	 * List with given index and label inside given composite
	 * @param referencedComposite
	 * @param label
	 * @param index of text
	 * @deprecated Since 1.0.0. This is not a standard widget constructor.
	 */
	public DefaultList(ReferencedComposite referencedComposite, String label, int index){
		this(referencedComposite, index,new WithLabelMatcher(label));
	}

}
