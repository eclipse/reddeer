package org.jboss.reddeer.swt.impl.button;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.util.Bot;

public class RadioButton implements Button {

	private static final Logger log = Logger.getLogger(RadioButton.class);
	
	private SWTBotRadio radioButton;
	
	public RadioButton() {
		radioButton = Bot.get().radio();
	}

	public RadioButton(String text) {
		radioButton = Bot.get().radio(text);
	}
	
	@Override
	public void click() {
		log.info("Clicking radio button " + getText());
		radioButton.click();
	}

	@Override
	public String getText() {
		return radioButton.getText();
	}

	@Override
	public boolean isEnabled() {
		return radioButton.isEnabled();
	}

	public boolean isSelected() {
		return radioButton.isSelected();
	}
}
