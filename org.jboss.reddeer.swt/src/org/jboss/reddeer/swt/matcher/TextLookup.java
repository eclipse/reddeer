package org.jboss.reddeer.swt.matcher;

import java.util.List;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.SWTLayerException;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Text getText(int index, Matcher... matchers)
	{
		Text text = null;
		ClassMatcher cm = new ClassMatcher(Text.class);
		Matcher[] allMatchers = MatcherBuilder.getInstance().addMatcher(matchers, cm);
		AndMatcher am  = new AndMatcher(allMatchers);
		
		Widget parentWidget = WidgetLookup.getInstance().getActiveWidgetParentControl();
		List<Widget> textWidgets = WidgetLookup.getInstance().findControls(parentWidget, am, true);
		
		if (textWidgets.size() > index)
			text  = (org.eclipse.swt.widgets.Text) textWidgets.get(index);
		else
			throw new SWTLayerException("Text widget not found");
		
		if (text == null) throw new SWTLayerException("Text widget is null");
		return text;
	}
}
