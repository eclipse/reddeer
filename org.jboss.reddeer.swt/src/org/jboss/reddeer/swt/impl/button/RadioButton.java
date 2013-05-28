package org.jboss.reddeer.swt.impl.button;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;
import org.jboss.reddeer.swt.matcher.ButtonLookup;
import org.jboss.reddeer.swt.matcher.StyleMatcher;
import org.jboss.reddeer.swt.matcher.TextMatcher;

/**
 * RadioButton is button implementation that can be selected
 * @author jjankovi
 *
 */
public class RadioButton implements Button {

	private static final Logger log = Logger.getLogger(RadioButton.class);
	
	private org.eclipse.swt.widgets.Button radioButton;
	
	/**
	 * Creates Radio button
	 */
	public RadioButton() {
		this(0);
	}
	
	/**
	 * Creates Radio button with given text
	 * @param text
	 */
	public RadioButton(String text) {
		this(0, text);
	}
	/**
	 * Creates Radio button with given index
	 * @param text
	 */
	public RadioButton(int index) {
		this(index, null);
	}
	
	/**
	 * Creates Radio button with given index and label
	 * @param index of button
	 * @param label of button
	 */
	public RadioButton(int index, String text) {
		if (text != null && !text.isEmpty()) {
			radioButton = ButtonLookup.getInstance().getButton(
					index, new TextMatcher(text), new StyleMatcher(SWT.RADIO));
		} else {
			radioButton = ButtonLookup.getInstance().getButton(
					index, new StyleMatcher(SWT.RADIO));
		}
	}
	
	@Override
	public void click() {
		log.info("Clicking radio button " + getText());
		WidgetHandler.getInstance().click(radioButton);
	}

	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(radioButton);
	}

	@Override
	public boolean isEnabled() {
		return WidgetLookup.getInstance().isEnabled(radioButton);
	}

	public boolean isSelected() {
		return WidgetHandler.getInstance().isSelected(radioButton);
	}
}
