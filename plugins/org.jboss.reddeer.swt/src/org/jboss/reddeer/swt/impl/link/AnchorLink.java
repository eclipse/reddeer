package org.jboss.reddeer.swt.impl.link;

import org.jboss.reddeer.swt.api.Link;
import org.jboss.reddeer.swt.lookup.LinkLookup;
import org.jboss.reddeer.swt.matcher.AnchorLinkTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

public class AnchorLink extends AbstractLink implements Link{
	
	/**
	 * Link with given anchor text
	 * @param text anchor of link
	 */
	public AnchorLink(String text){
		this(null, 0, text);
	}
	
	/**
	 * Link with given anchor text inside given composite
	 * @param referencedComposite
	 * @param text anchor of link
	 */
	public AnchorLink(ReferencedComposite referencedComposite, String text){
		this(referencedComposite, 0, text);
	}
	
	/**
	 * Link with given anchor text and index
	 * @param index of link
	 * @param text anchor of link
	 */
	public AnchorLink(int index, String text){
		this(null, index, text);
	}
	
	/**
	 * Link with given anchor text and index inside given composite
	 * @param referencedComposite
	 * @param index of link
	 * @param text anchor of link
	 */
	public AnchorLink(ReferencedComposite referencedComposite, int index, String text){
		link = LinkLookup.getInstance().getLink(referencedComposite, index, new AnchorLinkTextMatcher(text));
	}

}
