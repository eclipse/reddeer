package org.jboss.reddeer.swt.impl.label;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Bot;

public class DefaultLabel extends AbstractLabel {

	/**
	 * Create DefaultLabel
	 */
	public DefaultLabel() {
		try {
			label = Bot.get().label();
		} catch (WidgetNotFoundException e){
			throw new SWTLayerException("No label found");
		}
	}

	/**
	 * Create DefaultLabel matching given text
	 * @param text
	 */
	public DefaultLabel(String text) {
		try {
			label = Bot.get().label(text);
		} catch (WidgetNotFoundException e){
			throw new SWTLayerException("No label with text '" + text + "' found");
		}
	}
	
	/**
	 * Create DefaultLabel matching given index
	 * @param index
	 */
	public DefaultLabel(int index) {
		try {
			label = Bot.get().label(index);
		} catch (WidgetNotFoundException e){
			throw new SWTLayerException("Label with index " + index + " was not found");
		}
	}
	
	/**
	 * Create DefaultLabel matching given text and index
	 * @param text
	 * @param index
	 */
	public DefaultLabel(String text, int index) {
		try {
			label = Bot.get().label(text, index);
		} catch (WidgetNotFoundException e){
			throw new SWTLayerException("Label with text '" + text + "' and index " 
					+ index + " was not found");
		}
	}
}
