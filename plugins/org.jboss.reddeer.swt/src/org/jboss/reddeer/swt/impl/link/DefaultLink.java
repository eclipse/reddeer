package org.jboss.reddeer.swt.impl.link;

import org.jboss.reddeer.swt.matcher.LinkLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatcher;

public class DefaultLink extends AbstractLink{
	
	/**
	 * Link with index 0
	 */
	public DefaultLink(){
		link = LinkLookup.getInstance().getLink(0);
	}
	/**
	 * Link with given index
	 * @param index of link
	 */
	public DefaultLink(int index){
		link = LinkLookup.getInstance().getLink(index);
	}
	/**
	 * Link with given text
	 * @param text of link
	 */
	public DefaultLink(String text){
		link = LinkLookup.getInstance().getLink(0, new WithMnemonicMatcher(text));
	}

}
