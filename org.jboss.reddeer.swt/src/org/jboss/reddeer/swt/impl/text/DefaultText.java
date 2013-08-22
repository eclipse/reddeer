package org.jboss.reddeer.swt.impl.text;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.lookup.TextLookup;
import org.jboss.reddeer.swt.matcher.GroupMatcher;
import org.jboss.reddeer.swt.matcher.TextMatcher;

/**
 * Default Text implementation. Most standard Text implementation
 * @author Jiri Peterka
 *
 */
public class DefaultText extends AbstractText implements Text {
	
	/**
	 * Text with given index in given Group
	 * @param index of text
	 * @param inGroup in group
	 */
	public DefaultText(String inGroup, int index){
		w = TextLookup.getInstance().getText(index, new GroupMatcher(inGroup));
	}
		
	/**
	 * Text with given text in given Group
	 * @param text of text
	 * @param inGroup in group
	 */
	public DefaultText(String inGroup, String text){
		w = TextLookup.getInstance().getText(0, new GroupMatcher(inGroup), new TextMatcher(text));
	}
	
	/**
	 * Text with given index
	 * @param index
	 */
	public DefaultText(int index){
		w = TextLookup.getInstance().getText(index);
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * Text with given matchers
	 * @param matchers
	 */
	public DefaultText(Matcher... matchers){
		w = TextLookup.getInstance().getText(0, matchers);
	}
	
	/**
	 * Text with text value
	 * @param title
	 */
	public DefaultText(String title) {
		w = TextLookup.getInstance().getText(0, new TextMatcher(title));
	}
}
