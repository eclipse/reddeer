package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.condition.WaitCondition;
/**
 * Condition is fulfilled when button with text is active
 * @author Vlado Pakan / Len DiMaggio
 *
 */
public class ButtonWithTextIsActive implements WaitCondition {
	
    private Button button;
    
    public ButtonWithTextIsActive(Button button){
    	this.button = button;
 	}

	@Override
	public boolean test() {
			return button.isEnabled();
	}
	
	@Override
	public String description() {
		return "Button with text " + button.getText() + " is active";
	}

}
