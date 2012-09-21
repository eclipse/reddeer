package org.jboss.reddeer.swt.impl.button;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.jboss.reddeer.swt.api.Button;

/**
 * Basic Button class is abstract class for all Button implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractButton implements Button {
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	protected SWTBotButton button;
	
	@Override
	public void click() {
		log.info("Click on the button " + 
			     (button.getText() != null ? button.getText() : 
			        (button.getToolTipText() != null ? button.getToolTipText(): "with no text or tooltip")));
		button.click();
	}
	
	@Override
	public String getText() {
		return button.getText();
	}
	
	@Override
	public boolean isEnabled() {
		return button.isEnabled();
	}
}
