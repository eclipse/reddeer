package org.jboss.reddeer.swt.impl.button;

import org.hamcrest.Matcher;
import org.jboss.reddeeer.swt.regex.RegexSeq;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.util.Bot;

/**
 * PushButton is simple button implementation that can be pushed
 * @author Jiri Peterka
 *
 */
public class PushButton extends AbstractButton implements Button {

	/**
	 * Push button with given text
	 * @param text
	 */
	public PushButton(String text) {
		button = Bot.get().button(text);
	}

	/**
	 * Push button with given regex pattern
	 * @param text
	 */
	public PushButton(RegexSeq seq) {
		
	}

	/**
	 * Push button with given matcher(s)
	 * @param text
	 */
	public PushButton(Matcher... matchers) {
		
	}

	/**
	 * 
	 * @param timeout
	 * @param matchers
	 */
	public PushButton(int timeout, Matcher matchers) {
		
	}
	
	public PushButton(Condition condition, String text) {
		
	}
	
	
	public PushButton(Condition c, Matcher... matchers) {
		
	}

	
	
}
