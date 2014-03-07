package org.jboss.reddeer.swt.impl.link;

import org.jboss.reddeer.swt.lookup.LinkLookup;
import org.jboss.reddeer.swt.matcher.LinkTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

public class DefaultLink extends AbstractLink{
	
	/**
	 * Link with index 0
	 */
	public DefaultLink(){
		link = LinkLookup.getInstance().getLink(null, 0);
	}
	
	/**
	 * Link with index 0 inside given composite
	 * @param referencedComposite
	 */
	public DefaultLink(ReferencedComposite referencedComposite){
		link = LinkLookup.getInstance().getLink(referencedComposite, 0);
	}
	
	/**
	 * Link with given index
	 * @param index of link
	 */
	public DefaultLink(int index){
		link = LinkLookup.getInstance().getLink(null, index);
	}
	
	/**
	 * Link with given index inside given composite
	 * @param referencedComposite
	 * @param index of link
	 */
	public DefaultLink(ReferencedComposite referencedComposite, int index){
		link = LinkLookup.getInstance().getLink(referencedComposite, index);
	}
	
	/**
	 * Link with given text
	 * @param text of link
	 */
	public DefaultLink(String text){
		link = LinkLookup.getInstance().getLink(null, 0, new LinkTextMatcher(text));
	}
	
	/**
	 * Link with given text inside given composite
	 * @param referencedComposite
	 * @param text of link
	 */
	public DefaultLink(ReferencedComposite referencedComposite, String text){
		link = LinkLookup.getInstance().getLink(referencedComposite, 0, new LinkTextMatcher(text));
	}
	
	/**
	 * Link with given text and index inside given composite
	 * @param referencedComposite
	 * @param index of link
	 * @param text of link
	 */
	public DefaultLink(ReferencedComposite referencedComposite, int index, String text){
		link = LinkLookup.getInstance().getLink(referencedComposite, index, new LinkTextMatcher(text));
	}
	
	/**
	 * Link with given text and index
	 * @param index of link
	 * @param text of link
	 */
	public DefaultLink(int index, String text){
		link = LinkLookup.getInstance().getLink(null, index, new LinkTextMatcher(text));
	}

}
