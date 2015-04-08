package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.swt.api.Button;

/**
 * Condition is met when button with specified text is active.
 * 
 * @author Vlado Pakan
 * @author Len DiMaggio
 *
 */
public class ButtonWithTextIsActive implements WaitCondition {

	private Button button;

	/**
	 * Constructs ButtonWithTextIsActive wait condition.
	 * Condition is met when specified button is active.
	 * 
	 * @param button button to check
	 */
	public ButtonWithTextIsActive(Button button) {
		this.button = button;
	}

	@Override
	public boolean test() {
		return button.isEnabled();
	}

	@Override
	public String description() {
		return "button with text " + button.getText() + " is active";
	}

}
