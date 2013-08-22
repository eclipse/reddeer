package org.jboss.reddeer.swt.condition;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.lookup.ButtonLookup;
import org.jboss.reddeer.swt.matcher.StyleMatcher;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatcher;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
/**
 * Condition is fulfilled when button with text is active
 * @author Vlado Pakan / Len DiMaggio
 *
 */
public class ButtonWithTextIsActive implements WaitCondition {
	
    private String text;
    
    public ButtonWithTextIsActive(String text){
    	this.text = text;
 	}

	@Override
	public boolean test() {
		try{
			final Button swtButton = ButtonLookup.getInstance().getButton(0,
					new WithMnemonicMatcher(text),
					new StyleMatcher(SWT.PUSH | SWT.RADIO | SWT.TOGGLE | SWT.CHECK));
			if (swtButton != null){
				return WidgetLookup.getInstance().isEnabled(swtButton);
			}
			else{
				return false;
			}
		} catch (SWTLayerException swtle){
			return false;
		}
	}
	@Override
	public String description() {
		return "Button with text " + text + " is active";
	}

}
