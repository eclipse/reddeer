package org.jboss.reddeer.swt.impl.text;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.jboss.reddeer.swt.api.Text;

/**
 * Abstract class for all Text implementations
 * @author Jiri Peterka
 *
 */
public abstract class BasicText implements Text {
	protected final Logger log = Logger.getLogger(this.getClass());
	SWTBotText text;
	
	@Override
	public void setText(String str) {
		log.debug("Set text of Text widget to: " + str);
		text.setText(str);
		
	}

}
