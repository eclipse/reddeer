package org.jboss.reddeer.swt.impl.styledtext;

import org.hamcrest.Matcher;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.StyledText;
import org.jboss.reddeer.swt.handler.StyledTextHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.StyledTextLookup;
import org.jboss.reddeer.swt.matcher.TextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

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
		styledText = StyledTextLookup.getInstance().getStyledText(null, 0);
	}
	
	/**
	 * StyledText inside given composite
	 * @param referencedComposite
	 */
	public DefaultStyledText(ReferencedComposite referencedComposite) {
		styledText = StyledTextLookup.getInstance().getStyledText(referencedComposite, 0);
	}

	/**
	 * StyledText on given index
	 * 
	 * @param index
	 */
	public DefaultStyledText(int index) {
		styledText = StyledTextLookup.getInstance().getStyledText(null, index);
	}
	
	/**
	 * StyledText on given index inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultStyledText(ReferencedComposite referencedComposite, int index) {
		styledText = StyledTextLookup.getInstance().getStyledText(referencedComposite, index);
	}

	/**
	 * StyledText with given text
	 * 
	 * @param text
	 */
	public DefaultStyledText(String text) {
		styledText = StyledTextLookup.getInstance().getStyledText(null, 0, new TextMatcher(text));
	}
	
	/**
	 * StyledText with given text inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultStyledText(ReferencedComposite referencedComposite, String text) {
		styledText = StyledTextLookup.getInstance().getStyledText(referencedComposite, 0, new TextMatcher(text));
	}

	/**
	 * StyledText matching given matchers
	 * 
	 * @param matchers
	 */
	@SuppressWarnings("rawtypes")
	public DefaultStyledText(Matcher... matchers) {
		styledText = StyledTextLookup.getInstance().getStyledText(null, 0, matchers);
	}
	
	/**
	 * StyledText matching given matchers
	 * 
	 * @param matchers
	 */
	@SuppressWarnings("rawtypes")
	public DefaultStyledText(ReferencedComposite referencedComposite, Matcher... matchers) {
		styledText = StyledTextLookup.getInstance().getStyledText(referencedComposite, 0, matchers);
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

	
	/**
	 * insert text into styled text content
	 */
	@Override
	public void insertText(final int line, final int column, final String text) {
		StyledTextHandler.getInstance().insertText(styledText, line,column,text);
	}
	
	public org.eclipse.swt.custom.StyledText getSWTWidget(){
		return styledText;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(styledText);
	}
}
