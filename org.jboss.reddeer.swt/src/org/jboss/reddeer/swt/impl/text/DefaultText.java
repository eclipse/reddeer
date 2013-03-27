package org.jboss.reddeer.swt.impl.text;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.matcher.GroupMatcher;
import org.jboss.reddeer.swt.matcher.TextLookup;
import org.jboss.reddeer.swt.matcher.TextMatcher;

/**
 * Default Text implementation. Most standard Text implementation
 * @author Jiri Peterka
 *
 */
public class DefaultText extends AbstractText implements Text {

	
	private org.eclipse.swt.widgets.Text w;

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
	
	/**
	 * Set Text widget's text
	 */
	@Override
	public void setText(final String text) {
		WidgetHandler.getInstance().setText(w, text);
	}
	
	/**
	 * Get Text widgets's text
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(w);
	}

	/**
	 * Get Text widget's tooltip
	 */
	@Override
	public String getToolTipText() {
		return WidgetHandler.getInstance().getToolTipText(w);
	}
}
