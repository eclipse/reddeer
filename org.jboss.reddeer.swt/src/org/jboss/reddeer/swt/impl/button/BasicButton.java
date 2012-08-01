package org.jboss.reddeer.swt.impl.button;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.jboss.reddeer.swt.api.Button;

/**
 * Basic Button class is abstract class for all Button implementations
 * @author Jiri Peterka
 *
 */
public abstract class BasicButton implements Button {
	protected final Log logger = LogFactory.getLog(this.getClass());
	SWTBotButton button;
	
	@Override
	public void click() {
		logger.debug("Click on the button " + 
			      button.getText() != null ? button.getText() : 
			        (button.getToolTipText() != null ? button.getToolTipText(): "with no text or tooltip") +
			      " instance of class " + button.getClass());
		button.click();
		
	}
	
}
