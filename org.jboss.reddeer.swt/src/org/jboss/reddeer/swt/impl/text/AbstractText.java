package org.jboss.reddeer.swt.impl.text;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.jboss.reddeer.swt.api.Text;

/**
 * Abstract class for all Text implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractText implements Text {
	protected final Logger log = Logger.getLogger(this.getClass());
	protected SWTBotText botText;
	
	@Override
	public void setText(String str) {
		log.debug("Set text of Text widget to: " + str);
		botText.setText(str);
		botText.setFocus();
	}
	
	
	@Override
	public String getText() {
		String text = botText.getText();
		return text;
	}
	
	
	@Override
	public String getToolTipText() {
		String tooltipText = botText.getToolTipText();
		return tooltipText;
	}
}
