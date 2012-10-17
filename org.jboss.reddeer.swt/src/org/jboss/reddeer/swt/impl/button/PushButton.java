package org.jboss.reddeer.swt.impl.button;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.regex.RegexSeq;
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
	 * Push button with given index
	 * @param index
	 */
	public PushButton(int index) {
		button = Bot.get().button(index);
	}
	
	/**
	 * Push button with given index in given Group
	 * @param index of button
	 * @param inGroup in group
	 */
	public PushButton(String inGroup, int index){
		button = Bot.get().buttonInGroup(inGroup, index);
	}
	/**
	 * Radio button with given text in given Group
	 * @param text of button
	 * @param inGroup in group
	 */
	public PushButton(String inGroup, String text){
		button = Bot.get().buttonInGroup(inGroup, text);
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
	public PushButton(Matcher<?>... matchers) {
		
	}

	/**
	 * 
	 * @param timeout
	 * @param matchers
	 */
	public PushButton(int timeout, Matcher<?> matchers) {
		
	}
	
	public PushButton(Condition condition, String text) {
		
	}
	
	
	public PushButton(Condition c, Matcher<?>... matchers) {
		
	}

	
}
