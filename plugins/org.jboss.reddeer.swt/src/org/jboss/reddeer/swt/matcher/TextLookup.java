package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Text;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;

/**
 * Text lookup containing lookup routines for Text widget type
 * @author Jiri Peterka
 *
 */
public class TextLookup {

	private static TextLookup instance = null;
	
	private TextLookup() {
	}
	
	/**
	 * Creates and returns instance of Text Lookup
	 * @return TextLookup instance
	 */
	public static TextLookup getInstance() {
		if (instance == null) instance = new TextLookup();
		return instance;
	}
	
	/**
	 * Return Text instance
	 * @param matcher
	 * @return Text Widget matching criteria
	 */
	@SuppressWarnings({ "rawtypes" })
	public Text getText(int index, Matcher... matchers) {
		return (Text)WidgetLookup.getInstance().activeWidget(Text.class, index, matchers);
	}
}
