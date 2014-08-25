package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * Matcher which matches widgets with given tooltip
 * 
 * @author rhopp
 *
 */

public class WithTooltipTextMatcher extends AbstractWidgetWithTextMatcher {

	private Matcher<String> matcher;
	
	/**
	 * Default constructor for matching widgets with ToolTip text
	 * 
	 * @param text
	 */
	
	public WithTooltipTextMatcher(String text) {
		this(new IsEqual<String>(text));
	}
	
	
	/**
	 * Constructor for matching widgets ToolTip text using String matcher
	 * 
	 * @param matcher
	 */
	
	public WithTooltipTextMatcher(Matcher<String> matcher) {
		if (matcher == null)
			throw new NullPointerException("matcher is null");
		this.matcher = matcher;
	}
	
	@Override
	protected String extractWidgetText(Widget widget) {
		try{
			return WidgetHandler.getInstance().getToolTipText(widget);
		} catch (SWTLayerException ex) {
			return null;
		}
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("with tooltip ").appendDescriptionOf(matcher);

	}

	@Override
	protected boolean matches(String text) {
		return matcher.matches(text.replaceAll("&", "").split("\t")[0]);
	}
	
	@Override
	public String toString() {
		return "Matcher matching widget which tooltip matches: "+matcher.toString();
	}
}
