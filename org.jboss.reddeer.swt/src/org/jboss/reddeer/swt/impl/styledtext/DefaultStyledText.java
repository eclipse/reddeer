package org.jboss.reddeer.swt.impl.styledtext;

import org.apache.log4j.Logger;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.StyledText;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.StyledTextLookup;
import org.jboss.reddeer.swt.matcher.GroupMatcher;
import org.jboss.reddeer.swt.matcher.TextMatcher;

/**
 * Default class for representing StyledText
 * 
 * @author rhopp
 * 
 */
public class DefaultStyledText implements StyledText {

	protected org.eclipse.swt.custom.StyledText styledText;
	protected final Logger log = Logger.getLogger(this.getClass());

	/**
	 * StyledText
	 */
	public DefaultStyledText() {
		styledText = StyledTextLookup.getInstance().getStyledText(0);
	}

	/**
	 * StyledText on given index
	 * 
	 * @param index
	 */

	public DefaultStyledText(int index) {
		styledText = StyledTextLookup.getInstance().getStyledText(index);
	}

	/**
	 * StyledText on given index and in given goup
	 * 
	 * @param inGroup
	 * @param index
	 */

	public DefaultStyledText(String inGroup, int index) {
		styledText = StyledTextLookup.getInstance().getStyledText(index,
				new GroupMatcher(inGroup));
	}

	/**
	 * StyledText with given text
	 * 
	 * @param text
	 */

	public DefaultStyledText(String text) {
		styledText = StyledTextLookup.getInstance().getStyledText(0, new TextMatcher(text));
	}

	/**
	 * StyledText matching given matchers
	 * 
	 * @param matchers
	 */

	@SuppressWarnings("rawtypes")
	public DefaultStyledText(Matcher... matchers) {
		styledText = StyledTextLookup.getInstance().getStyledText(0, matchers);
	}

	/**
	 * 
	 * @return text of this StyledText
	 */
	@Override
	public String getText() {
		String text = WidgetHandler.getInstance().getText(styledText);
		return text;
	}

	/**
	 * Sets text of this StyledText
	 */
	@Override
	public void setText(String text) {
		log.info("Text set to: " + text);
		WidgetHandler.getInstance().setText(styledText, text);
	}

	/**
	 * 
	 * @return Tooltip text of this StyledText
	 */

	@Override
	public String getToolTipText() {
		String tooltipText = WidgetHandler.getInstance().getToolTipText(
				styledText);
		return tooltipText;
	}
}
