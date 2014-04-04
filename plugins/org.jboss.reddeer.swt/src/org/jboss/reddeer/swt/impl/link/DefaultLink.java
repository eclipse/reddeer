package org.jboss.reddeer.swt.impl.link;

import org.jboss.reddeer.swt.lookup.LinkLookup;
import org.jboss.reddeer.swt.matcher.LinkTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

public class DefaultLink extends AbstractLink{
	
	/**
	 * Link with index 0
	 */
	public DefaultLink(){
		this(null,0,null);
	}
	
	/**
	 * Link with index 0 inside given composite
	 * @param referencedComposite
	 */
	public DefaultLink(ReferencedComposite referencedComposite){
		this(referencedComposite, 0, null);
	}
	
	/**
	 * Link with given index
	 * @param index of link
	 */
	public DefaultLink(int index){
		this(null,index,null);
	}
	
	/**
	 * Link with given index inside given composite
	 * @param referencedComposite
	 * @param index of link
	 */
	public DefaultLink(ReferencedComposite referencedComposite, int index){
		this(referencedComposite,index, null);
	}
	
	/**
	 * Link with given text
	 * @param text of link
	 */
	public DefaultLink(String text){
		this(null,0,text);
	}
	
	/**
	 * Link with given text inside given composite
	 * @param referencedComposite
	 * @param text of link
	 */
	public DefaultLink(ReferencedComposite referencedComposite, String text){
		this(referencedComposite, 0, text);
	}
	
	/**
	 * Link with given text and index inside given composite
	 * @param referencedComposite
	 * @param index of link
	 * @param text of link
	 */
	public DefaultLink(ReferencedComposite referencedComposite, int index, String text){
		if(text != null){
			link = LinkLookup.getInstance().getLink(referencedComposite, index, new LinkTextMatcher(text));
		} else {
			link = LinkLookup.getInstance().getLink(referencedComposite, index);
		}
	}
	
	/**
	 * Link with given text and index
	 * @param index of link
	 * @param text of link
	 */
	public DefaultLink(int index, String text){
		this(null,index,text);
	}

}
